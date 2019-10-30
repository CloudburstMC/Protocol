package com.nukkitx.protocol.bedrock.v388.serializer;

import com.nukkitx.protocol.bedrock.packet.AnvilDamagePacket;
import com.nukkitx.protocol.bedrock.v388.BedrockUtils;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class AnvilDamageSerializer_v388 implements PacketSerializer<AnvilDamagePacket> {

    public static final AnvilDamageSerializer_v388 INSTANCE = new AnvilDamageSerializer_v388();

    @Override
    public void serialize(ByteBuf buffer, AnvilDamagePacket packet) {
        buffer.writeByte(packet.getDamage());
        BedrockUtils.writeBlockPosition(buffer, packet.getPosition());
    }

    @Override
    public void deserialize(ByteBuf buffer, AnvilDamagePacket packet) {
        packet.setDamage(buffer.readByte());
        packet.setPosition(BedrockUtils.readBlockPosition(buffer));
    }
}
