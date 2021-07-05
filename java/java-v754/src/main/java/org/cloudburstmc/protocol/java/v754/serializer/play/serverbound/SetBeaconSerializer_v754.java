package org.cloudburstmc.protocol.java.v754.serializer.play.serverbound;

import com.nukkitx.protocol.exception.PacketSerializeException;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.java.JavaPacketHelper;
import org.cloudburstmc.protocol.java.JavaPacketSerializer;
import org.cloudburstmc.protocol.java.packet.play.serverbound.SetBeaconPacket;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SetBeaconSerializer_v754 implements JavaPacketSerializer<SetBeaconPacket> {
    public static final SetBeaconSerializer_v754 INSTANCE = new SetBeaconSerializer_v754();

    @Override
    public void serialize(ByteBuf buffer, JavaPacketHelper helper, SetBeaconPacket packet) throws PacketSerializeException {
        helper.writeVarInt(buffer, packet.getPrimary());
        helper.writeVarInt(buffer, packet.getSecondary());
    }

    @Override
    public void deserialize(ByteBuf buffer, JavaPacketHelper helper, SetBeaconPacket packet) throws PacketSerializeException {
        packet.setPrimary(helper.readVarInt(buffer));
        packet.setSecondary(helper.readVarInt(buffer));
    }
}
