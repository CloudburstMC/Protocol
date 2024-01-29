package org.cloudburstmc.protocol.bedrock.codec.v649.serializer;

import io.netty.buffer.ByteBuf;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v527.serializer.PlayerAuthInputSerializer_v527;
import org.cloudburstmc.protocol.bedrock.data.PlayerAuthInputData;
import org.cloudburstmc.protocol.bedrock.packet.PlayerAuthInputPacket;
import org.cloudburstmc.protocol.common.util.VarInts;

/**
 * Use PlayerAuthInputSerializer_v527 here because predictedVehicle was added before analogMoveVector
 */
@NoArgsConstructor
public class PlayerAuthInputSerializer_v649 extends PlayerAuthInputSerializer_v527 {

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, PlayerAuthInputPacket packet) {
        super.serialize(buffer, helper, packet);
        if (packet.getInputData().contains(PlayerAuthInputData.IN_CLIENT_PREDICTED_IN_VEHICLE)) {
            VarInts.writeLong(buffer, packet.getPredictedVehicle());
        }
        helper.writeVector2f(buffer, packet.getAnalogMoveVector());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, PlayerAuthInputPacket packet) {
        super.deserialize(buffer, helper, packet);
        if (packet.getInputData().contains(PlayerAuthInputData.IN_CLIENT_PREDICTED_IN_VEHICLE)) {
            packet.setPredictedVehicle(VarInts.readLong(buffer));
        }
        packet.setAnalogMoveVector(helper.readVector2f(buffer));
    }
}
