package org.cloudburstmc.protocol.bedrock;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.DecoderException;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.ScheduledFuture;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.cloudburstmc.netty.channel.raknet.RakDisconnectReason;
import org.cloudburstmc.netty.channel.raknet.config.RakChannelOption;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodec;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v428.Bedrock_v428;
import org.cloudburstmc.protocol.bedrock.data.PacketCompressionAlgorithm;
import org.cloudburstmc.protocol.bedrock.netty.BedrockPacketWrapper;
import org.cloudburstmc.protocol.bedrock.netty.codec.BlackholeInboundAdapter;
import org.cloudburstmc.protocol.bedrock.netty.codec.FrameIdCodec;
import org.cloudburstmc.protocol.bedrock.netty.codec.batch.BedrockBatchDecoder;
import org.cloudburstmc.protocol.bedrock.netty.codec.compression.CompressionCodec;
import org.cloudburstmc.protocol.bedrock.netty.codec.compression.CompressionStrategy;
import org.cloudburstmc.protocol.bedrock.netty.codec.encryption.BedrockEncryptionDecoder;
import org.cloudburstmc.protocol.bedrock.netty.codec.encryption.BedrockEncryptionEncoder;
import org.cloudburstmc.protocol.bedrock.netty.codec.packet.BedrockPacketCodec;
import org.cloudburstmc.protocol.bedrock.netty.initializer.BedrockChannelInitializer;
import org.cloudburstmc.protocol.bedrock.packet.BedrockPacket;
import org.cloudburstmc.protocol.bedrock.util.EncryptionUtils;

import javax.crypto.SecretKey;
import java.net.SocketAddress;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * A Bedrock peer that represents a single network connection to the remote peer.
 * It can hold one or more {@link BedrockSession}s.
 */
public class BedrockPeer extends ChannelInboundHandlerAdapter {

    public static final String NAME = "bedrock-peer";

    private static final InternalLogger log = InternalLoggerFactory.getInstance(BedrockPeer.class);

    protected final Int2ObjectMap<BedrockSession> sessions = new Int2ObjectOpenHashMap<>();
    protected final Queue<BedrockPacketWrapper> packetQueue = PlatformDependent.newMpscQueue();
    protected final Channel channel;
    protected final BedrockSessionFactory sessionFactory;
    protected ScheduledFuture<?> tickFuture;
    protected AtomicBoolean closed = new AtomicBoolean();
    protected AtomicBoolean closing = new AtomicBoolean();

    public BedrockPeer(Channel channel, BedrockSessionFactory sessionFactory) {
        this.channel = channel;
        this.sessionFactory = sessionFactory;
    }

    protected void onBedrockPacket(BedrockPacketWrapper wrapper) {
        if (this.closing.get()) {
            return;
        }
        int targetId = wrapper.getTargetSubClientId();
        BedrockSession session = this.sessions.computeIfAbsent(targetId, this::onSessionCreated);
        session.onPacket(wrapper);
    }

    protected BedrockSession onSessionCreated(int sessionId) {
        return this.sessionFactory.createSession(this, sessionId);
    }

    protected void checkForClosed() {
        if (this.closed.get()) {
            throw new IllegalStateException("Peer has been closed");
        }
    }

    protected void removeSession(BedrockSession session) {
        this.sessions.remove(session.subClientId, session);
    }

    protected void onTick() {
        if (this.closing.get() || this.closed.get()) {
            return;
        }
        flushQueue();
    }

    protected void flushQueue() {
        if (this.packetQueue.isEmpty()) {
            return;
        }
        BedrockPacketWrapper packet;
        while ((packet = this.packetQueue.poll()) != null) {
            this.channel.write(packet);
        }
        this.channel.flush();
    }

    private void onRakNetDisconnect(ChannelHandlerContext ctx, RakDisconnectReason reason) {
        CharSequence disconnectReason = BedrockDisconnectReasons.getReason(reason);
        for (BedrockSession session : this.sessions.values()) {
            session.disconnectReason = disconnectReason;
        }
    }

    private void free() {
        for (BedrockPacketWrapper wrapper : this.packetQueue) {
            ReferenceCountUtil.safeRelease(wrapper);
        }
    }

    public void sendPacket(int senderClientId, int targetClientId, BedrockPacket packet) {
        if (this.closing.get() || this.closed.get()) {
            ReferenceCountUtil.safeRelease(packet); // queue is no longer drained
            return;
        }
        this.packetQueue.add(BedrockPacketWrapper.create(0, senderClientId, targetClientId, packet, null));
    }

    public void sendPacketImmediately(int senderClientId, int targetClientId, BedrockPacket packet) {
        if (this.closing.get()) { // closed is covered by netty: writes to a closed channel are failed and released
            ReferenceCountUtil.safeRelease(packet);
            return;
        }
        this.channel.writeAndFlush(BedrockPacketWrapper.create(0, senderClientId, targetClientId, packet, null));
    }

    public void sendPacketsImmediately(int senderClientId, int targetClientId, BedrockPacket... packets) {
        if (this.closing.get()) {
            for (BedrockPacket packet : packets) {
                ReferenceCountUtil.safeRelease(packet);
            }
            return;
        }
        for (BedrockPacket packet : packets) {
            this.channel.write(BedrockPacketWrapper.create(0, senderClientId, targetClientId, packet, null));
        }
        this.channel.flush();
    }

