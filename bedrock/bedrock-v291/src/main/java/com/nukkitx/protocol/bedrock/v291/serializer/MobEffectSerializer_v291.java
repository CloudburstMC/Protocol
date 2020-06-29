package com.nukkitx.protocol.bedrock.v291.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.packet.MobEffectPacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MobEffectSerializer_v291 implements BedrockPacketSerializer<MobEffectPacket> {
    public static final MobEffectSerializer_v291 INSTANCE = new MobEffectSerializer_v291();


    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, MobEffectPacket packet) {
        VarInts.writeUnsignedLong(buffer, packet.getRuntimeEntityId());
        buffer.writeByte(packet.getEvent().ordinal());
        VarInts.writeInt(buffer, packet.getEffectId());
        VarInts.writeInt(buffer, packet.getAmplifier());
        buffer.writeBoolean(packet.isParticles());
        VarInts.writeInt(buffer, packet.getDuration());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, MobEffectPacket packet) {
        packet.setRuntimeEntityId(VarInts.readUnsignedLong(buffer));
        packet.setEvent(MobEffectPacket.Event.values()[buffer.readUnsignedByte()]);
        packet.setEffectId(VarInts.readInt(buffer));
        packet.setAmplifier(VarInts.readInt(buffer));
        packet.setParticles(buffer.readBoolean());
        packet.setDuration(VarInts.readInt(buffer));
    }
}
