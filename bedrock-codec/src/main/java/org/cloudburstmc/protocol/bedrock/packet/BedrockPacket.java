package org.cloudburstmc.protocol.bedrock.packet;

import org.cloudburstmc.protocol.common.MinecraftPacket;
import org.cloudburstmc.protocol.common.PacketSignal;

public interface BedrockPacket extends MinecraftPacket, Cloneable {

    /**
     * @deprecated Packet-specific handle methods are deprecated. Use {@link BedrockPacketHandler#handlePacket(BedrockPacket)} instead.
     */
    @Deprecated
    default PacketSignal handle(BedrockPacketHandler handler) {
        return PacketSignal.UNHANDLED;
    }

    /**
     * @deprecated {@link BedrockPacketType} enum is deprecated as it cannot be extended for custom packets.
     */
    @Deprecated
    default BedrockPacketType getPacketType() {
        return BedrockPacketType.UNKNOWN;
    }

    /**
     * Creates a new instance of this packet using Object.clone()
     * Will throw a {@link UnsupportedOperationException} if the packet implements {@link io.netty.util.ReferenceCounted}
     * @return a new instance of this packet
     */
    BedrockPacket clone();
}