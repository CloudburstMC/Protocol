package org.cloudburstmc.protocol.bedrock.codec.v291.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.packet.ExplodePacket;
import org.cloudburstmc.protocol.common.util.VarInts;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ExplodeSerializer_v291 implements BedrockPacketSerializer<ExplodePacket> {
    public static final ExplodeSerializer_v291 INSTANCE = new ExplodeSerializer_v291();


    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, ExplodePacket packet) {
        helper.writeVector3f(buffer, packet.getPosition());
        VarInts.writeInt(buffer, (int) (packet.getRadius() * 32));

        helper.writeArray(buffer, packet.getRecords(), helper::writeVector3i);
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, ExplodePacket packet) {
        packet.setPosition(helper.readVector3f(buffer));
        packet.setRadius(VarInts.readInt(buffer) / 32f);

        helper.readArray(buffer, packet.getRecords(), helper::readVector3i);
    }
}
