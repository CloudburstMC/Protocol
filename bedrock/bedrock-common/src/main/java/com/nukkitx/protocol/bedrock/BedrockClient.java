package com.nukkitx.protocol.bedrock;

import com.nukkitx.network.raknet.RakNetClient;
import com.nukkitx.network.raknet.RakNetClientSession;
import com.nukkitx.network.util.EventLoops;
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
        this(bindAddress, EventLoops.commonGroup());
    }

    public BedrockClient(InetSocketAddress bindAddress, EventLoopGroup eventLoopGroup) {
        super(eventLoopGroup);
        this.rakNetClient = new RakNetClient(bindAddress, eventLoopGroup);
    }

    @Override
    protected void onTick() {
        if (this.session != null) {
            this.eventLoopGroup.execute(session::onTick);
        }
    }

    @Override
    public RakNetClient getRakNet() {
        return this.rakNetClient;
    }

    @Override
    public void close() {
        if (session != null) {
            session.disconnect();
        }
        rakNetClient.close();
    }

    public CompletableFuture<BedrockClientSession> connect(InetSocketAddress address) {
        CompletableFuture<BedrockClientSession> future = new CompletableFuture<>();

        this.ping(address).whenComplete((pong, throwable) -> {
            if (throwable != null) {
                future.completeExceptionally(throwable);
                return;
            }

            int port;
            if (address.getAddress() instanceof Inet4Address && pong.getIpv4Port() != -1) {
                port = pong.getIpv4Port();
            } else if (address.getAddress() instanceof Inet6Address && pong.getIpv6Port() != -1) {
                port = pong.getIpv6Port();
            } else {
                port = address.getPort();
            }

            InetSocketAddress connectAddress = new InetSocketAddress(address.getAddress(), port);

            RakNetClientSession connection = this.rakNetClient.create(connectAddress);
            this.session = new BedrockClientSession(connection);
            BedrockRakNetSessionListener.Client listener = new BedrockRakNetSessionListener.Client(this.session,
                    connection, this, future);
            connection.setListener(listener);
            connection.connect();
        });
        return future;
    }

    public CompletableFuture<BedrockClientSession> directConnect(InetSocketAddress address) {
        CompletableFuture<BedrockClientSession> future = new CompletableFuture<>();

        RakNetClientSession connection = this.rakNetClient.create(address);
        this.session = new BedrockClientSession(connection);
        BedrockRakNetSessionListener.Client listener = new BedrockRakNetSessionListener.Client(this.session,
                connection, this, future);
        connection.setListener(listener);
        connection.connect();

        return future;
    }

    public CompletableFuture<BedrockPong> ping(InetSocketAddress address) {
        return this.rakNetClient.ping(address, 10, TimeUnit.SECONDS).thenApply(BedrockPong::fromRakNet);
    }

    public BedrockClientSession getSession() {
        return session;
    }
}
