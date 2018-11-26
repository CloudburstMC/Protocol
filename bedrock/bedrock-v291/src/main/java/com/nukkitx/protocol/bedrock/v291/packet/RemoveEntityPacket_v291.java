package com.nukkitx.protocol.bedrock.v291.packet;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.RemoveEntityPacket;
import io.netty.buffer.ByteBuf;

public class RemoveEntityPacket_v291 extends RemoveEntityPacket {

    @Override
    public void encode(ByteBuf buffer) {
        VarInts.writeLong(buffer, uniqueEntityId);
    }

    @Override
    public void decode(ByteBuf buffer) {
        uniqueEntityId = VarInts.readLong(buffer);
    }
}
