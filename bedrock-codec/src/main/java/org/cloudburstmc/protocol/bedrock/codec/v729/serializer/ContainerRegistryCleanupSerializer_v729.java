package org.cloudburstmc.protocol.bedrock.codec.v729.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.packet.ContainerRegistryCleanupPacket;

public class ContainerRegistryCleanupSerializer_v729 implements BedrockPacketSerializer<ContainerRegistryCleanupPacket> {
    public static final ContainerRegistryCleanupSerializer_v729 INSTANCE = new ContainerRegistryCleanupSerializer_v729();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, ContainerRegistryCleanupPacket packet) {
        helper.writeArray(buffer, packet.getRemovedContainers(), helper::writeFullContainerName);
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, ContainerRegistryCleanupPacket packet) {
        helper.readArray(buffer, packet.getRemovedContainers(), helper::readFullContainerName);
    }
}