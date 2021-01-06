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
        this(bindAddress, EventLoops.commonGroup());
    }

    public BedrockClient(InetSocketAddress bindAddress, EventLoopGroup eventLoopGroup) {
        super(eventLoopGroup);
        this.rakNetClient = new RakNetClient(bindAddress, eventLoopGroup);
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
    public void close() {
        if (session != null) {
            session.disconnect();
        }
        rakNetClient.close();
        tickFuture.cancel(false);
    }

    public CompletableFuture<BedrockClientSession> connect(InetSocketAddress address) {
        return this.ping(address).thenApply(pong -> {
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
        this.session = new BedrockClientSession(connection, this.eventLoopGroup.next(), serializer);
        BedrockRakNetSessionListener.Client listener = new BedrockRakNetSessionListener.Client(this.session,
                connection, this, future);
        connection.setListener(listener);
        return future;
    }

    public CompletableFuture<BedrockPong> ping(InetSocketAddress address) {
        return this.rakNetClient.ping(address, 10, TimeUnit.SECONDS).thenApply(BedrockPong::fromRakNet);
    }

    public BedrockClientSession getSession() {
        return session;
    }
}
