package org.cloudburstmc.protocol.java.v754.serializer.play.clientbound;

import com.nukkitx.protocol.exception.PacketSerializeException;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.java.JavaPacketHelper;
import org.cloudburstmc.protocol.java.JavaPacketSerializer;
import org.cloudburstmc.protocol.java.data.SoundSource;
import org.cloudburstmc.protocol.java.packet.play.clientbound.StopSoundPacket;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StopSoundSerializer_v754 implements JavaPacketSerializer<StopSoundPacket> {
    public static final StopSoundSerializer_v754 INSTANCE = new StopSoundSerializer_v754();

    @Override
    public void serialize(ByteBuf buffer, JavaPacketHelper helper, StopSoundPacket packet) throws PacketSerializeException {
        int flags = 0;
        if (packet.getSoundSource() != null) {
            flags |= 0x01;
        }

        if (packet.getKey() != null) {
            flags |= 0x02;
        }

        buffer.writeByte(flags);
        if (packet.getSoundSource() != null) {
            helper.writeVarInt(buffer, packet.getSoundSource().ordinal());
        }

        if (packet.getKey() != null) {
            helper.writeKey(buffer, packet.getKey());
        }
    }

    @Override
    public void deserialize(ByteBuf buffer, JavaPacketHelper helper, StopSoundPacket packet) throws PacketSerializeException {
        int flags = buffer.readByte();
        if ((flags & 0x01) != 0) {
            packet.setSoundSource(SoundSource.getById(helper.readVarInt(buffer)));
        } else {
            packet.setSoundSource(null);
        }

        if ((flags & 0x02) != 0) {
            packet.setKey(helper.readKey(buffer));
        } else {
            packet.setKey(null);
        }
    }
}
