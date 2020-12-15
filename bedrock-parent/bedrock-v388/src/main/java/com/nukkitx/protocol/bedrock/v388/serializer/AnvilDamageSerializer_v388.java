package com.nukkitx.protocol.bedrock.v388.serializer;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.packet.AnvilDamagePacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class AnvilDamageSerializer_v388 implements BedrockPacketSerializer<AnvilDamagePacket> {

    public static final AnvilDamageSerializer_v388 INSTANCE = new AnvilDamageSerializer_v388();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, AnvilDamagePacket packet) {
        buffer.writeByte(packet.getDamage());
        helper.writeBlockPosition(buffer, packet.getPosition());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, AnvilDamagePacket packet) {
        packet.setDamage(buffer.readByte());
        packet.setPosition(helper.readBlockPosition(buffer));
    }
}
