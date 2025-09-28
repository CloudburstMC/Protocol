package org.cloudburstmc.protocol.bedrock;

import io.netty.util.internal.SystemPropertyUtil;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.cloudburstmc.protocol.bedrock.packet.DisconnectPacket;

import java.util.concurrent.TimeUnit;

public class BedrockServerSession extends BedrockSession {

    private static final int TIMEOUT_SECONDS = SystemPropertyUtil.getInt("org.cloudburstmc.protocol.bedrock.disconnectTimeout", 10);

    public BedrockServerSession(BedrockPeer peer, int subClientId) {
        super(peer, subClientId);
    }

    public void disconnect(@Nullable CharSequence reason, boolean hideReason) {
        this.checkForClosed();

        DisconnectPacket packet = new DisconnectPacket();
        CharSequence finalReason;
        if (reason == null || hideReason) {
            packet.setMessageSkipped(true);
            finalReason = BedrockDisconnectReasons.DISCONNECTED;
        } else {
            finalReason = reason;
        }
        packet.setKickMessage(finalReason);
        this.sendPacketImmediately(packet);

        if (!this.isSubClient()) {
            this.getPeer().blackholeInboundPackets();
        }

        this.getPeer().channel.eventLoop().schedule(() -> {
            if (this.isConnected()) {
                this.close(finalReason);
            }
        }, TIMEOUT_SECONDS, TimeUnit.SECONDS);
    }
}
