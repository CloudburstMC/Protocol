package org.cloudburstmc.protocol.bedrock.codec.v786.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v776.serializer.MovementPredictionSyncSerializer_v776;
import org.cloudburstmc.protocol.bedrock.packet.MovementPredictionSyncPacket;

public class MovementPredictionSyncSerializer_v786 extends MovementPredictionSyncSerializer_v776 {
    public static final MovementPredictionSyncSerializer_v786 INSTANCE = new MovementPredictionSyncSerializer_v786();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, MovementPredictionSyncPacket packet) {
        super.serialize(buffer, helper, packet);
        buffer.writeBoolean(packet.isActorFlyingState());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, MovementPredictionSyncPacket packet) {
        super.deserialize(buffer, helper, packet);
        packet.setActorFlyingState(buffer.readBoolean());
    }
}