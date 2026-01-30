package org.cloudburstmc.protocol.bedrock.packet;

import org.cloudburstmc.protocol.common.MinecraftPacket;
import org.cloudburstmc.protocol.common.PacketSignal;

public interface BedrockPacket extends MinecraftPacket, Cloneable {

    PacketSignal handle(BedrockPacketHandler handler);

    BedrockPacketType getPacketType();

    /**
     * Creates a new instance of this packet using Object.clone()
     * Will throw a {@link UnsupportedOperationException} if the packet implements {@link io.netty.util.ReferenceCounted}
     * @return a new instance of this packet
     */
    BedrockPacket clone();
}