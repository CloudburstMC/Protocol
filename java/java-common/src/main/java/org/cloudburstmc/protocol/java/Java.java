package org.cloudburstmc.protocol.java;

import com.nukkitx.protocol.MinecraftInterface;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.EventLoopGroup;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

public abstract class Java implements MinecraftInterface {
    private final InetSocketAddress bindAddress;
    private final Bootstrap bootstrap;

    final EventLoopGroup eventLoopGroup;

    Java(InetSocketAddress bindAddress, EventLoopGroup eventLoopGroup) {
        this.bindAddress = bindAddress;
        this.bootstrap = new Bootstrap();
        this.eventLoopGroup = eventLoopGroup;
        eventLoopGroup.scheduleAtFixedRate(this::onTick, 50, 50, TimeUnit.MILLISECONDS);
    }

    protected abstract void onTick();

    public InetSocketAddress getBindAddress() {
        return this.bindAddress;
    }

    public Bootstrap getBootstrap() {
        return this.bootstrap;
    }

    public abstract void close();
}
