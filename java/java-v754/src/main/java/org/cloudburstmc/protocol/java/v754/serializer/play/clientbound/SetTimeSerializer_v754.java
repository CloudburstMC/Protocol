package org.cloudburstmc.protocol.java.v754.serializer.play.clientbound;

import com.nukkitx.protocol.exception.PacketSerializeException;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.java.JavaPacketHelper;
import org.cloudburstmc.protocol.java.JavaPacketSerializer;
import org.cloudburstmc.protocol.java.packet.play.clientbound.SetTimePacket;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SetTimeSerializer_v754 implements JavaPacketSerializer<SetTimePacket> {
    public static final SetTimeSerializer_v754 INSTANCE = new SetTimeSerializer_v754();

    @Override
    public void serialize(ByteBuf buffer, JavaPacketHelper helper, SetTimePacket packet) throws PacketSerializeException {
        buffer.writeLong(packet.getGameTime());
        buffer.writeLong(packet.getDayTime());
    }

    @Override
    public void deserialize(ByteBuf buffer, JavaPacketHelper helper, SetTimePacket packet) throws PacketSerializeException {
        packet.setGameTime(buffer.readLong());
        packet.setDayTime(buffer.readLong());
    }
}
