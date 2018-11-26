package com.nukkitx.protocol.bedrock.v291.packet;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.PlayerActionPacket;
import com.nukkitx.protocol.bedrock.v291.BedrockUtils;
import io.netty.buffer.ByteBuf;

public class PlayerActionPacket_v291 extends PlayerActionPacket {

    @Override
    public void encode(ByteBuf buffer) {
        VarInts.writeUnsignedLong(buffer, runtimeEntityId);
        VarInts.writeInt(buffer, action.ordinal());
        BedrockUtils.writeBlockPosition(buffer, blockPosition);
        VarInts.writeInt(buffer, face);
    }

    @Override
    public void decode(ByteBuf buffer) {
        runtimeEntityId = VarInts.readUnsignedLong(buffer);
        action = Action.values()[VarInts.readInt(buffer)];
        blockPosition = BedrockUtils.readBlockPosition(buffer);
        face = VarInts.readInt(buffer);
    }
}
