package com.nukkitx.protocol.bedrock.v361.serializer;

import com.nukkitx.protocol.bedrock.data.ImageData;
import com.nukkitx.protocol.bedrock.data.SerializedSkin;
import com.nukkitx.protocol.bedrock.packet.PlayerSkinPacket;
import com.nukkitx.protocol.bedrock.v361.BedrockUtils;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PlayerSkinSerializer_v361 implements PacketSerializer<PlayerSkinPacket> {
    public static final PlayerSkinSerializer_v361 INSTANCE = new PlayerSkinSerializer_v361();


    @Override
    public void serialize(ByteBuf buffer, PlayerSkinPacket packet) {
        BedrockUtils.writeUuid(buffer, packet.getUuid());
        SerializedSkin skin = packet.getSkin();
        BedrockUtils.writeString(buffer, skin.getSkinId());
        BedrockUtils.writeString(buffer, packet.getNewSkinName());
        BedrockUtils.writeString(buffer, packet.getOldSkinName());
        skin.getSkinData().checkLegacySkinSize();
        BedrockUtils.writeByteArray(buffer, skin.getSkinData().getImage());
        skin.getCapeData().checkLegacyCapeSize();
        BedrockUtils.writeByteArray(buffer, skin.getCapeData().getImage());
        BedrockUtils.writeString(buffer, skin.getGeometryName());
        BedrockUtils.writeString(buffer, skin.getGeometryData());
        buffer.writeBoolean(skin.isPremium());
    }

    @Override
    public void deserialize(ByteBuf buffer, PlayerSkinPacket packet) {
        packet.setUuid(BedrockUtils.readUuid(buffer));
        String skinId = BedrockUtils.readString(buffer);
        packet.setNewSkinName(BedrockUtils.readString(buffer));
        packet.setOldSkinName(BedrockUtils.readString(buffer));
        ImageData skinData = ImageData.of(BedrockUtils.readByteArray(buffer));
        ImageData capeData = ImageData.of(64, 32, BedrockUtils.readByteArray(buffer));
        String geometryName = BedrockUtils.readString(buffer);
        String geometryData = BedrockUtils.readString(buffer);
        boolean premium = buffer.readBoolean();
        packet.setSkin(SerializedSkin.of(skinId, skinData, capeData, geometryName, geometryData, premium));
    }
}
