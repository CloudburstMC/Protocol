package org.cloudburstmc.protocol.bedrock;

import org.cloudburstmc.protocol.bedrock.packet.DisconnectPacket;

import javax.annotation.Nullable;

public class BedrockServerSession extends BedrockSession {

    public BedrockServerSession(BedrockPeer peer) {
        super(peer, 0);
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
