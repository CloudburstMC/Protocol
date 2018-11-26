package com.nukkitx.protocol.bedrock.v291.packet;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.MoveEntityAbsolutePacket;
import com.nukkitx.protocol.bedrock.v291.BedrockUtils;
import io.netty.buffer.ByteBuf;

public class MoveEntityAbsolutePacket_v291 extends MoveEntityAbsolutePacket {
    private static final int FLAG_ON_GROUND = 0x1;
    private static final int FLAG_TELEPORTED = 0x2;

    @Override
    public void encode(ByteBuf buffer) {
        VarInts.writeUnsignedLong(buffer, runtimeEntityId);
        int flags = 0;
        if (onGround) {
            flags |= FLAG_ON_GROUND;
        }
        if (teleported) {
            flags |= FLAG_TELEPORTED;
        }
        buffer.writeByte(flags);
        BedrockUtils.writeVector3f(buffer, position);
        BedrockUtils.writeByteRotation(buffer, rotation);
    }

    @Override
    public void decode(ByteBuf buffer) {
        runtimeEntityId = VarInts.readUnsignedLong(buffer);
        int flags = buffer.readUnsignedByte();
        onGround = (flags & FLAG_ON_GROUND) != 0;
        teleported = (flags & FLAG_TELEPORTED) != 0;
        position = BedrockUtils.readVector3f(buffer);
        rotation = BedrockUtils.readByteRotation(buffer);
    }
}
