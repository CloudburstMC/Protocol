package com.nukkitx.protocol.bedrock.v388.serializer;

import com.nukkitx.protocol.bedrock.packet.ResourcePackStackPacket;
import com.nukkitx.protocol.bedrock.v388.BedrockUtils;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ResourcePackStackSerializer_v388 implements PacketSerializer<ResourcePackStackPacket> {
    public static final ResourcePackStackSerializer_v388 INSTANCE = new ResourcePackStackSerializer_v388();


    @Override
    public void serialize(ByteBuf buffer, ResourcePackStackPacket packet) {
        buffer.writeBoolean(packet.isForcedToAccept());
        BedrockUtils.writeArray(buffer, packet.getBehaviorPacks(), BedrockUtils::writePackInstanceEntry);
        BedrockUtils.writeArray(buffer, packet.getResourcePacks(), BedrockUtils::writePackInstanceEntry);
        buffer.writeBoolean(packet.isExperimental());
        BedrockUtils.writeString(buffer, packet.getGameVersion());
    }

    @Override
    public void deserialize(ByteBuf buffer, ResourcePackStackPacket packet) {
        packet.setForcedToAccept(buffer.readBoolean());
        BedrockUtils.readArray(buffer, packet.getBehaviorPacks(), BedrockUtils::readPackInstanceEntry);
        BedrockUtils.readArray(buffer, packet.getResourcePacks(), BedrockUtils::readPackInstanceEntry);
        packet.setExperimental(buffer.readBoolean());
        packet.setGameVersion(BedrockUtils.readString(buffer));
    }
}
