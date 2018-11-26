package com.nukkitx.protocol.bedrock.v291.packet;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.MobEffectPacket;
import io.netty.buffer.ByteBuf;

public class MobEffectPacket_v291 extends MobEffectPacket {

    @Override
    public void encode(ByteBuf buffer) {
        VarInts.writeUnsignedLong(buffer, runtimeEntityId);
        buffer.writeByte(event.ordinal());
        VarInts.writeInt(buffer, effectId);
        VarInts.writeInt(buffer, amplifier);
        buffer.writeBoolean(particles);
        VarInts.writeInt(buffer, duration);
    }

    @Override
    public void decode(ByteBuf buffer) {
        runtimeEntityId = VarInts.readUnsignedLong(buffer);
        event = Event.values()[buffer.readUnsignedByte()];
        effectId = VarInts.readInt(buffer);
        amplifier = VarInts.readInt(buffer);
        particles = buffer.readBoolean();
        duration = VarInts.readInt(buffer);
    }
}
