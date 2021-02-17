package com.nukkitx.protocol.bedrock;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.net.InetSocketAddress;

@ParametersAreNonnullByDefault
public interface BedrockServerEventHandler {
    default boolean onConnectionRequest(InetSocketAddress address, InetSocketAddress realAddress) {
        return onConnectionRequest(address);
    }

    default boolean onConnectionRequest(InetSocketAddress address) {
        throw new UnsupportedOperationException("BedrockServerEventHandler#onConnectionRequest is not implemented");
    }

    @Nullable
    BedrockPong onQuery(InetSocketAddress address);

    void onSessionCreation(BedrockServerSession serverSession);

    default void onUnhandledDatagram(ChannelHandlerContext ctx, DatagramPacket packet) {
    }
}