package com.nukkitx.protocol.bedrock;

import com.nukkitx.network.raknet.RakNet;
import io.netty.bootstrap.Bootstrap;

import java.net.InetSocketAddress;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public abstract class Bedrock {
    final Executor executor;

    Bedrock(ScheduledExecutorService scheduler, Executor executor) {
        this.executor = executor;
        scheduler.scheduleAtFixedRate(this::onTick, 50, 50, TimeUnit.MILLISECONDS);
    }

    protected abstract void onTick();

    abstract RakNet getRakNet();

    public InetSocketAddress getBindAddress() {
        return this.getRakNet().getBindAddress();
    }

    public Bootstrap getBootstrap() {
        return this.getRakNet().getBootstrap();
    }

    public CompletableFuture<Void> bind() {
        return this.getRakNet().bind();
    }

    public abstract void close();
}
