package org.cloudburstmc.protocol.bedrock.codec.v827.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v712.serializer.CorrectPlayerMovePredictionSerializer_v712;
import org.cloudburstmc.protocol.bedrock.data.PredictionType;
import org.cloudburstmc.protocol.bedrock.packet.CorrectPlayerMovePredictionPacket;
import org.cloudburstmc.protocol.common.util.VarInts;

public class CorrectPlayerMovePredictionSerializer_v827 extends CorrectPlayerMovePredictionSerializer_v712 {

    public static final CorrectPlayerMovePredictionSerializer_v827 INSTANCE = new CorrectPlayerMovePredictionSerializer_v827();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, CorrectPlayerMovePredictionPacket packet) {
        buffer.writeByte(packet.getPredictionType().ordinal());
        helper.writeVector3f(buffer, packet.getPosition());
        helper.writeVector3f(buffer, packet.getDelta());
        this.writeVehiclePrediction(buffer, helper, packet);
        buffer.writeBoolean(packet.isOnGround());
        VarInts.writeUnsignedLong(buffer, packet.getTick());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, CorrectPlayerMovePredictionPacket packet) {
        packet.setPredictionType(PredictionType.values()[buffer.readUnsignedByte()]);
        packet.setPosition(helper.readVector3f(buffer));
        packet.setDelta(helper.readVector3f(buffer));
        this.readVehiclePrediction(buffer, helper, packet);
        packet.setOnGround(buffer.readBoolean());
        packet.setTick(VarInts.readUnsignedLong(buffer));
    }
}
