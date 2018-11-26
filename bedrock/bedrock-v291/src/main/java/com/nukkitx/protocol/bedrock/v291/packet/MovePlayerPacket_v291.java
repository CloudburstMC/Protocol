package com.nukkitx.protocol.bedrock.v291.packet;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.MovePlayerPacket;
import com.nukkitx.protocol.bedrock.v291.BedrockUtils;
import io.netty.buffer.ByteBuf;

public class MovePlayerPacket_v291 extends MovePlayerPacket {

    @Override
    public void encode(ByteBuf buffer) {
        VarInts.writeUnsignedLong(buffer, runtimeEntityId);
        BedrockUtils.writeVector3f(buffer, position);
        BedrockUtils.writeVector3f(buffer, rotation);
        buffer.writeByte(mode.ordinal());
        buffer.writeBoolean(onGround);
        VarInts.writeUnsignedLong(buffer, ridingRuntimeEntityId);
        if (mode == Mode.TELEPORT) {
            buffer.writeIntLE(teleportationCause.ordinal());
            buffer.writeIntLE(unknown0);
        }
    }

    @Override
    public void decode(ByteBuf buffer) {
        runtimeEntityId = VarInts.readUnsignedLong(buffer);
        position = BedrockUtils.readVector3f(buffer);
        rotation = BedrockUtils.readVector3f(buffer);
        mode = Mode.values()[buffer.readByte()];
        onGround = buffer.readBoolean();
        ridingRuntimeEntityId = VarInts.readUnsignedLong(buffer);
        if (mode == Mode.TELEPORT) {
            teleportationCause = TeleportationCause.values()[buffer.readIntLE()];
            unknown0 = buffer.readIntLE();
        }
    }
}
