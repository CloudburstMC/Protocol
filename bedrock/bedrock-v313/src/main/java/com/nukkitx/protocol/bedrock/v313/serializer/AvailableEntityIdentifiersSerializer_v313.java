package com.nukkitx.protocol.bedrock.v313.serializer;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.packet.AvailableEntityIdentifiersPacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AvailableEntityIdentifiersSerializer_v313 implements BedrockPacketSerializer<AvailableEntityIdentifiersPacket> {
    public static final AvailableEntityIdentifiersSerializer_v313 INSTANCE = new AvailableEntityIdentifiersSerializer_v313();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, AvailableEntityIdentifiersPacket packet) {
        helper.writeTag(buffer, packet.getIdentifiers());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, AvailableEntityIdentifiersPacket packet) {
        packet.setIdentifiers(helper.readTag(buffer));
    }
}
