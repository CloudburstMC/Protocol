package org.cloudburstmc.protocol.java;

import com.nukkitx.network.util.Bootstraps;
import com.nukkitx.network.util.EventLoops;
import com.nukkitx.network.util.Preconditions;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollSocketChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;
import lombok.Getter;
import org.cloudburstmc.protocol.java.network.JavaPacketDecoder;
import org.cloudburstmc.protocol.java.network.JavaPacketEncoder;
import org.cloudburstmc.protocol.java.network.JavaVarInt21FrameDecoder;
import org.cloudburstmc.protocol.java.network.JavaVarInt21LengthFieldPrepender;
import org.cloudburstmc.protocol.java.packet.type.JavaPacketType;

import java.net.InetSocketAddress;
import java.util.concurrent.CompletableFuture;

@Getter
public class JavaClient extends Java {
    private InetSocketAddress remoteAddress;
    private JavaClientSession session;
    private int timeout;
    private boolean closed = false;

    private Bootstrap bootstrap;

    public JavaClient(InetSocketAddress bindAddress) {
        this(bindAddress, EventLoops.commonGroup());
    }

    public JavaClient(InetSocketAddress bindAddress, EventLoopGroup eventLoopGroup) {
        this(bindAddress, eventLoopGroup, 30_000);
    }

    JavaClient(InetSocketAddress bindAddress, EventLoopGroup eventLoopGroup, int timeout) {
        super(bindAddress, eventLoopGroup);

        this.timeout = timeout;
    }

    @Override
    protected void onTick() {
        if (this.session.isConnected()) {
            this.session.tick();
        }
    }

    @Override
    public CompletableFuture<Void> bind() {
        Preconditions.checkNotNull(this.eventLoopGroup, "Event loop group was null");

        Class<? extends SocketChannel> channel = NioSocketChannel.class;
        if (Epoll.isAvailable()) {
            channel = EpollSocketChannel.class;
        }
        this.bootstrap = new Bootstrap()
                .channel(channel)
                .group(this.eventLoopGroup)
                .localAddress(this.getBindAddress())
                .option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, this.timeout)
                .handler(new ChannelInitializer<Channel>() {
                    @Override
                    public void initChannel(Channel channel) throws Exception {
                        JavaClientSession session = new JavaClientSession((InetSocketAddress) channel.remoteAddress(), channel, channel.eventLoop());
                        channel.pipeline()
                                .addLast("timeout", new ReadTimeoutHandler(JavaClient.this.timeout))
                                .addLast("splitter", new JavaVarInt21FrameDecoder())
                                .addLast("prepender", new JavaVarInt21LengthFieldPrepender())
                                .addLast("encoder", new JavaPacketEncoder(session))
                                .addLast("decoder", new JavaPacketDecoder(session, JavaPacketType.Direction.CLIENTBOUND))
                                .addLast("session", session);
                        JavaClient.this.session = session;
                    }
                });
        return CompletableFuture.completedFuture(null);
    }

    public CompletableFuture<Void> connect(InetSocketAddress remoteAddress) {
        Preconditions.checkNotNull(this.bootstrap, "Client has not been created yet!");
        this.remoteAddress = remoteAddress;
        return Bootstraps.allOf(this.bootstrap.connect(this.remoteAddress).syncUninterruptibly());
    }

    @Override
    public void close() {
        this.close("disconnect.disconnected");
    }

    public void close(String reason) {
        this.session.disconnect(reason);
        this.closed = true;
    }
}
