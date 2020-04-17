package com.nukkitx.protocol.bedrock.v390.serializer;

import com.nukkitx.protocol.bedrock.packet.PlayerSkinPacket;
import com.nukkitx.protocol.bedrock.v388.BedrockUtils;
import com.nukkitx.protocol.bedrock.v390.BedrockUtils_v390;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PlayerSkinSerializer_v390 implements PacketSerializer<PlayerSkinPacket> {
    public static final PlayerSkinSerializer_v390 INSTANCE = new PlayerSkinSerializer_v390();


    @Override
    public void serialize(ByteBuf buffer, PlayerSkinPacket packet) {
        BedrockUtils.writeUuid(buffer, packet.getUuid());
        BedrockUtils_v390.writeSkin(buffer, packet.getSkin());
        buffer.writeBoolean(packet.isTrustedSkin());
    }

    @Override
    public void deserialize(ByteBuf buffer, PlayerSkinPacket packet) {
        packet.setUuid(BedrockUtils.readUuid(buffer));
        packet.setSkin(BedrockUtils_v390.readSkin(buffer));
        packet.setTrustedSkin(buffer.readBoolean());
    }
}
