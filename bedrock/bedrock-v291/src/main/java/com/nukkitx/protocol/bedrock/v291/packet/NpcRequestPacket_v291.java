package com.nukkitx.protocol.bedrock.v291.packet;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.NpcRequestPacket;
import com.nukkitx.protocol.bedrock.v291.BedrockUtils;
import io.netty.buffer.ByteBuf;

public class NpcRequestPacket_v291 extends NpcRequestPacket {

    @Override
    public void encode(ByteBuf buffer) {
        VarInts.writeUnsignedLong(buffer, runtimeEntityId);
        buffer.writeByte(requestType.ordinal());
        BedrockUtils.writeString(buffer, command);
        buffer.writeByte(actionType);
    }

    @Override
    public void decode(ByteBuf buffer) {
        runtimeEntityId = VarInts.readUnsignedLong(buffer);
        requestType = Type.values()[buffer.readUnsignedByte()];
        command = BedrockUtils.readString(buffer);
        actionType = buffer.readUnsignedByte();
    }
}
