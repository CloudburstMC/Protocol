package org.cloudburstmc.protocol.bedrock.codec.v419.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.packet.CorrectPlayerMovePredictionPacket;
import org.cloudburstmc.protocol.common.util.VarInts;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class CorrectPlayerMovePredictionSerializer_v419 implements BedrockPacketSerializer<CorrectPlayerMovePredictionPacket> {

    public static final CorrectPlayerMovePredictionSerializer_v419 INSTANCE = new CorrectPlayerMovePredictionSerializer_v419();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, CorrectPlayerMovePredictionPacket packet) {
        helper.writeVector3f(buffer, packet.getPosition());
        helper.writeVector3f(buffer, packet.getDelta());
        buffer.writeBoolean(packet.isOnGround());
        VarInts.writeUnsignedLong(buffer, packet.getTick());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, CorrectPlayerMovePredictionPacket packet) {
        packet.setPosition(helper.readVector3f(buffer));
        packet.setDelta(helper.readVector3f(buffer));
        packet.setOnGround(buffer.readBoolean());
        packet.setTick(VarInts.readUnsignedLong(buffer));
    }
}
