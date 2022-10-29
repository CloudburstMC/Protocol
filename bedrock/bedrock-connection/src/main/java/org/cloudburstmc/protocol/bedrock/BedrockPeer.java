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
import org.cloudburstmc.netty.channel.raknet.RakDisconnectReason;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodec;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v428.Bedrock_v428;
import org.cloudburstmc.protocol.bedrock.data.PacketCompressionAlgorithm;
import org.cloudburstmc.protocol.bedrock.netty.BedrockPacketWrapper;
import org.cloudburstmc.protocol.bedrock.netty.codec.batch.BedrockBatchDecoder;
import org.cloudburstmc.protocol.bedrock.netty.codec.compression.CompressionCodec;
import org.cloudburstmc.protocol.bedrock.netty.codec.compression.SnappyCompressionCodec;
import org.cloudburstmc.protocol.bedrock.netty.codec.compression.ZlibCompressionCodec;
import org.cloudburstmc.protocol.bedrock.netty.codec.encryption.BedrockEncryptionDecoder;
import org.cloudburstmc.protocol.bedrock.netty.codec.encryption.BedrockEncryptionEncoder;
import org.cloudburstmc.protocol.bedrock.netty.codec.packet.BedrockPacketCodec;
import org.cloudburstmc.protocol.bedrock.packet.BedrockPacket;
import org.cloudburstmc.protocol.bedrock.util.EncryptionUtils;
import org.cloudburstmc.protocol.common.util.Zlib;

import javax.annotation.Nonnull;
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

    private final Int2ObjectMap<BedrockSession> sessions = new Int2ObjectOpenHashMap<>();
    private final Queue<BedrockPacketWrapper> packetQueue = PlatformDependent.newMpscQueue();
    private final Channel channel;
    private final BedrockSessionFactory sessionFactory;
    private ScheduledFuture<?> tickFuture;
    private AtomicBoolean closed = new AtomicBoolean();

    public BedrockPeer(Channel channel, BedrockSessionFactory sessionFactory) {
        this.channel = channel;
        this.sessionFactory = sessionFactory;
    }

    protected void onBedrockPacket(BedrockPacketWrapper wrapper) {
        int targetId = wrapper.getTargetSubClientId();
        BedrockSession session = this.sessions.computeIfAbsent(targetId, id -> this.sessionFactory.createSession(this, id));
        session.onPacket(wrapper.getPacket());
    }

    protected void checkForClosed() {
        if (this.closed.get()) {
            throw new IllegalStateException("Peer has been closed");
        }
    }

    protected void removeSession(BedrockSession session) {
        this.sessions.remove(session.subClientId, session);
    }

    private void onTick() {
        if (!this.packetQueue.isEmpty()) {
            BedrockPacketWrapper packet;
            while ((packet = this.packetQueue.poll()) != null) {
                this.channel.write(packet);
            }
            this.channel.flush();
        }
    }

    private void onRakNetDisconnect(ChannelHandlerContext ctx, RakDisconnectReason reason) {
        String disconnectReason = BedrockDisconnectReasons.getReason(reason);
        for (BedrockSession session : this.sessions.values()) {
            session.disconnectReason = disconnectReason;
        }
    }

    private void free() {
        for (BedrockPacketWrapper wrapper : this.packetQueue) {
            ReferenceCountUtil.release(wrapper);
        }
    }

    public void sendPacket(int senderClientId, int targetClientId, BedrockPacket packet) {
        ReferenceCountUtil.retain(packet);
        this.packetQueue.add(new BedrockPacketWrapper(0, senderClientId, targetClientId, packet, null));
    }

    public void sendPacketImmediately(int senderClientId, int targetClientId, BedrockPacket packet) {
        ReferenceCountUtil.retain(packet);
        this.channel.writeAndFlush(new BedrockPacketWrapper(0, senderClientId, targetClientId, packet, null));
    }

    public void enableEncryption(@Nonnull SecretKey secretKey) {
        Objects.requireNonNull(secretKey, "secretKey");
        if (!secretKey.getAlgorithm().equals("AES")) {
            throw new IllegalArgumentException("Invalid key algorithm");
        }
        // Check if the codecs exist in the pipeline
        if (this.channel.pipeline().get(BedrockEncryptionEncoder.class) != null ||
                this.channel.pipeline().get(BedrockEncryptionDecoder.class) != null) {
            throw new IllegalStateException("Encryption is already enabled");
        }

        int protocolVersion = this.channel.pipeline().get(BedrockPacketCodec.class).getCodec().getProtocolVersion();
        boolean useCtr = protocolVersion >= Bedrock_v428.CODEC.getProtocolVersion();

        this.channel.pipeline().addBefore(BedrockBatchDecoder.NAME, BedrockEncryptionEncoder.NAME,
                new BedrockEncryptionEncoder(secretKey, EncryptionUtils.createCipher(useCtr, true, secretKey)));
        this.channel.pipeline().addBefore(BedrockEncryptionEncoder.NAME, BedrockEncryptionDecoder.NAME,
                new BedrockEncryptionDecoder(secretKey, EncryptionUtils.createCipher(useCtr, false, secretKey)));

        log.debug("Encryption enabled for {}", getSocketAddress());
    }

    public void setCompression(PacketCompressionAlgorithm algorithm) {
        Objects.requireNonNull(algorithm, "algorithm");
        ChannelHandler handler = this.channel.pipeline().get(CompressionCodec.NAME);
        if (handler != null) {
            throw new IllegalArgumentException("Compression is already set");
        }
        ChannelHandler compressionHandler;
        switch (algorithm) {
            case ZLIB:
                compressionHandler = new ZlibCompressionCodec(Zlib.RAW);
                break;
            case SNAPPY:
                compressionHandler = new SnappyCompressionCodec();
                break;
            default:
                throw new UnsupportedOperationException("Unsupported compression algorithm: " + algorithm);
        }
        this.channel.pipeline().addBefore(BedrockBatchDecoder.NAME, CompressionCodec.NAME, compressionHandler);
    }

    public void setCompressionLevel(int level) {
        ChannelHandler handler = this.channel.pipeline().get(CompressionCodec.NAME);
        if (handler == null) {
            throw new IllegalArgumentException("Peer has no compression!");
        }
        ((CompressionCodec) handler).setLevel(level);
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

    public void close(String reason) {
        for (BedrockSession session : this.sessions.values()) {
            session.disconnectReason = reason;
        }
        this.channel.close();
    }

    protected void onClose() {
        if (this.channel.isOpen() || !this.closed.compareAndSet(false, true)) {
            return;
        }

        for (BedrockSession session : this.sessions.values())
            try {
                session.onClose();
            } catch (Exception e) {
                log.error("Exception whilst closing session", e);
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
        this.tickFuture.cancel(false);
        this.tickFuture = null;
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
}
