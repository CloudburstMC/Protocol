package org.cloudburstmc.protocol.java.v754.serializer.play.clientbound;

import com.nukkitx.protocol.exception.PacketSerializeException;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.java.JavaPacketHelper;
import org.cloudburstmc.protocol.java.JavaPacketSerializer;
import org.cloudburstmc.protocol.java.packet.play.clientbound.SetPassengersPacket;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SetPassengersSerializer_v754 implements JavaPacketSerializer<SetPassengersPacket> {
    public static final SetPassengersSerializer_v754 INSTANCE = new SetPassengersSerializer_v754();

    @Override
    public void serialize(ByteBuf buffer, JavaPacketHelper helper, SetPassengersPacket packet) throws PacketSerializeException {
        helper.writeVarInt(buffer, packet.getEntityId());
        helper.writeVarInt(buffer, packet.getPassengerIds().length);
        for (int passengerId : packet.getPassengerIds()) {
            helper.writeVarInt(buffer, passengerId);
        }
    }

    @Override
    public void deserialize(ByteBuf buffer, JavaPacketHelper helper, SetPassengersPacket packet) throws PacketSerializeException {
        packet.setEntityId(helper.readVarInt(buffer));
        int[] passengerIds = new int[helper.readVarInt(buffer)];
        for (int i = 0; i < passengerIds.length; i++) {
            passengerIds[i] = helper.readVarInt(buffer);
        }
        packet.setPassengerIds(passengerIds);
    }
}
