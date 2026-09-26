package org.cloudburstmc.protocol.bedrock.codec.v975.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v776.serializer.MovementPredictionSyncSerializer_v776;
import org.cloudburstmc.protocol.bedrock.codec.v786.serializer.MovementPredictionSyncSerializer_v786;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityFlag;
import org.cloudburstmc.protocol.bedrock.packet.MovementPredictionSyncPacket;
import org.cloudburstmc.protocol.common.util.VarInts;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MovementPredictionSyncSerializer_v975 extends MovementPredictionSyncSerializer_v786 {
    public static final MovementPredictionSyncSerializer_v975 INSTANCE = new MovementPredictionSyncSerializer_v975();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, MovementPredictionSyncPacket packet) {
        helper.writeLargeVarIntFlags(buffer, packet.getFlags(), EntityFlag.class);
        helper.writeVector3f(buffer, packet.getBoundingBox());
        buffer.writeFloatLE(packet.getSpeed());
        buffer.writeFloatLE(packet.getUnderwaterSpeed());
        buffer.writeFloatLE(packet.getLavaSpeed());
        buffer.writeFloatLE(packet.getJumpStrength());
        buffer.writeFloatLE(packet.getHealth());
        buffer.writeFloatLE(packet.getHunger());
        buffer.writeFloatLE(packet.getUnknown1());
        buffer.writeFloatLE(packet.getUnknown2());
        buffer.writeFloatLE(packet.getUnknown3());
        VarInts.writeLong(buffer, packet.getUniqueEntityId());
        buffer.writeBoolean(packet.isFlying());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, MovementPredictionSyncPacket packet) {
        helper.readLargeVarIntFlags(buffer, packet.getFlags(), EntityFlag.class);
        packet.setBoundingBox(helper.readVector3f(buffer));
        packet.setSpeed(buffer.readFloatLE());
        packet.setUnderwaterSpeed(buffer.readFloatLE());
        packet.setLavaSpeed(buffer.readFloatLE());
        packet.setJumpStrength(buffer.readFloatLE());
        packet.setHealth(buffer.readFloatLE());
        packet.setHunger(buffer.readFloatLE());
        packet.setUnknown1(buffer.readFloatLE());
        packet.setUnknown2(buffer.readFloatLE());
        packet.setUnknown3(buffer.readFloatLE());
        packet.setUniqueEntityId(VarInts.readLong(buffer));
        packet.setFlying(buffer.readBoolean());
    }
}
