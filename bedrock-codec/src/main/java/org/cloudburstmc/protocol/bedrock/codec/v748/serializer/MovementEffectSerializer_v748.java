package org.cloudburstmc.protocol.bedrock.codec.v748.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.data.MovementEffectType;
import org.cloudburstmc.protocol.bedrock.packet.MovementEffectPacket;
import org.cloudburstmc.protocol.common.util.VarInts;

public class MovementEffectSerializer_v748 implements BedrockPacketSerializer<MovementEffectPacket> {
    public static final MovementEffectSerializer_v748 INSTANCE = new MovementEffectSerializer_v748();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, MovementEffectPacket packet) {
        VarInts.writeUnsignedLong(buffer, packet.getTargetRuntimeID());
        VarInts.writeUnsignedInt(buffer, packet.getEffectType().getId());
        VarInts.writeUnsignedInt(buffer, packet.getEffectDuration());
        VarInts.writeUnsignedLong(buffer, packet.getTick());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, MovementEffectPacket packet) {
        packet.setTargetRuntimeID(VarInts.readUnsignedLong(buffer));
        packet.setEffectType(MovementEffectType.byId(VarInts.readUnsignedInt(buffer)));
        packet.setEffectDuration(VarInts.readUnsignedInt(buffer));
        packet.setTick(VarInts.readUnsignedLong(buffer));
    }
}