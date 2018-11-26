package com.nukkitx.protocol.bedrock.v291.packet;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.SetTimePacket;
import io.netty.buffer.ByteBuf;

public class SetTimePacket_v291 extends SetTimePacket {

    @Override
    public void encode(ByteBuf buffer) {
        VarInts.writeInt(buffer, time);
    }

    @Override
    public void decode(ByteBuf buffer) {
        time = VarInts.readInt(buffer);
    }
}
