package com.nukkitx.protocol.bedrock;

import com.nukkitx.network.raknet.RakNet;
import com.nukkitx.network.raknet.RakNetClient;

import java.net.InetSocketAddress;
import java.util.concurrent.*;

public class BedrockClient extends Bedrock {
    private final RakNetClient rakNetClient;
    BedrockClientSession session;

    public BedrockClient(InetSocketAddress bindAddress) {
        this(bindAddress, Executors.newSingleThreadScheduledExecutor());
    }

    public BedrockClient(InetSocketAddress bindAddress, ScheduledExecutorService scheduler) {
        this(bindAddress, scheduler, scheduler);
    }

    public BedrockClient(InetSocketAddress bindAddress, ScheduledExecutorService scheduler, Executor executor) {
        super(scheduler, executor);
        this.rakNetClient = new RakNetClient(bindAddress, scheduler, executor);
    }

    @Override
    protected void onTick() {
        if (this.session != null) {
            this.executor.execute(session::onTick);
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
        CompletableFuture<BedrockClientSession> sessionFuture = new CompletableFuture<>();
        this.session = new BedrockClientSession(this.rakNetClient.connect(address), this, sessionFuture);
        return sessionFuture;
    }

    public CompletableFuture<BedrockPong> ping(InetSocketAddress address) {
        return this.rakNetClient.ping(address, 10, TimeUnit.SECONDS).thenApply(BedrockPong::fromRakNet);
    }

    public BedrockClientSession getSession() {
        return session;
    }
}
