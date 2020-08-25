package com.nukkitx.protocol.bedrock.v409.serializer;

import com.nukkitx.math.vector.Vector3f;
import com.nukkitx.math.vector.Vector3i;
import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.packet.MoveEntityDeltaPacket;
import com.nukkitx.protocol.bedrock.v388.serializer.MoveEntityDeltaSerializer_v388;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MoveEntityDeltaSerializer_v409 extends MoveEntityDeltaSerializer_v388 {
    public static final MoveEntityDeltaSerializer_v409 INSTANCE = new MoveEntityDeltaSerializer_v409();

    private static final int HAS_X = 0x01;
    private static final int HAS_Y = 0x02;
    private static final int HAS_Z = 0x4;
    private static final int HAS_PITCH = 0x8;
    private static final int HAS_YAW = 0x10;
    private static final int HAS_ROLL = 0x20;
    private static final int HAS_UNKNOWN1 = 0x40;
    private static final int HAS_FLOAT_POSITION = 0xFE00;


    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, MoveEntityDeltaPacket packet) {
        VarInts.writeUnsignedLong(buffer, packet.getRuntimeEntityId());
        short flags = 0;

        Vector3f rotationDelta = packet.getRotationDelta();
        flags |= rotationDelta.getX() != 0 ? HAS_PITCH : 0;
        flags |= rotationDelta.getY() != 0 ? HAS_YAW : 0;
        flags |= rotationDelta.getZ() != 0 ? HAS_ROLL : 0;
        flags |= packet.getMovementDeltaF() != null ? HAS_FLOAT_POSITION : 0;

        if (packet.getMovementDeltaF() != null) {
            flags |= packet.getMovementDeltaF().getX() != 0 ? HAS_X : 0;
            flags |= packet.getMovementDeltaF().getY() != 0 ? HAS_Y : 0;
            flags |= packet.getMovementDeltaF().getZ() != 0 ? HAS_Z : 0;
        } else {
            flags |= packet.getMovementDeltaI().getX() != 0 ? HAS_X : 0;
            flags |= packet.getMovementDeltaI().getY() != 0 ? HAS_Y : 0;
            flags |= packet.getMovementDeltaI().getZ() != 0 ? HAS_Z : 0;
        }

        buffer.writeShortLE(flags);
        if (packet.getMovementDeltaF() != null) {
            if ((flags & HAS_X) != 0) {
                buffer.writeFloat(packet.getMovementDeltaF().getX());
            }
            if ((flags & HAS_Y) != 0) {
                buffer.writeFloat(packet.getMovementDeltaF().getY());
            }
            if ((flags & HAS_Z) != 0) {
                buffer.writeFloat(packet.getMovementDeltaF().getZ());
            }
        } else {
            if ((flags & HAS_X) != 0) {
                VarInts.writeInt(buffer, packet.getMovementDeltaI().getX());
            }
            if ((flags & HAS_Y) != 0) {
                VarInts.writeInt(buffer, packet.getMovementDeltaI().getY());
            }
            if ((flags & HAS_Z) != 0) {
                VarInts.writeInt(buffer, packet.getMovementDeltaI().getZ());
            }
        }
        if ((flags & HAS_PITCH) != 0) {
            helper.writeByteAngle(buffer, rotationDelta.getX());
        }
        if ((flags & HAS_YAW) != 0) {
            helper.writeByteAngle(buffer, rotationDelta.getY());
        }
        if ((flags & HAS_ROLL) != 0) {
            helper.writeByteAngle(buffer, rotationDelta.getZ());
        }
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, MoveEntityDeltaPacket packet) {
        packet.setRuntimeEntityId(VarInts.readUnsignedLong(buffer));
        short flags = buffer.readShortLE();
        float pitch = 0, yaw = 0, roll = 0;

        if ((flags & HAS_FLOAT_POSITION) != 0) {
            float x = 0f, y = 0f, z = 0f;

            if ((flags & HAS_X) != 0) {
                x = buffer.readFloat();
            }
            if ((flags & HAS_Y) != 0) {
                y = buffer.readFloat();
            }
            if ((flags & HAS_Z) != 0) {
                z = buffer.readFloat();
            }
            packet.setMovementDelta(Vector3f.from(x, y, z));
        } else {
            int x = 0, y = 0, z = 0;

            if ((flags & HAS_X) != 0) {
                x = VarInts.readInt(buffer);
            }
            if ((flags & HAS_Y) != 0) {
                y = VarInts.readInt(buffer);
            }
            if ((flags & HAS_Z) != 0) {
                z = VarInts.readInt(buffer);
            }
            packet.setMovementDelta(Vector3i.from(x, y, z));
        }

        if ((flags & HAS_PITCH) != 0) {
            pitch = helper.readByteAngle(buffer);
        }
        if ((flags & HAS_YAW) != 0) {
            yaw = helper.readByteAngle(buffer);
        }
        if ((flags & HAS_ROLL) != 0) {
            roll = helper.readByteAngle(buffer);
        }
        packet.setRotationDelta(Vector3f.from(pitch, yaw, roll));
    }
}
