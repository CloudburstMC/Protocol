package org.cloudburstmc.protocol.bedrock;

import org.cloudburstmc.protocol.bedrock.packet.DisconnectPacket;

import javax.annotation.Nullable;

public class BedrockServerSession extends BedrockSession {

    public BedrockServerSession(BedrockPeer peer, int subClientId) {
        super(peer, subClientId);
    }

    public void disconnect(@Nullable String reason, boolean hideReason) {
        this.checkForClosed();

        DisconnectPacket packet = new DisconnectPacket();
        if (reason == null || hideReason) {
            packet.setMessageSkipped(true);
            reason = BedrockDisconnectReasons.DISCONNECTED;
        }
        packet.setKickMessage(reason);
        this.sendPacketImmediately(packet);
    }
}
