package com.nukkitx.protocol.bedrock.v291.serializer;

import com.nukkitx.protocol.bedrock.packet.ResourcePackStackPacket;
import com.nukkitx.protocol.bedrock.v291.BedrockUtils;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ResourcePackStackSerializer_v291 implements PacketSerializer<ResourcePackStackPacket> {
    public static final ResourcePackStackSerializer_v291 INSTANCE = new ResourcePackStackSerializer_v291();


    @Override
    public void serialize(ByteBuf buffer, ResourcePackStackPacket packet) {
        buffer.writeBoolean(packet.isForcedToAccept());
        BedrockUtils.writePackInstanceEntries(buffer, packet.getBehaviorPacks());
        BedrockUtils.writePackInstanceEntries(buffer, packet.getResourcePacks());
    }

    @Override
    public void deserialize(ByteBuf buffer, ResourcePackStackPacket packet) {
        packet.setForcedToAccept(buffer.readBoolean());
        packet.getBehaviorPacks().addAll(BedrockUtils.readPackInstanceEntries(buffer));
        packet.getResourcePacks().addAll(BedrockUtils.readPackInstanceEntries(buffer));
    }
}
