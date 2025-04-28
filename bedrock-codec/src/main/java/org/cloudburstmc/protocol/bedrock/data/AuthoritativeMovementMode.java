package org.cloudburstmc.protocol.bedrock.data;

import org.cloudburstmc.protocol.bedrock.packet.PlayerAuthInputPacket;
import org.cloudburstmc.protocol.bedrock.packet.StartGamePacket;

/**
 * The authoritative movement mode chosen by the server in the {@link StartGamePacket} to verify the clients movement is
 * valid.
 */
public enum AuthoritativeMovementMode {
    /**
     * Movement is completely controlled by the client and does not send {@link PlayerAuthInputPacket}
     * @deprecated v800
     */
    CLIENT,
    /**
     * Movement is verified by the server using the {@link PlayerAuthInputPacket}
     */
    SERVER,
    SERVER_WITH_REWIND
}
