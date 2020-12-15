package com.nukkitx.protocol.bedrock.v388.serializer;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.packet.PlayerSkinPacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlayerSkinSerializer_v388 implements BedrockPacketSerializer<PlayerSkinPacket> {
    public static final PlayerSkinSerializer_v388 INSTANCE = new PlayerSkinSerializer_v388();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, PlayerSkinPacket packet) {
        helper.writeUuid(buffer, packet.getUuid());
        helper.writeSkin(buffer, packet.getSkin());
        helper.writeString(buffer, packet.getNewSkinName());
        helper.writeString(buffer, packet.getOldSkinName());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, PlayerSkinPacket packet) {
        packet.setUuid(helper.readUuid(buffer));
        packet.setSkin(helper.readSkin(buffer));
        packet.setNewSkinName(helper.readString(buffer));
        packet.setOldSkinName(helper.readString(buffer));
    }
}
