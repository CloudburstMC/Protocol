package com.nukkitx.protocol.bedrock.v388.serializer;

import com.nukkitx.protocol.bedrock.packet.PlayerSkinPacket;
import com.nukkitx.protocol.bedrock.v388.BedrockUtils;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PlayerSkinSerializer_v388 implements PacketSerializer<PlayerSkinPacket> {
    public static final PlayerSkinSerializer_v388 INSTANCE = new PlayerSkinSerializer_v388();


    @Override
    public void serialize(ByteBuf buffer, PlayerSkinPacket packet) {
        BedrockUtils.writeUuid(buffer, packet.getUuid());
        BedrockUtils.writeSkin(buffer, packet.getSkin());
        BedrockUtils.writeString(buffer, packet.getNewSkinName());
        BedrockUtils.writeString(buffer, packet.getOldSkinName());
    }

    @Override
    public void deserialize(ByteBuf buffer, PlayerSkinPacket packet) {
        packet.setUuid(BedrockUtils.readUuid(buffer));
        packet.setSkin(BedrockUtils.readSkin(buffer));
        packet.setNewSkinName(BedrockUtils.readString(buffer));
        packet.setOldSkinName(BedrockUtils.readString(buffer));
    }
}
