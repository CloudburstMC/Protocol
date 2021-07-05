package org.cloudburstmc.protocol.java.v754.serializer.play.clientbound;

import com.nukkitx.protocol.exception.PacketSerializeException;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.java.JavaPacketHelper;
import org.cloudburstmc.protocol.java.JavaPacketSerializer;
import org.cloudburstmc.protocol.java.packet.play.clientbound.UpdateMobEffectPacket;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UpdateMobEffectSerializer_v754 implements JavaPacketSerializer<UpdateMobEffectPacket> {
    public static final UpdateMobEffectSerializer_v754 INSTANCE = new UpdateMobEffectSerializer_v754();

    @Override
    public void serialize(ByteBuf buffer, JavaPacketHelper helper, UpdateMobEffectPacket packet) throws PacketSerializeException {
        helper.writeVarInt(buffer, packet.getEntityId());
        buffer.writeByte(helper.getMobEffectId(packet.getMobEffect()));
        buffer.writeByte(packet.getAmplifier());
        helper.writeVarInt(buffer, packet.getDuration());
        byte flags = 0;
        if (packet.isAmbient()) {
            flags |= 0x01;
        }
        if (packet.isVisible()) {
            flags |= 0x02;
        }
        if (packet.isShowIcon()) {
            flags |= 0x04;
        }
        buffer.writeByte(flags);
    }

    @Override
    public void deserialize(ByteBuf buffer, JavaPacketHelper helper, UpdateMobEffectPacket packet) throws PacketSerializeException {
        packet.setEntityId(helper.readVarInt(buffer));
        packet.setMobEffect(helper.getMobEffect(buffer.readByte()));
        packet.setAmplifier(buffer.readByte());
        packet.setDuration(helper.readVarInt(buffer));
        byte flags = buffer.readByte();
        packet.setAmbient((flags & 0x01) == 0x01);
        packet.setVisible((flags & 0x02) == 0x02);
        packet.setShowIcon((flags & 0x04) == 0x04);
    }
}
