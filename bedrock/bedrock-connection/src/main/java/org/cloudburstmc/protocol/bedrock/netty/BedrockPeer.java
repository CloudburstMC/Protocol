package org.cloudburstmc.protocol.bedrock.netty;

import io.netty.channel.*;
import io.netty.util.concurrent.ScheduledFuture;
import org.cloudburstmc.netty.channel.raknet.RakDisconnectReason;
import org.cloudburstmc.netty.channel.raknet.packet.EncapsulatedPacket;
import org.cloudburstmc.netty.channel.raknet.packet.RakMessage;
import org.cloudburstmc.netty.handler.codec.common.RakSessionCodec;
import org.cloudburstmc.protocol.bedrock.BedrockSession;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public class BedrockPeer<T extends BedrockSession> extends SimpleChannelInboundHandler<EncapsulatedPacket> {

    public static final String NAME = "bedrock-peer";

    private final Channel channel;
    private final EventLoop eventLoop;
    private final ScheduledFuture<?> tickFuture;

    private final InetSocketAddress address;
    private volatile boolean closed;

    // SessionFactory can be useful when creating split-screen session
    private final Function<BedrockPeer<T>, T> sessionFactory;
    private final T session;
    // TODO: private final Map<Long, BedrockServerSession> childSessions = new ConcurrentHashMap<>();

    public BedrockPeer(Channel channel, EventLoop eventLoop, Function<BedrockPeer<T>, T> sessionFactory) {
        this.channel = channel;
        this.eventLoop = eventLoop;
        this.address = (InetSocketAddress) channel.remoteAddress();
        this.sessionFactory = sessionFactory;
        this.session = sessionFactory.apply(this);
        this.tickFuture = eventLoop.scheduleAtFixedRate(this::onTick, 50, 50, TimeUnit.MILLISECONDS);
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        ctx.pipeline().addLast("rak-session-clone-listener", new SimpleEventHandler<>(this::onRakNetDisconnect));
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) {
        if (!this.closed) {
            this.free();
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, EncapsulatedPacket encapsulated) throws Exception {
        // TODO: change to this.eventLoop, fire thought bedrockPipeline, decrypt, decompress
    }

    public void sendMessage(RakMessage message) {
        if (!this.closed) {
            this.channel.writeAndFlush(message);
        }
    }

    private void onTick() {
        this.session.tick();
        // TODO: tick child sessions
    }

    private void onRakNetDisconnect(ChannelHandlerContext ctx, RakDisconnectReason reason) {
        // We don't free BedrockPeer here because handlerRemoved() should be called as channel will be closed soon
        this.session.close(reason);
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
        this.tickFuture.cancel(false);
    }

    public boolean isClosed() {
        return this.closed;
    }

    public Channel getChannel() {
        return this.channel;
    }

    public EventLoop getEventLoop() {
        return this.eventLoop;
    }

    public InetSocketAddress getAddress() {
        return this.address;
    }

    public T getSession() {
        return this.session;
    }

    public RakSessionCodec getRakSessionCodec() {
        return this.channel.pipeline().get(RakSessionCodec.class);
    }

    public <T> T getOption(ChannelOption<T> option) {
        return this.channel.config().getOption(option);
    }
}
