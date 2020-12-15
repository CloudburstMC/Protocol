package com.nukkitx.protocol.bedrock;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.net.InetSocketAddress;

@ParametersAreNonnullByDefault
public interface BedrockServerEventHandler {

    boolean onConnectionRequest(InetSocketAddress address);

    @Nullable
    BedrockPong onQuery(InetSocketAddress address);

    void onSessionCreation(BedrockServerSession serverSession);

    default void onUnhandledDatagram(ChannelHandlerContext ctx, DatagramPacket packet) {
    }
}