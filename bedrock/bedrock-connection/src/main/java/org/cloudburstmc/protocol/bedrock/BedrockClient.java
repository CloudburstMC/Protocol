package org.cloudburstmc.protocol.bedrock;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.util.concurrent.DefaultPromise;
import io.netty.util.concurrent.ImmediateEventExecutor;
import io.netty.util.concurrent.Promise;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import org.cloudburstmc.netty.channel.raknet.RakChannelFactory;
import org.cloudburstmc.netty.channel.raknet.RakConstants;
import org.cloudburstmc.netty.channel.raknet.config.RakChannelOption;
import org.cloudburstmc.protocol.bedrock.raknet.BedrockPeer;
import org.cloudburstmc.protocol.bedrock.raknet.ChannelImplementation;
import org.cloudburstmc.protocol.bedrock.raknet.Channels;
import org.cloudburstmc.protocol.bedrock.raknet.pipeline.ClientChannelInitializer;
import org.cloudburstmc.protocol.bedrock.wrapper.BedrockWrapperSerializer;
import org.cloudburstmc.protocol.bedrock.wrapper.BedrockWrapperSerializers;

import java.net.InetSocketAddress;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class BedrockClient extends Bedrock<Bootstrap> {

    private static final InternalLogger log = InternalLoggerFactory.getInstance(BedrockClient.class);

    private final EventLoopGroup eventLoopGroup;
    private final EventLoopGroup networkGroup;

    private int rakNetProtocol = RakConstants.RAKNET_PROTOCOL_VERSION;
    private volatile boolean closed;

    private Channel channel;
    private BedrockPeer<BedrockClientSession> bedrockPeer;

    public BedrockClient() {
        this(Channels.commonGroup(), null);
    }

    public BedrockClient(EventLoopGroup bedrockGroup, EventLoopGroup networkGroup) {
        this.eventLoopGroup = bedrockGroup;
        this.networkGroup = networkGroup;
    }

    @Override
    protected Future<Void> doBind(InetSocketAddress address, ChannelFutureListener listener) {
        Bootstrap bootstrap = this.createBootstrap();
        ChannelFuture channelFuture = bootstrap.bind();
        channelFuture.addListener(listener);

        Promise<Void> promise = new DefaultPromise<>(ImmediateEventExecutor.INSTANCE);
        channelFuture.addListener((ChannelFuture f) -> {
            if (f.cause() == null) {
                this.channel = f.channel();
                promise.setSuccess(null);
            } else {
                promise.setFailure(f.cause());
            }
        });
        return promise;
    }

    public ChannelFuture connect(InetSocketAddress address) {
        return this.connect(address, 10, TimeUnit.SECONDS);
    }

    public ChannelFuture connect(InetSocketAddress address, long timeout, TimeUnit unit) {
        if (this.bindAddress != null) {
            throw new IllegalStateException("Already bind");
        }
        this.bindAddress = address;

        Bootstrap bootstrap = this.createBootstrap();
        bootstrap.option(RakChannelOption.RAK_CONNECT_TIMEOUT, unit.toMillis(timeout));
        bootstrap.remoteAddress(address);

        ChannelFuture channelFuture = bootstrap.connect();
        channelFuture.addListener((ChannelFuture f) -> {
            if (f.cause() == null) {
                this.channel = f.channel();
            }
        });
        return channelFuture;
    }

    private Bootstrap createBootstrap() {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.handler(new ClientChannelInitializer(this));

        ChannelImplementation channelImpl = Channels.defaultImplementation();
        bootstrap.channelFactory(RakChannelFactory.client(channelImpl.datagramChannel()));

        EventLoopGroup eventLoopGroup = this.networkGroup;
        if (eventLoopGroup == null) {
            eventLoopGroup = Channels.newEventLoopGroup(1, true);
        }
        bootstrap.group(eventLoopGroup);
        bootstrap.option(RakChannelOption.RAK_PROTOCOL_VERSION, this.rakNetProtocol);
        this.adjustBootstrap(bootstrap);
        return bootstrap;
    }

    @Override
    public BedrockPeer<?> constructBedrockPeer(Channel channel) {
        if (this.bedrockPeer != null) {
            throw new IllegalStateException("Client was already connected!");
        }
        EventLoop eventLoop = this.eventLoopGroup.next();
        return this.bedrockPeer = new BedrockPeer<>(channel, eventLoop, this::constructBedrockSession);
    }

    @Override
    protected BedrockClientSession constructBedrockSession(BedrockPeer<?> peer) {
        int rakVersion = peer.getOption(RakChannelOption.RAK_PROTOCOL_VERSION);
        BedrockWrapperSerializer serializer = BedrockWrapperSerializers.getSerializer(rakVersion);
        return new BedrockClientSession(peer, serializer);
    }

    @Override
    public void close(boolean force) {
        if (this.closed || this.channel == null) {
            return;
        }
        this.closed = true;

        if (this.bedrockPeer != null) {
            this.bedrockPeer.getSession().disconnect();
            return;
        }

        ChannelFuture future = this.channel.close();
        if (force) {
            future.syncUninterruptibly();
        }
    }

    @Override
    public boolean isClosed() {
        return this.closed;
    }

    public void setRakNetProtocol(int rakNetProtocol) {
        this.rakNetProtocol = rakNetProtocol;
    }

    public int getRakNetProtocol() {
        return this.rakNetProtocol;
    }

    public Channel getChannel() {
        return this.channel;
    }

    public BedrockPeer<BedrockClientSession> getBedrockPeer() {
        return this.bedrockPeer;
    }
}
