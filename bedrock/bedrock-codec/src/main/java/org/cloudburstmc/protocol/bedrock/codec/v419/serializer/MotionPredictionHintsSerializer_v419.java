package org.cloudburstmc.protocol.bedrock.codec.v419.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.packet.MotionPredictionHintsPacket;
import org.cloudburstmc.protocol.common.util.VarInts;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class MotionPredictionHintsSerializer_v419 implements BedrockPacketSerializer<MotionPredictionHintsPacket> {

    public static final MotionPredictionHintsSerializer_v419 INSTANCE = new MotionPredictionHintsSerializer_v419();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, MotionPredictionHintsPacket packet) {
        VarInts.writeUnsignedLong(buffer, packet.getRuntimeEntityId());
        helper.writeVector3f(buffer, packet.getMotion());
        buffer.writeBoolean(packet.isOnGround());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, MotionPredictionHintsPacket packet) {
        packet.setRuntimeEntityId(VarInts.readUnsignedLong(buffer));
        packet.setMotion(helper.readVector3f(buffer));
        packet.setOnGround(buffer.readBoolean());
    }
}
