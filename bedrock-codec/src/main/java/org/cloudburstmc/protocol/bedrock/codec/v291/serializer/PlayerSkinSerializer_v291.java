package org.cloudburstmc.protocol.bedrock.codec.v291.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.data.skin.ImageData;
import org.cloudburstmc.protocol.bedrock.data.skin.SerializedSkin;
import org.cloudburstmc.protocol.bedrock.packet.PlayerSkinPacket;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlayerSkinSerializer_v291 implements BedrockPacketSerializer<PlayerSkinPacket> {
    public static final PlayerSkinSerializer_v291 INSTANCE = new PlayerSkinSerializer_v291();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, PlayerSkinPacket packet) {
        helper.writeUuid(buffer, packet.getUuid());
        SerializedSkin skin = packet.getSkin();
        helper.writeString(buffer, skin.getSkinId());
        helper.writeString(buffer, packet.getNewSkinName());
        helper.writeString(buffer, packet.getOldSkinName());
        skin.getSkinData().checkLegacySkinSize();
        helper.writeByteArray(buffer, skin.getSkinData().getImage());
        skin.getCapeData().checkLegacyCapeSize();
        helper.writeByteArray(buffer, skin.getCapeData().getImage());
        helper.writeString(buffer, skin.getGeometryName());
        helper.writeString(buffer, skin.getGeometryData());
        buffer.writeBoolean(skin.isPremium());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, PlayerSkinPacket packet) {
        packet.setUuid(helper.readUuid(buffer));
        String skinId = helper.readString(buffer);
        packet.setNewSkinName(helper.readString(buffer));
        packet.setOldSkinName(helper.readString(buffer));
        ImageData skinData = ImageData.of(helper.readByteArray(buffer, ImageData.SKIN_PERSONA_SIZE));
        ImageData capeData = ImageData.of(64, 32, helper.readByteArray(buffer, ImageData.SINGLE_SKIN_SIZE));
        String geometryName = helper.readString(buffer);
        String geometryData = helper.readString(buffer);
        boolean premium = buffer.readBoolean();
        packet.setSkin(SerializedSkin.of(skinId, "", skinData, capeData, geometryName, geometryData, premium));
    }
}
