package org.cloudburstmc.protocol.bedrock.codec.v527.serializer;

import io.netty.buffer.ByteBuf;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v428.serializer.PlayerAuthInputSerializer_v428;
import org.cloudburstmc.protocol.bedrock.data.InputInteractionModel;
import org.cloudburstmc.protocol.bedrock.packet.PlayerAuthInputPacket;
import org.cloudburstmc.protocol.common.util.VarInts;

@NoArgsConstructor
public class PlayerAuthInputSerializer_v527 extends PlayerAuthInputSerializer_v428 {

    protected static final InputInteractionModel[] VALUES = InputInteractionModel.values();

    @Override
    protected void readInteractionModel(ByteBuf buffer, BedrockCodecHelper helper, PlayerAuthInputPacket packet) {
        packet.setInputInteractionModel(VALUES[VarInts.readUnsignedInt(buffer)]);
    }

    @Override
    protected void writeInteractionModel(ByteBuf buffer, BedrockCodecHelper helper, PlayerAuthInputPacket packet) {
        VarInts.writeUnsignedInt(buffer, packet.getInputInteractionModel().ordinal());
    }
}