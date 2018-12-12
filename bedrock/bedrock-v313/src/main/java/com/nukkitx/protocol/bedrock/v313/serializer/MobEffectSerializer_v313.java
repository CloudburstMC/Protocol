package com.nukkitx.protocol.bedrock.v313.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.MobEffectPacket;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MobEffectSerializer_v313 implements PacketSerializer<MobEffectPacket> {
    public static final MobEffectSerializer_v313 INSTANCE = new MobEffectSerializer_v313();


    @Override
    public void serialize(ByteBuf buffer, MobEffectPacket packet) {
        VarInts.writeUnsignedLong(buffer, packet.getRuntimeEntityId());
        buffer.writeByte(packet.getEvent().ordinal());
        VarInts.writeInt(buffer, packet.getEffectId());
        VarInts.writeInt(buffer, packet.getAmplifier());
        buffer.writeBoolean(packet.isParticles());
        VarInts.writeInt(buffer, packet.getDuration());
    }

    @Override
    public void deserialize(ByteBuf buffer, MobEffectPacket packet) {
        packet.setRuntimeEntityId(VarInts.readUnsignedLong(buffer));
        packet.setEvent(MobEffectPacket.Event.values()[buffer.readUnsignedByte()]);
        packet.setEffectId(VarInts.readInt(buffer));
        packet.setAmplifier(VarInts.readInt(buffer));
        packet.setParticles(buffer.readBoolean());
        packet.setDuration(VarInts.readInt(buffer));
    }
}
