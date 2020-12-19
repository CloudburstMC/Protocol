package com.nukkitx.protocol.bedrock;

import com.nukkitx.network.raknet.RakNet;
import com.nukkitx.protocol.MinecraftInterface;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.EventLoopGroup;

import java.net.InetSocketAddress;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public abstract class Bedrock implements MinecraftInterface {
    final EventLoopGroup eventLoopGroup;
    final ScheduledFuture<?> tickFuture;

    Bedrock(EventLoopGroup eventLoopGroup) {
        this.eventLoopGroup = eventLoopGroup;
        this.tickFuture = eventLoopGroup.scheduleAtFixedRate(this::onTick, 50, 50, TimeUnit.MILLISECONDS);
    }

    protected abstract void onTick();

    public abstract RakNet getRakNet();

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
