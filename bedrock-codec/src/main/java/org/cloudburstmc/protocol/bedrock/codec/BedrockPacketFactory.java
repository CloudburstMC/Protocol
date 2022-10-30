package org.cloudburstmc.protocol.bedrock.codec;

import org.cloudburstmc.protocol.bedrock.packet.BedrockPacket;

@FunctionalInterface
public interface BedrockPacketFactory<T extends BedrockPacket> {

    BedrockPacket newInstance();

    @SuppressWarnings("unchecked")
    default Class<BedrockPacket> getPacketClass() {
        return (Class<BedrockPacket>) newInstance().getClass();
    }
}
