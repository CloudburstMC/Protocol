package com.nukkitx.protocol.bedrock.v363.serializer;

import com.nukkitx.protocol.bedrock.packet.AnvilDamagePacket;
import com.nukkitx.protocol.bedrock.v363.BedrockUtils;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class AnvilDamageSerializer_v363 implements PacketSerializer<AnvilDamagePacket> {

    public static final AnvilDamageSerializer_v363 INSTANCE = new AnvilDamageSerializer_v363();

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
