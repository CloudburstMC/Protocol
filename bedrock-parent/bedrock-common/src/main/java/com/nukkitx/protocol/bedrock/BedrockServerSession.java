package com.nukkitx.protocol.bedrock;

import com.nukkitx.network.raknet.RakNetSession;
import com.nukkitx.protocol.MinecraftServerSession;
import com.nukkitx.protocol.bedrock.packet.DisconnectPacket;
import com.nukkitx.protocol.bedrock.wrapper.BedrockWrapperSerializer;
import io.netty.channel.EventLoop;

import javax.annotation.Nullable;

public class BedrockServerSession extends BedrockSession implements MinecraftServerSession<BedrockPacket> {

    public BedrockServerSession(RakNetSession connection, EventLoop eventLoop, BedrockWrapperSerializer serializer) {
        super(connection, eventLoop, serializer);
    }

    @Override
    public void disconnect() {
        this.disconnect(null, true);
    }

    public void disconnect(@Nullable String reason) {
        this.disconnect(reason, false);
    }

    public void disconnect(@Nullable String reason, boolean hideReason) {
        this.checkForClosed();

        DisconnectPacket packet = new DisconnectPacket();
        if (reason == null || hideReason) {
            packet.setMessageSkipped(true);
            reason = "disconnect.disconnected";
        }
        packet.setKickMessage(reason);
        this.sendPacketImmediately(packet);
    }
}
