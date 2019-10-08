package com.nukkitx.protocol.bedrock.v291.serializer;

import com.nukkitx.math.vector.Vector3f;
import com.nukkitx.math.vector.Vector3i;
import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.MoveEntityDeltaPacket;
import com.nukkitx.protocol.bedrock.v291.BedrockUtils;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MoveEntityDeltaSerializer_v291 implements PacketSerializer<MoveEntityDeltaPacket> {
    public static final MoveEntityDeltaSerializer_v291 INSTANCE = new MoveEntityDeltaSerializer_v291();

    private static final int HAS_X = 0x01;
    private static final int HAS_Y = 0x02;
    private static final int HAS_Z = 0x4;
    private static final int HAS_PITCH = 0x8;
    private static final int HAS_YAW = 0x10;
    private static final int HAS_ROLL = 0x20;

    @Override
    public void serialize(ByteBuf buffer, MoveEntityDeltaPacket packet) {
        VarInts.writeUnsignedLong(buffer, packet.getRuntimeEntityId());
        byte flags = 0;
        Vector3i movementDelta = packet.getMovementDelta();
        Vector3f rotationDelta = packet.getRotationDelta();
        flags |= movementDelta.getX() != 0 ? HAS_X : 0;
        flags |= movementDelta.getY() != 0 ? HAS_Y : 0;
        flags |= movementDelta.getZ() != 0 ? HAS_Z : 0;
        flags |= rotationDelta.getX() != 0 ? HAS_PITCH : 0;
        flags |= rotationDelta.getY() != 0 ? HAS_YAW : 0;
        flags |= rotationDelta.getZ() != 0 ? HAS_ROLL : 0;
        buffer.writeByte(flags);
        if ((flags & HAS_X) != 0) {
            VarInts.writeInt(buffer, movementDelta.getX());
        }
        if ((flags & HAS_Y) != 0) {
            VarInts.writeInt(buffer, movementDelta.getY());
        }
        if ((flags & HAS_Z) != 0) {
            VarInts.writeInt(buffer, movementDelta.getZ());
        }
        if ((flags & HAS_PITCH) != 0) {
            BedrockUtils.writeByteAngle(buffer, rotationDelta.getX());
        }
        if ((flags & HAS_YAW) != 0) {
            BedrockUtils.writeByteAngle(buffer, rotationDelta.getY());
        }
        if ((flags & HAS_ROLL) != 0) {
            BedrockUtils.writeByteAngle(buffer, rotationDelta.getZ());
        }
    }

    @Override
    public void deserialize(ByteBuf buffer, MoveEntityDeltaPacket packet) {
        packet.setRuntimeEntityId(VarInts.readUnsignedLong(buffer));
        byte flags = buffer.readByte();
        int x = 0, y = 0, z = 0;
        float pitch = 0, yaw = 0, roll = 0;
        if ((flags & HAS_X) != 0) {
            x = VarInts.readInt(buffer);
        }
        if ((flags & HAS_Y) != 0) {
            y = VarInts.readInt(buffer);
        }
        if ((flags & HAS_Z) != 0) {
            z = VarInts.readInt(buffer);
        }
        if ((flags & HAS_PITCH) != 0) {
            pitch = BedrockUtils.readByteAngle(buffer);
        }
        if ((flags & HAS_YAW) != 0) {
            yaw = BedrockUtils.readByteAngle(buffer);
        }
        if ((flags & HAS_ROLL) != 0) {
            roll = BedrockUtils.readByteAngle(buffer);
        }
        packet.setMovementDelta(Vector3i.from(x, y, z));
        packet.setRotationDelta(Vector3f.from(pitch, yaw, roll));
    }
}
