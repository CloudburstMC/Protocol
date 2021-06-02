package org.cloudburstmc.protocol.java.v754.serializer.play.clientbound;

import com.nukkitx.math.vector.Vector2f;
import com.nukkitx.protocol.exception.PacketSerializeException;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.java.JavaPacketHelper;
import org.cloudburstmc.protocol.java.JavaPacketSerializer;
import org.cloudburstmc.protocol.java.packet.play.clientbound.MoveVehiclePacket;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MoveVehicleSerializer_v754 implements JavaPacketSerializer<MoveVehiclePacket> {
    public static final MoveVehicleSerializer_v754 INSTANCE = new MoveVehicleSerializer_v754();

    @Override
    public void serialize(ByteBuf buffer, JavaPacketHelper helper, MoveVehiclePacket packet) throws PacketSerializeException {
        helper.writePosition(buffer, packet.getPosition());
        // Yes, Java flips these values for whatever reason
        buffer.writeFloat(packet.getRotation().getY());
        buffer.writeFloat(packet.getRotation().getX());
    }

    @Override
    public void deserialize(ByteBuf buffer, JavaPacketHelper helper, MoveVehiclePacket packet) throws PacketSerializeException {
        packet.setPosition(helper.readPosition(buffer));
        float y = buffer.readFloat();
        float x = buffer.readFloat();
        packet.setRotation(Vector2f.from(x, y));
    }
}