    public void enableEncryption(@NonNull SecretKey secretKey) {
        Objects.requireNonNull(secretKey, "secretKey");
        if (!secretKey.getAlgorithm().equals("AES")) {
            throw new IllegalArgumentException("Invalid key algorithm");
        }
        // Check if the codecs exist in the pipeline
        if (this.channel.pipeline().get(BedrockEncryptionEncoder.class) != null ||
                this.channel.pipeline().get(BedrockEncryptionDecoder.class) != null) {
            throw new IllegalStateException("Encryption is already enabled");
        }

        int protocolVersion = this.getCodec().getProtocolVersion();
        boolean useCtr = protocolVersion >= Bedrock_v428.CODEC.getProtocolVersion();

        this.channel.pipeline().addAfter(FrameIdCodec.NAME, BedrockEncryptionEncoder.NAME,
                new BedrockEncryptionEncoder(secretKey, EncryptionUtils.createCipher(useCtr, true, secretKey)));
        this.channel.pipeline().addAfter(FrameIdCodec.NAME, BedrockEncryptionDecoder.NAME,
                new BedrockEncryptionDecoder(secretKey, EncryptionUtils.createCipher(useCtr, false, secretKey)));

        log.debug("Encryption enabled for {}", getSocketAddress());
    }

    public void setCompression(PacketCompressionAlgorithm algorithm) {
        Objects.requireNonNull(algorithm, "algorithm");
        this.setCompression(BedrockChannelInitializer.getCompression(algorithm, this.getRakVersion(), false));
    }

    public void setCompression(CompressionStrategy strategy) {
        Objects.requireNonNull(strategy, "strategy");

        boolean needsPrefix = this.getCodec().getProtocolVersion() >= 649; // TODO: do not hardcode

        ChannelHandler handler = this.channel.pipeline().get(CompressionCodec.NAME);
        if (handler == null) {
            this.channel.pipeline().addBefore(BedrockBatchDecoder.NAME, CompressionCodec.NAME, new CompressionCodec(strategy, needsPrefix));
        } else {
            this.channel.pipeline().replace(CompressionCodec.NAME, CompressionCodec.NAME, new CompressionCodec(strategy, needsPrefix));
        }
    }

    public CompressionStrategy getCompressionStrategy() {
        ChannelHandler handler = this.channel.pipeline().get(CompressionCodec.NAME);
        if (!(handler instanceof CompressionCodec)) {
            return null;
        }
        return ((CompressionCodec) handler).getStrategy();
    }

    public BedrockCodec getCodec() {
        return this.channel.pipeline().get(BedrockPacketCodec.class).getCodec();
    }

    public BedrockCodecHelper getCodecHelper() {
        return this.channel.pipeline().get(BedrockPacketCodec.class).getHelper();
    }

    public void setCodec(BedrockCodec codec) {
        Objects.requireNonNull(codec, "codec");
        this.channel.pipeline().get(BedrockPacketCodec.class).setCodec(codec);
    }

    public void close(CharSequence reason) {
        if (this.channel.eventLoop().inEventLoop()) {
            this.close0(reason, false);
        } else {
            this.channel.eventLoop().execute(() -> this.close0(reason, false));
        }
    }

    protected void close0(CharSequence reason, boolean force) {
        if (!this.closing.compareAndSet(false, true)) {
            return;
        }

        this.blackholeInboundPackets(); // inbound stops either way

        for (BedrockSession session : this.sessions.values()) {
            session.disconnectReason = reason;
        }

        if (force) {
            // If an exception is thrown or the peer is misbehaving, we close it immediately without notification
            this.channel.close();
        } else {
            this.channel.disconnect();
        }
    }

    protected void onClose() {
        if (this.channel.isOpen()) {
            log.warn("Tried to close peer, but channel is open!", new Throwable());
            return;
        }

        if (!this.closed.compareAndSet(false, true)) {
            return;
        }

        if (this.tickFuture != null) {
            this.tickFuture.cancel(false);
            this.tickFuture = null;
        }

        for (BedrockSession session : this.sessions.values()) {
            try {
                session.onClose();
            } catch (Exception e) {
                log.error("Exception whilst closing session", e);
            }
        }

        this.free();
    }

    public boolean isConnected() {
        return !this.closed.get() && this.channel.isOpen();
    }

    public boolean isConnecting() {
        return !this.channel.isActive() && !this.closed.get();
    }

    public SocketAddress getSocketAddress() {
        return this.channel.remoteAddress();
    }

    public Channel getChannel() {
        return this.channel;
    }

    public int getRakVersion() {
        return this.channel.config().getOption(RakChannelOption.RAK_PROTOCOL_VERSION);
    }

    /*
        Netty handler methods
     */

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) {
        this.onClose();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        this.sessions.put(0, this.sessionFactory.createSession(this, 0));
        this.tickFuture = this.channel.eventLoop().scheduleAtFixedRate(this::onTick, 50, 50, TimeUnit.MILLISECONDS);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        this.onClose();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            if (msg instanceof BedrockPacketWrapper) {
                this.onBedrockPacket((BedrockPacketWrapper) msg);
            } else {
                throw new DecoderException("Unexpected message type: " + msg.getClass().getName());
            }
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof RakDisconnectReason) {
            onRakNetDisconnect(ctx, (RakDisconnectReason) evt);
        }
    }

    protected void blackholeInboundPackets() {
        if (this.channel.pipeline().get(BlackholeInboundAdapter.class) == null) {
            this.channel.pipeline().addFirst(BlackholeInboundAdapter.NAME, BlackholeInboundAdapter.INSTANCE);
        }
    }
}
