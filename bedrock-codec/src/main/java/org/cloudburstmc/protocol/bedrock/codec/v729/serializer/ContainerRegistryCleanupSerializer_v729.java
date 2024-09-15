package org.cloudburstmc.protocol.bedrock.codec.v729.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.packet.ContainerRegistryCleanupPacket;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ContainerRegistryCleanupSerializer_v729 implements BedrockPacketSerializer<ContainerRegistryCleanupPacket> {
    public static final ContainerRegistryCleanupSerializer_v729 INSTANCE = new ContainerRegistryCleanupSerializer_v729();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, ContainerRegistryCleanupPacket packet) {
        helper.writeArray(buffer, packet.getContainers(), helper::writeFullContainerName);
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, ContainerRegistryCleanupPacket packet) {
        helper.readArray(buffer, packet.getContainers(), helper::readFullContainerName);
    }
}
