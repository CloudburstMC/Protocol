package com.nukkitx.protocol.bedrock;

import com.nukkitx.network.raknet.RakNetClient;
import com.nukkitx.network.raknet.RakNetClientSession;
import com.nukkitx.network.util.EventLoops;
import com.nukkitx.protocol.bedrock.wrapper.BedrockWrapperSerializer;
import com.nukkitx.protocol.bedrock.wrapper.BedrockWrapperSerializers;
import io.netty.channel.EventLoopGroup;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetSocketAddress;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class BedrockClient extends Bedrock {
    private final RakNetClient rakNetClient;
    BedrockClientSession session;

    public BedrockClient(InetSocketAddress bindAddress) {
        this(bindAddress, EventLoops.commonGroup(), EventLoops.commonGroup());
    }

    public BedrockClient(InetSocketAddress bindAddress, EventLoopGroup eventLoopGroup) {
        this(bindAddress, eventLoopGroup, eventLoopGroup);
    }

    public BedrockClient(InetSocketAddress bindAddress, EventLoopGroup bossGroup, EventLoopGroup workerGroup) {
        super(bossGroup, workerGroup);
        this.rakNetClient = new RakNetClient(bindAddress, bossGroup);
    }

    @Override
    protected void onTick() {
        if (this.session != null) {
            this.session.tick();
        }
    }

    @Override
    public RakNetClient getRakNet() {
        return this.rakNetClient;
    }

    public void setRakNetVersion(int version) {
        this.rakNetClient.setProtocolVersion(version);
    }

    @Override
    public void close(boolean force) {
        if (this.session != null && !this.session.isClosed()) {
            this.session.disconnect();
        }
        this.rakNetClient.close(force);
        this.tickFuture.cancel(false);
    }

    public CompletableFuture<BedrockClientSession> connect(InetSocketAddress address) {
        return this.connect(address, 10, TimeUnit.SECONDS);
    }

    public CompletableFuture<BedrockClientSession> connect(InetSocketAddress address, long timeout, TimeUnit unit) {
        return this.ping(address, timeout, unit).thenApply(pong -> {
            int port;
            if (address.getAddress() instanceof Inet4Address && pong.getIpv4Port() != -1) {
                port = pong.getIpv4Port();
            } else if (address.getAddress() instanceof Inet6Address && pong.getIpv6Port() != -1) {
                port = pong.getIpv6Port();
            } else {
                port = address.getPort();
            }

            return new InetSocketAddress(address.getAddress(), port);
        }).thenCompose(this::directConnect);
    }

    public CompletableFuture<BedrockClientSession> directConnect(InetSocketAddress address) {
        CompletableFuture<BedrockClientSession> future = new CompletableFuture<>();

        RakNetClientSession connection = this.rakNetClient.connect(address);
        BedrockWrapperSerializer serializer = BedrockWrapperSerializers.getSerializer(connection.getProtocolVersion());
        this.session = new BedrockClientSession(connection, connection.getEventLoop(), serializer);
        BedrockRakNetSessionListener.Client listener = new BedrockRakNetSessionListener.Client(this.session,
                connection, this, future);
        connection.setListener(listener);
        return future;
    }

    public CompletableFuture<BedrockPong> ping(InetSocketAddress address) {
        return this.ping(address, 10, TimeUnit.SECONDS);
    }

    public CompletableFuture<BedrockPong> ping(InetSocketAddress address, long timeout, TimeUnit unit) {
        return this.rakNetClient.ping(address, timeout, unit).thenApply(BedrockPong::fromRakNet);
    }

    public BedrockClientSession getSession() {
        return session;
    }
}
