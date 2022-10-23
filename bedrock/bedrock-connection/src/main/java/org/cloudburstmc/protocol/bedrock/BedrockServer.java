package org.cloudburstmc.protocol.bedrock;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.unix.UnixChannelOption;
import io.netty.util.concurrent.DefaultPromise;
import io.netty.util.concurrent.ImmediateEventExecutor;
import io.netty.util.concurrent.Promise;
import io.netty.util.concurrent.PromiseCombiner;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import net.jodah.expiringmap.ExpiringMap;
import org.cloudburstmc.netty.channel.raknet.RakChannelFactory;
import org.cloudburstmc.netty.channel.raknet.config.RakChannelOption;
import org.cloudburstmc.protocol.bedrock.netty.BedrockPeer;
import org.cloudburstmc.protocol.bedrock.netty.ChannelImplementation;
import org.cloudburstmc.protocol.bedrock.netty.Channels;
import org.cloudburstmc.protocol.bedrock.netty.codec.ServerChannelInitializer;
import org.cloudburstmc.protocol.bedrock.netty.codec.ServerChildChannelInitializer;
import org.cloudburstmc.protocol.bedrock.wrapper.BedrockWrapperSerializer;
import org.cloudburstmc.protocol.bedrock.wrapper.BedrockWrapperSerializers;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class BedrockServer extends Bedrock<ServerBootstrap> {

    private static final InternalLogger log = InternalLoggerFactory.getInstance(BedrockServer.class);

    private final int maxThreads;
    private final EventLoopGroup eventLoopGroup;
    private final Set<Channel> channels = new HashSet<>();

    private final long serverGuid = UUID.randomUUID().getMostSignificantBits();
    private int[] supportedRakVersions = new int[]{7, 8, 9, 10};

    private final ExpiringMap<InetAddress, Boolean> blockedAddressMap = ExpiringMap.builder().variableExpiration().build();
    private final Set<BedrockPeer<BedrockServerSession>> bedrockPeers = Collections.newSetFromMap(new ConcurrentHashMap<>());

    private final AtomicBoolean closed = new AtomicBoolean(false);

    public BedrockServer() {
        this(1);
    }

    public BedrockServer(int maxThreads) {
        this(maxThreads, Channels.commonGroup());
    }

    public BedrockServer(int maxThreads, EventLoopGroup eventLoopGroup) {
        this.maxThreads = maxThreads;
        this.eventLoopGroup = eventLoopGroup;
    }

    @Override
    protected Future<Void> doBind(InetSocketAddress address, ChannelFutureListener listener) {
        ServerBootstrap bootstrap = new ServerBootstrap();

        ChannelImplementation channelImpl = Channels.defaultImplementation();
        bootstrap.channelFactory(RakChannelFactory.server(channelImpl.datagramChannel()));
        bootstrap.handler(new ServerChannelInitializer(this));
        bootstrap.childHandler(new ServerChildChannelInitializer(this));

        EventLoopGroup networkLoops = Channels.newEventLoopGroup(this.maxThreads, true);
        bootstrap.group(networkLoops, networkLoops);
        bootstrap.localAddress(address);

        int maxThreads = Channels.reusePort() ? this.maxThreads : 1;
        if (channelImpl == ChannelImplementation.EPOLL && maxThreads > 1) {
            bootstrap.option(UnixChannelOption.SO_REUSEPORT, true);
            log.debug("Enabled SO_REUSEPORT option, creating " + maxThreads + " RakNet server sockets!");
        }

        bootstrap.option(RakChannelOption.RAK_SUPPORTED_PROTOCOLS, this.supportedRakVersions);
        bootstrap.option(RakChannelOption.RAK_GUID, this.serverGuid);
        this.adjustBootstrap(bootstrap);

        ChannelFutureListener channelListener = future -> {
            if (future.isSuccess()) {
                this.channels.add(future.channel());
            }
        };

        PromiseCombiner combiner = new PromiseCombiner(ImmediateEventExecutor.INSTANCE);
        for (int i = 0; i < maxThreads; i++) {
            ChannelFuture channelFuture = bootstrap.clone().bind();
            channelFuture.addListener(listener);
            channelFuture.addListener(channelListener);
            combiner.add(channelFuture);
        }

        // Return future which completes once all bootstraps are successfully bind
        Promise<Void> promise = new DefaultPromise<>(ImmediateEventExecutor.INSTANCE);
        combiner.finish(promise);
        return promise;
    }

    @Override
    public BedrockPeer<?> constructBedrockPeer(Channel channel) {
        EventLoop eventLoop = this.eventLoopGroup.next();
        BedrockPeer<BedrockServerSession> peer = new BedrockPeer<>(channel, eventLoop, this::constructBedrockSession);
        this.bedrockPeers.add(peer);
        return peer;
    }

    @Override
    protected BedrockServerSession constructBedrockSession(BedrockPeer<?> peer) {
        int rakVersion = peer.getOption(RakChannelOption.RAK_PROTOCOL_VERSION);
        BedrockWrapperSerializer serializer = BedrockWrapperSerializers.getSerializer(rakVersion);
        BedrockServerSession session = new BedrockServerSession(peer, serializer);
        // TODO: session creation handler
        return session;
    }

    @Override
    public void close(boolean force) {
        this.close("disconnect.disconnected");
    }

    public void close(String reason) {
        if (this.closed.get()) {
            return;
        }
        this.closed.set(true);

        for (BedrockPeer<BedrockServerSession> peer : this.bedrockPeers) {
            peer.getSession().disconnect(reason);
        }

        for (Channel channel : this.channels) {
            channel.close();
        }
    }

    @Override
    public boolean isClosed() {
        return this.closed.get();
    }

    public void blockAddress(InetAddress address, long duration, TimeUnit unit) {
        this.blockedAddressMap.put(address, true, duration, unit);
    }

    public void unblockAddress(InetAddress address) {
        this.blockedAddressMap.remove(address);
    }

    public boolean isBlocked(InetAddress address) {
        return this.blockedAddressMap.containsKey(address);
    }
}
