package com.nukkitx.protocol.bedrock.v340.serializer;

import com.nukkitx.protocol.bedrock.packet.PlayerSkinPacket;
import com.nukkitx.protocol.bedrock.v340.BedrockUtils;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PlayerSkinSerializer_v340 implements PacketSerializer<PlayerSkinPacket> {
    public static final PlayerSkinSerializer_v340 INSTANCE = new PlayerSkinSerializer_v340();


    @Override
    public void serialize(ByteBuf buffer, PlayerSkinPacket packet) {
        BedrockUtils.writeUuid(buffer, packet.getUuid());
        BedrockUtils.writeString(buffer, packet.getSkinId());
        BedrockUtils.writeString(buffer, packet.getNewSkinName());
        BedrockUtils.writeString(buffer, packet.getOldSkinName());
        BedrockUtils.writeByteArray(buffer, packet.getSkinData());
        BedrockUtils.writeByteArray(buffer, packet.getCapeData());
        BedrockUtils.writeString(buffer, packet.getGeometryName());
        BedrockUtils.writeString(buffer, packet.getGeometryData());
        buffer.writeBoolean(packet.isPremiumSkin());
    }

    @Override
    public void deserialize(ByteBuf buffer, PlayerSkinPacket packet) {
        packet.setUuid(BedrockUtils.readUuid(buffer));
        packet.setSkinId(BedrockUtils.readString(buffer));
        packet.setNewSkinName(BedrockUtils.readString(buffer));
        packet.setOldSkinName(BedrockUtils.readString(buffer));
        packet.setSkinData(BedrockUtils.readByteArray(buffer));
        packet.setCapeData(BedrockUtils.readByteArray(buffer));
        packet.setGeometryName(BedrockUtils.readString(buffer));
        packet.setGeometryData(BedrockUtils.readString(buffer));
        packet.setPremiumSkin(buffer.readBoolean());
    }
}
