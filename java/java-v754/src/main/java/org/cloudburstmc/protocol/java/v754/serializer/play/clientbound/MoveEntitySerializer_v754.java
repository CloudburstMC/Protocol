package org.cloudburstmc.protocol.java.v754.serializer.play.clientbound;

import com.nukkitx.math.vector.Vector2f;
import com.nukkitx.math.vector.Vector3d;
import com.nukkitx.protocol.exception.PacketSerializeException;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.java.JavaPacketHelper;
import org.cloudburstmc.protocol.java.JavaPacketSerializer;
import org.cloudburstmc.protocol.java.packet.play.clientbound.MoveEntityPacket;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MoveEntitySerializer_v754<T extends MoveEntityPacket> implements JavaPacketSerializer<T> {
    public static final MoveEntitySerializer_v754<?> INSTANCE = new MoveEntitySerializer_v754<>();

    @Override
    public void serialize(ByteBuf buffer, JavaPacketHelper helper, T packet) throws PacketSerializeException {
        helper.writeVarInt(buffer, packet.getEntityId());
    }

    @Override
    public void deserialize(ByteBuf buffer, JavaPacketHelper helper, T packet) throws PacketSerializeException {
        packet.setEntityId(helper.readVarInt(buffer));
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class Noop extends MoveEntitySerializer_v754<MoveEntityPacket> {
        public static final Noop INSTANCE = new Noop();
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class Pos extends MoveEntitySerializer_v754<MoveEntityPacket.Pos> {
         public static final Pos INSTANCE = new Pos();

        @Override
        public void serialize(ByteBuf buffer, JavaPacketHelper helper, MoveEntityPacket.Pos packet) throws PacketSerializeException {
            super.serialize(buffer, helper, packet);
            buffer.writeShort((int) (packet.getDelta().getX() * 4096));
            buffer.writeShort((int) (packet.getDelta().getY() * 4096));
            buffer.writeShort((int) (packet.getDelta().getZ() * 4096));
            buffer.writeBoolean(packet.isOnGround());
        }

        @Override
        public void deserialize(ByteBuf buffer, JavaPacketHelper helper, MoveEntityPacket.Pos packet) throws PacketSerializeException {
            super.deserialize(buffer, helper, packet);
            packet.setDelta(Vector3d.from(
                    buffer.readShort() / 4096D,
                    buffer.readShort() / 4096D,
                    buffer.readShort() / 4096D
            ));
            packet.setOnGround(buffer.readBoolean());
        }
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class PosRot extends MoveEntitySerializer_v754<MoveEntityPacket.PosRot> {
        public static final PosRot INSTANCE = new PosRot();

        @Override
        public void serialize(ByteBuf buffer, JavaPacketHelper helper, MoveEntityPacket.PosRot packet) throws PacketSerializeException {
            super.serialize(buffer, helper, packet);
            buffer.writeShort((int) (packet.getDelta().getX() * 4096));
            buffer.writeShort((int) (packet.getDelta().getY() * 4096));
            buffer.writeShort((int) (packet.getDelta().getZ() * 4096));
            buffer.writeByte((byte) (packet.getRotation().getX() * 256 / 300));
            buffer.writeByte((byte) (packet.getRotation().getY() * 256 / 300));
            buffer.writeBoolean(packet.isOnGround());
        }

        @Override
        public void deserialize(ByteBuf buffer, JavaPacketHelper helper, MoveEntityPacket.PosRot packet) throws PacketSerializeException {
            super.deserialize(buffer, helper, packet);
            packet.setDelta(Vector3d.from(
                    buffer.readShort() / 4096D,
                    buffer.readShort() / 4096D,
                    buffer.readShort() / 4096D
            ));
            packet.setRotation(Vector2f.from(
                    buffer.readByte() * 360 / 256F,
                    buffer.readByte() * 360 / 256F
            ));
            packet.setOnGround(buffer.readBoolean());
        }
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class Rot extends MoveEntitySerializer_v754<MoveEntityPacket.Rot> {
        public static final Rot INSTANCE = new Rot();

        @Override
        public void serialize(ByteBuf buffer, JavaPacketHelper helper, MoveEntityPacket.Rot packet) throws PacketSerializeException {
            super.serialize(buffer, helper, packet);
            buffer.writeByte((byte) (packet.getRotation().getX() * 256 / 300));
            buffer.writeByte((byte) (packet.getRotation().getY() * 256 / 300));
            buffer.writeBoolean(packet.isOnGround());
        }

        @Override
        public void deserialize(ByteBuf buffer, JavaPacketHelper helper, MoveEntityPacket.Rot packet) throws PacketSerializeException {
            super.deserialize(buffer, helper, packet);
            packet.setRotation(Vector2f.from(
                    buffer.readByte() * 360 / 256F,
                    buffer.readByte() * 360 / 256F
            ));
            packet.setOnGround(buffer.readBoolean());
        }
    }
}
