package org.cloudburstmc.protocol.bedrock;

import io.netty.channel.Channel;
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
import org.cloudburstmc.protocol.bedrock.codec.compat.BedrockCompat;
import org.cloudburstmc.protocol.bedrock.codec.v428.Bedrock_v428;
import org.cloudburstmc.protocol.bedrock.netty.BedrockPacketWrapper;
import org.cloudburstmc.protocol.bedrock.netty.codec.batch.BedrockBatchDecoder;
import org.cloudburstmc.protocol.bedrock.netty.codec.encryption.BedrockEncryptionDecoder;
import org.cloudburstmc.protocol.bedrock.netty.codec.encryption.BedrockEncryptionEncoder;
import org.cloudburstmc.protocol.bedrock.netty.codec.packet.BedrockPacketCodec;
import org.cloudburstmc.protocol.bedrock.netty.initializer.BedrockSessionFactory;
import org.cloudburstmc.protocol.bedrock.packet.BedrockPacket;
import org.cloudburstmc.protocol.bedrock.util.EncryptionUtils;

import javax.annotation.Nonnull;
import javax.crypto.SecretKey;
import java.net.SocketAddress;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

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
    private boolean closed;
    private RakDisconnectReason disconnectReason;
    private BedrockCodec codec = BedrockCompat.COMPAT_CODEC;

    public BedrockPeer(Channel channel, BedrockSessionFactory sessionFactory) {
        this.channel = channel;
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {

    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) {
        if (!this.closed) {
            this.free();
        }
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

    protected void onBedrockPacket(BedrockPacketWrapper wrapper) {
        int targetId = wrapper.getTargetSubClientId();
        this.sessions.computeIfAbsent(targetId, id -> new BedrockSession(this, id));
    }

    public void sendPacket(int senderClientId, int targetClientId, BedrockPacket packet) {
        BedrockPacketWrapper wrapper = new BedrockPacketWrapper();
        wrapper.setPacket(packet);
        wrapper.setSenderSubClientId(senderClientId);
        wrapper.setTargetSubClientId(targetClientId);
        this.packetQueue.add(wrapper);
    }

    public void sendPacketImmediately(int senderClientId, int targetClientId, BedrockPacket packet) {
        BedrockPacketWrapper wrapper = new BedrockPacketWrapper();
        wrapper.setPacket(packet);
        wrapper.setSenderSubClientId(senderClientId);
        wrapper.setTargetSubClientId(targetClientId);
        this.channel.writeAndFlush(wrapper);
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
        this.disconnectReason = reason;
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

    public BedrockCodec getCodec() {
        return this.channel.pipeline().get(BedrockPacketCodec.class).getCodec();
    }

    public void setCodec(BedrockCodec codec) {
        Objects.requireNonNull(codec, "codec");
        this.channel.pipeline().get(BedrockPacketCodec.class).setCodec(codec);
    }

    public void close() {
        if (!this.closed) {
            this.free();
            this.channel.close();
        }
    }

    private void free() {
        this.closed = true;
        if (!this.session.isClosed()) {
            this.session.close(RakDisconnectReason.DISCONNECTED);
        }
    }

    public boolean isClosed() {
        return this.closed;
    }

    public SocketAddress getSocketAddress() {
        return this.channel.remoteAddress();
    }
}
