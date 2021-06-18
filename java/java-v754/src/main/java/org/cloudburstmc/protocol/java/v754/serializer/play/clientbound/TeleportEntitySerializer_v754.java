package org.cloudburstmc.protocol.java.v754.serializer.play.clientbound;

import com.nukkitx.math.vector.Vector2f;
import com.nukkitx.math.vector.Vector3d;
import com.nukkitx.protocol.exception.PacketSerializeException;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.java.JavaPacketHelper;
import org.cloudburstmc.protocol.java.JavaPacketSerializer;
import org.cloudburstmc.protocol.java.packet.play.clientbound.TeleportEntityPacket;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TeleportEntitySerializer_v754 implements JavaPacketSerializer<TeleportEntityPacket> {
    public static final TeleportEntitySerializer_v754 INSTANCE = new TeleportEntitySerializer_v754();

    @Override
    public void serialize(ByteBuf buffer, JavaPacketHelper helper, TeleportEntityPacket packet) throws PacketSerializeException {
        helper.writeVarInt(buffer, packet.getEntityId());
        buffer.writeDouble(packet.getPosition().getX());
        buffer.writeDouble(packet.getPosition().getY());
        buffer.writeDouble(packet.getPosition().getZ());
        buffer.writeByte((byte) (packet.getRotation().getX() * 256 / 300));
        buffer.writeByte((byte) (packet.getRotation().getY() * 256 / 300));
        buffer.writeBoolean(packet.isOnGround());
    }

    @Override
    public void deserialize(ByteBuf buffer, JavaPacketHelper helper, TeleportEntityPacket packet) throws PacketSerializeException {
        packet.setEntityId(helper.readVarInt(buffer));
        packet.setPosition(Vector3d.from(
                buffer.readDouble(),
                buffer.readDouble(),
                buffer.readDouble()
        ));
        packet.setRotation(Vector2f.from(
                buffer.readByte() * 360 / 256F,
                buffer.readByte() * 360 / 256F
        ));
        packet.setOnGround(buffer.readBoolean());
    }
}
