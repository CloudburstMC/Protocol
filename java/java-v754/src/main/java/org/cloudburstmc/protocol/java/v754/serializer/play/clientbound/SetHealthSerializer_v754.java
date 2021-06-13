package org.cloudburstmc.protocol.java.v754.serializer.play.clientbound;

import com.nukkitx.protocol.exception.PacketSerializeException;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.java.JavaPacketHelper;
import org.cloudburstmc.protocol.java.JavaPacketSerializer;
import org.cloudburstmc.protocol.java.packet.play.clientbound.SetHealthPacket;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SetHealthSerializer_v754 implements JavaPacketSerializer<SetHealthPacket> {
    public static final SetHealthSerializer_v754 INSTANCE = new SetHealthSerializer_v754();

    @Override
    public void serialize(ByteBuf buffer, JavaPacketHelper helper, SetHealthPacket packet) throws PacketSerializeException {
        buffer.writeFloat(packet.getHealth());
        helper.writeVarInt(buffer, packet.getFood());
        buffer.writeFloat(packet.getSaturation());
    }

    @Override
    public void deserialize(ByteBuf buffer, JavaPacketHelper helper, SetHealthPacket packet) throws PacketSerializeException {
        packet.setHealth(buffer.readFloat());
        packet.setFood(helper.readVarInt(buffer));
        packet.setSaturation(buffer.readFloat());
    }
}
