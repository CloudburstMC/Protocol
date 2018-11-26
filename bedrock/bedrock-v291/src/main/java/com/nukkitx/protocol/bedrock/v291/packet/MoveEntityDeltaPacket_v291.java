package com.nukkitx.protocol.bedrock.v291.packet;

import com.flowpowered.math.vector.Vector3f;
import com.flowpowered.math.vector.Vector3i;
import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.MoveEntityDeltaPacket;
import io.netty.buffer.ByteBuf;

public class MoveEntityDeltaPacket_v291 extends MoveEntityDeltaPacket {
    private static final int HAS_X = 0x01;
    private static final int HAS_Y = 0x02;
    private static final int HAS_Z = 0x4;
    private static final int HAS_PITCH = 0x8;
    private static final int HAS_YAW = 0x10;
    private static final int HAS_ROLL = 0x20;

    @Override
    public void encode(ByteBuf buffer) {
        VarInts.writeUnsignedLong(buffer, runtimeEntityId);
        int flags = 0;
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
            buffer.writeFloatLE(rotationDelta.getX());
        }
        if ((flags & HAS_YAW) != 0) {
            buffer.writeFloatLE(rotationDelta.getY());
        }
        if ((flags & HAS_ROLL) != 0) {
            buffer.writeFloatLE(rotationDelta.getZ());
        }
    }

    @Override
    public void decode(ByteBuf buffer) {
        runtimeEntityId = VarInts.readUnsignedLong(buffer);
        int flags = buffer.readUnsignedByte();
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
            pitch = buffer.readFloatLE();
        }
        if ((flags & HAS_YAW) != 0) {
            yaw = buffer.readFloatLE();
        }
        if ((flags & HAS_ROLL) != 0) {
            roll = buffer.readFloatLE();
        }
        movementDelta = new Vector3i(x, y, z);
        rotationDelta = new Vector3f(pitch, yaw, roll);
    }
}
