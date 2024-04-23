package org.cloudburstmc.protocol.bedrock.data;

/**
 * Describes who receives a packet.
 */
public enum PacketRecipient {
    /**
     * The packet is sent to the client.
     */
    CLIENT,
    /**
     * The packet is sent to the server.
     */
    SERVER,
    /**
     * The packet is sent to both the client and the server.
     */
    BOTH
}
