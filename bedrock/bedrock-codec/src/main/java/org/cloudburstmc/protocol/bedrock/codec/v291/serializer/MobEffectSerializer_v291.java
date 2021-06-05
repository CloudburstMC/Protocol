package org.cloudburstmc.protocol.bedrock.codec.v291.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.packet.MobEffectPacket;
import org.cloudburstmc.protocol.common.util.VarInts;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MobEffectSerializer_v291 implements BedrockPacketSerializer<MobEffectPacket> {
    public static final MobEffectSerializer_v291 INSTANCE = new MobEffectSerializer_v291();


    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, MobEffectPacket packet) {
        VarInts.writeUnsignedLong(buffer, packet.getRuntimeEntityId());
        buffer.writeByte(packet.getEvent().ordinal());
        VarInts.writeInt(buffer, packet.getEffectId());
        VarInts.writeInt(buffer, packet.getAmplifier());
        buffer.writeBoolean(packet.isParticles());
        VarInts.writeInt(buffer, packet.getDuration());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, MobEffectPacket packet) {
        packet.setRuntimeEntityId(VarInts.readUnsignedLong(buffer));
        packet.setEvent(MobEffectPacket.Event.values()[buffer.readUnsignedByte()]);
        packet.setEffectId(VarInts.readInt(buffer));
        packet.setAmplifier(VarInts.readInt(buffer));
        packet.setParticles(buffer.readBoolean());
        packet.setDuration(VarInts.readInt(buffer));
    }
}
