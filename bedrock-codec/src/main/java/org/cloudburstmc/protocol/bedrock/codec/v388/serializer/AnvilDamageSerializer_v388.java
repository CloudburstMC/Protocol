package org.cloudburstmc.protocol.bedrock.codec.v388.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.packet.AnvilDamagePacket;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class AnvilDamageSerializer_v388 implements BedrockPacketSerializer<AnvilDamagePacket> {

    public static final AnvilDamageSerializer_v388 INSTANCE = new AnvilDamageSerializer_v388();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, AnvilDamagePacket packet) {
        buffer.writeByte(packet.getDamage());
        helper.writeBlockPosition(buffer, packet.getPosition());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, AnvilDamagePacket packet) {
        packet.setDamage(buffer.readByte());
        packet.setPosition(helper.readBlockPosition(buffer));
    }
}
