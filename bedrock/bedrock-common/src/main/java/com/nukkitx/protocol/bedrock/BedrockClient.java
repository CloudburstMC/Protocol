package com.nukkitx.protocol.bedrock;

import com.nukkitx.network.raknet.RakNet;
import com.nukkitx.network.raknet.RakNetClient;
import com.nukkitx.network.raknet.RakNetClientSession;
import com.nukkitx.network.util.EventLoops;
import io.netty.channel.EventLoopGroup;

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
    RakNet getRakNet() {
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
        RakNetClientSession connection = this.rakNetClient.connect(address);
        this.session = new BedrockClientSession(connection);
        BedrockRakNetSessionListener.Client listener = new BedrockRakNetSessionListener.Client(this.session, connection, this);
        connection.setListener(listener);
        return listener.future;
    }

    public CompletableFuture<BedrockPong> ping(InetSocketAddress address) {
        return this.rakNetClient.ping(address, 10, TimeUnit.SECONDS).thenApply(BedrockPong::fromRakNet);
    }

    public BedrockClientSession getSession() {
        return session;
    }
}
