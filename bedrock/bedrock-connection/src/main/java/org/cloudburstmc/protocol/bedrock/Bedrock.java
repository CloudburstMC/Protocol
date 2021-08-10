package org.cloudburstmc.protocol.bedrock;

import io.netty.bootstrap.AbstractBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import org.cloudburstmc.protocol.bedrock.raknet.BedrockPeer;
import org.cloudburstmc.protocol.common.MinecraftInterface;

import java.net.InetSocketAddress;
import java.util.concurrent.Future;


public abstract class Bedrock<T extends AbstractBootstrap<?, ?>> implements MinecraftInterface {

    protected InetSocketAddress bindAddress;

    public Bedrock() {
    }

    public Future<Void> bind(InetSocketAddress address) {
        return this.bind(address, f -> {});
    }

    public Future<Void> bind(InetSocketAddress address, ChannelFutureListener listener) {
        if (this.bindAddress != null) {
            throw new IllegalStateException("Already bind");
        }
        this.bindAddress = address;
        return this.doBind(address, listener);
    }

    protected abstract Future<Void> doBind(InetSocketAddress address, ChannelFutureListener listener);

    protected void adjustBootstrap(T bootstrap) {
        // TODO:
    }

    public abstract BedrockPeer<?> constructBedrockPeer(Channel channel);
    protected abstract BedrockSession constructBedrockSession(BedrockPeer<?> peer);

    public void close() {
        this.close(false);
    }

    public abstract void close(boolean force);
    public abstract boolean isClosed();

    @Override
    public InetSocketAddress getBindAddress() {
        return this.bindAddress;
    }
}