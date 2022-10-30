package org.cloudburstmc.protocol.bedrock;

import org.checkerframework.checker.nullness.qual.Nullable;
import org.cloudburstmc.protocol.bedrock.packet.DisconnectPacket;

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
