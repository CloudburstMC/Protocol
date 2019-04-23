package com.nukkitx.protocol.bedrock.v354.serializer;

import com.nukkitx.protocol.bedrock.packet.ResourcePacksInfoPacket;
import com.nukkitx.protocol.bedrock.v354.BedrockUtils;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ResourcePacksInfoSerializer_v354 implements PacketSerializer<ResourcePacksInfoPacket> {
    public static final ResourcePacksInfoSerializer_v354 INSTANCE = new ResourcePacksInfoSerializer_v354();

    @Override
    public void serialize(ByteBuf buffer, ResourcePacksInfoPacket packet) {
        buffer.writeBoolean(packet.isForcedToAccept());
        BedrockUtils.writePacksInfoEntries(buffer, packet.getBehaviorPackInfos());
        BedrockUtils.writePacksInfoEntries(buffer, packet.getResourcePackInfos());
        buffer.writeBoolean(packet.isScripting());
    }

    @Override
    public void deserialize(ByteBuf buffer, ResourcePacksInfoPacket packet) {
        packet.setForcedToAccept(buffer.readBoolean());
        packet.getBehaviorPackInfos().addAll(BedrockUtils.readPacksInfoEntries(buffer));
        packet.getResourcePackInfos().addAll(BedrockUtils.readPacksInfoEntries(buffer));
        packet.setScripting(buffer.readBoolean());
    }
}
