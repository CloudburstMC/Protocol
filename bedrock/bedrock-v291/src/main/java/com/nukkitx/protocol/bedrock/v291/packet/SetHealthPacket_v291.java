package com.nukkitx.protocol.bedrock.v291.packet;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.SetHealthPacket;
import io.netty.buffer.ByteBuf;

public class SetHealthPacket_v291 extends SetHealthPacket {

    @Override
    public void encode(ByteBuf buffer) {
        VarInts.writeInt(buffer, health);
    }

    @Override
    public void decode(ByteBuf buffer) {
        health = VarInts.readInt(buffer);
    }
}
