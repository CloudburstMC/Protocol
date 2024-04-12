package org.cloudburstmc.protocol.bedrock.codec.v671.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v649.serializer.CorrectPlayerMovePredictionSerializer_v649;
import org.cloudburstmc.protocol.bedrock.data.PredictionType;
import org.cloudburstmc.protocol.bedrock.packet.CorrectPlayerMovePredictionPacket;
import org.cloudburstmc.protocol.common.util.VarInts;

public class CorrectPlayerMovePredictionSerializer_v671 extends CorrectPlayerMovePredictionSerializer_v649 {
    public static final CorrectPlayerMovePredictionSerializer_v671 INSTANCE = new CorrectPlayerMovePredictionSerializer_v671();

    private static final PredictionType[] PREDICTION_TYPES = PredictionType.values();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, CorrectPlayerMovePredictionPacket packet) {
        buffer.writeByte(packet.getPredictionType().ordinal());
        helper.writeVector3f(buffer, packet.getPosition());
        helper.writeVector3f(buffer, packet.getDelta());
        if (packet.getPredictionType() == PredictionType.VEHICLE) {
            helper.writeVector2f(buffer, packet.getVehicleRotation());
        }
        buffer.writeBoolean(packet.isOnGround());
        VarInts.writeUnsignedLong(buffer, packet.getTick());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, CorrectPlayerMovePredictionPacket packet) {
        packet.setPredictionType(PREDICTION_TYPES[buffer.readUnsignedByte()]);
        packet.setPosition(helper.readVector3f(buffer));
        packet.setDelta(helper.readVector3f(buffer));
        if (packet.getPredictionType() == PredictionType.VEHICLE) {
            packet.setVehicleRotation(helper.readVector2f(buffer));
        }
        packet.setOnGround(buffer.readBoolean());
        packet.setTick(VarInts.readUnsignedInt(buffer));
    }
}
