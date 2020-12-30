package org.cloudburstmc.protocol.java;

import com.google.common.base.Preconditions;
import com.nukkitx.network.util.DisconnectReason;
import com.nukkitx.protocol.MinecraftSession;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.EventLoop;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.cloudburstmc.protocol.java.handler.JavaPacketHandler;
import org.cloudburstmc.protocol.java.packet.State;
import org.cloudburstmc.protocol.java.packet.play.DisconnectPacket;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.net.InetSocketAddress;
import java.util.Collections;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeoutException;
import java.util.function.Consumer;

@Getter
public abstract class JavaSession extends SimpleChannelInboundHandler<JavaPacket<?>> implements MinecraftSession<JavaPacket<?>> {
    private static final InternalLogger log = InternalLoggerFactory.getInstance(JavaSession.class);

    private final Set<Consumer<DisconnectReason>> disconnectHandlers = Collections.newSetFromMap(new ConcurrentHashMap<>());
    private final Queue<JavaPacket<?>> queuedPackets = PlatformDependent.newMpscQueue();
    private final InetSocketAddress address;
    private final EventLoop eventLoop;
    private JavaPacketCodec packetCodec = null;
    private JavaPacketHandler packetHandler;
    private State protocolState = State.HANDSHAKING;
    private Channel channel;
    private boolean compression = false;
    private int compressionThreshold;
    private volatile boolean closed = false;
    private volatile boolean logging = true;

    JavaSession(InetSocketAddress address, Channel channel, EventLoop eventLoop) {
        this.address = address;
        this.channel = channel;
        this.eventLoop = eventLoop;
    }

    public void setPacketCodec(JavaPacketCodec packetCodec) {
        this.packetCodec = packetCodec;
        this.protocolState = packetCodec.getProtocolState();
    }

    public void setPacketHandler(@Nonnull JavaPacketHandler packetHandler) {
        this.packetHandler = packetHandler;
    }

    void checkForClosed() {
        if (this.closed || this.channel == null || !this.channel.isOpen()) {
            throw new IllegalStateException("Connection has been closed");
        }
    }

    @Override
    public boolean isClosed() {
        return this.closed;
    }

    public boolean isConnected() {
        return this.closed && this.channel.isOpen();
    }

    public void close() {
        this.closed = true;
        if (this.channel.isOpen()) {
            this.channel.close();
        }
    }

    @Override
    public InetSocketAddress getAddress() {
        return this.address;
    }

    @Override
    public void sendPacket(@Nonnull JavaPacket<?> packet) {
        this.checkPacket(packet);

        // Only queue if player is in the play state
        if (this.protocolState == State.PLAY) {
            this.queuedPackets.add(packet);
        } else {
            this.sendPacketImmediately(packet);
        }
    }

    @Override
    public void sendPacketImmediately(@Nonnull JavaPacket<?> packet) {
        this.checkPacket(packet);
        this.channel.writeAndFlush(packet).addListener((ChannelFutureListener) future -> {
            if (!future.isSuccess()) {
                exceptionCaught(null, future.cause());
            }
        });
    }

    private void checkPacket(JavaPacket<?> packet) {
        this.checkForClosed();
        Objects.requireNonNull(packet, "packet");

        Preconditions.checkState(this.protocolState == packet.getPacketType().getState(),
                "Expected protocol state %s, but %s was of state %s",
                this.protocolState, packet.toString(), packet.getPacketType().getState());

        if (log.isTraceEnabled() && this.logging) {
            String to = this.getAddress().toString();
            log.trace("Outbound {}: {}", to, packet);
        }

        // Verify that the packet ID exists.
        this.packetCodec.getId(packet);
    }

    @Override
    public long getLatency() {
        return 0;
    }

    public void tick() {
        this.eventLoop.execute(this::onTick);
    }

    private void onTick() {
        if (this.closed) {
            return;
        }

        this.sendQueued();
    }

    private void sendQueued() {
        JavaPacket<?> packet;
        while ((packet = this.queuedPackets.poll()) != null) {
            this.sendPacketImmediately(packet);
        }
    }

    @Override
    public void disconnect() {
        this.disconnect(null, true);
    }

    public void disconnect(@Nullable String reason) {
        this.disconnect(reason, false);
    }

    public void disconnect(@Nullable String reason, boolean hideReason) {
        if (this.protocolState == State.PLAY) {
            DisconnectPacket packet = new DisconnectPacket();
            if (reason == null || hideReason) {
                reason = "disconnect.disconnected";
            }
            packet.setReason(Component.text(reason));
            this.sendPacketImmediately(packet);
        }
        this.close();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, JavaPacket<?> javaPacket) throws Exception {
        if (this.channel != null && this.channel.isOpen()) {
            try {
                ((JavaPacket) javaPacket).handle(this.packetHandler);
            } catch (Exception ex) {
                ex.printStackTrace();
                throw ex;
            }
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        this.disconnect("disconnect.endOfStream");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if (cause instanceof TimeoutException) {
            this.disconnect("disconnect.timeout");
        } else {
            this.disconnect("disconnect.genericReason");
        }
        log.warn(cause.getMessage(), cause);
    }
}
