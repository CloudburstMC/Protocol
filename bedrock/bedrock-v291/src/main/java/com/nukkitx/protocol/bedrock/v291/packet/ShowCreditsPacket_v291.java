package com.nukkitx.protocol.bedrock.v291.packet;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.ShowCreditsPacket;
import io.netty.buffer.ByteBuf;

public class ShowCreditsPacket_v291 extends ShowCreditsPacket {

    @Override
    public void encode(ByteBuf buffer) {
        VarInts.writeUnsignedLong(buffer, runtimeEntityId);
        VarInts.writeInt(buffer, status.ordinal());
    }

    @Override
    public void decode(ByteBuf buffer) {
        runtimeEntityId = VarInts.readUnsignedLong(buffer);
        status = Status.values()[VarInts.readInt(buffer)];
    }
}
