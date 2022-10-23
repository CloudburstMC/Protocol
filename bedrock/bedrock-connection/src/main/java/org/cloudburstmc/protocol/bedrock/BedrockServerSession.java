package org.cloudburstmc.protocol.bedrock;

import org.cloudburstmc.protocol.bedrock.netty.BedrockPeer;
import org.cloudburstmc.protocol.bedrock.packet.BedrockPacket;
import org.cloudburstmc.protocol.bedrock.packet.DisconnectPacket;
import org.cloudburstmc.protocol.bedrock.wrapper.BedrockWrapperSerializer;
import org.cloudburstmc.protocol.common.MinecraftServerSession;

import javax.annotation.Nullable;

public class BedrockServerSession extends BedrockSession implements MinecraftServerSession<BedrockPacket> {

    public BedrockServerSession(BedrockPeer<?> peer, BedrockWrapperSerializer serializer) {
        super(peer, serializer);
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
