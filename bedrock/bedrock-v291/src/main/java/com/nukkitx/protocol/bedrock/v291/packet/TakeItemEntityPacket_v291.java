package com.nukkitx.protocol.bedrock.v291.packet;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.TakeItemEntityPacket;
import io.netty.buffer.ByteBuf;

public class TakeItemEntityPacket_v291 extends TakeItemEntityPacket {

    @Override
    public void encode(ByteBuf buffer) {
        VarInts.writeUnsignedLong(buffer, itemRuntimeEntityId);
        VarInts.writeUnsignedLong(buffer, runtimeEntityId);
    }

    @Override
    public void decode(ByteBuf buffer) {
        itemRuntimeEntityId = VarInts.readUnsignedLong(buffer);
        runtimeEntityId = VarInts.readUnsignedLong(buffer);
    }
}
