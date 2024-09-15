package org.cloudburstmc.protocol.bedrock.codec.v712.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v671.serializer.CorrectPlayerMovePredictionSerializer_v671;
import org.cloudburstmc.protocol.bedrock.packet.CorrectPlayerMovePredictionPacket;

public class CorrectPlayerMovePredictionSerializer_v712 extends CorrectPlayerMovePredictionSerializer_v671 {
    public static final CorrectPlayerMovePredictionSerializer_v712 INSTANCE = new CorrectPlayerMovePredictionSerializer_v712();

    @Override
    protected void writeVehiclePrediction(ByteBuf buffer, BedrockCodecHelper helper, CorrectPlayerMovePredictionPacket packet) {
        super.writeVehiclePrediction(buffer, helper, packet);
        helper.writeOptionalNull(buffer, packet.getVehicleAngularVelocity(), ByteBuf::writeFloatLE);
    }

    @Override
    protected void readVehiclePrediction(ByteBuf buffer, BedrockCodecHelper helper, CorrectPlayerMovePredictionPacket packet) {
        super.readVehiclePrediction(buffer, helper, packet);
        packet.setVehicleAngularVelocity(helper.readOptional(buffer, null, ByteBuf::readFloatLE));
    }
}