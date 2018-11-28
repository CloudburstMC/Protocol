package com.nukkitx.protocol.bedrock.v291.serializer;

import com.nukkitx.protocol.bedrock.packet.ResourcePacksInfoPacket;
import com.nukkitx.protocol.bedrock.v291.BedrockUtils;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ResourcePacksInfoSerializer_v291 implements PacketSerializer<ResourcePacksInfoPacket> {
    public static final ResourcePacksInfoSerializer_v291 INSTANCE = new ResourcePacksInfoSerializer_v291();


    @Override
    public void serialize(ByteBuf buffer, ResourcePacksInfoPacket packet) {
        buffer.writeBoolean(packet.isForcedToAccept());
        BedrockUtils.writePacksInfoEntries(buffer, packet.getBehaviorPackInfos());
        BedrockUtils.writePacksInfoEntries(buffer, packet.getResourcePackInfos());
    }

    @Override
    public void deserialize(ByteBuf buffer, ResourcePacksInfoPacket packet) {
        packet.setForcedToAccept(buffer.readBoolean());
        packet.getBehaviorPackInfos().addAll(BedrockUtils.readPacksInfoEntries(buffer));
        packet.getResourcePackInfos().addAll(BedrockUtils.readPacksInfoEntries(buffer));
    }
}
