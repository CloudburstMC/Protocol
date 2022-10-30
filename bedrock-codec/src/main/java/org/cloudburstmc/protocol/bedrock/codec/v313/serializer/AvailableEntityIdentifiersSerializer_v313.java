package org.cloudburstmc.protocol.bedrock.codec.v313.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.nbt.NbtMap;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.packet.AvailableEntityIdentifiersPacket;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AvailableEntityIdentifiersSerializer_v313 implements BedrockPacketSerializer<AvailableEntityIdentifiersPacket> {
    public static final AvailableEntityIdentifiersSerializer_v313 INSTANCE = new AvailableEntityIdentifiersSerializer_v313();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, AvailableEntityIdentifiersPacket packet) {
        helper.writeTag(buffer, packet.getIdentifiers());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, AvailableEntityIdentifiersPacket packet) {
        packet.setIdentifiers(helper.readTag(buffer, NbtMap.class));
    }
}
