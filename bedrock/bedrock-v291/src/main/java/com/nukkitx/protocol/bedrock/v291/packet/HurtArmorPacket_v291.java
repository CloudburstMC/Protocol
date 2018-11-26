package com.nukkitx.protocol.bedrock.v291.packet;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.HurtArmorPacket;
import io.netty.buffer.ByteBuf;

public class HurtArmorPacket_v291 extends HurtArmorPacket {

    @Override
    public void encode(ByteBuf buffer) {
        VarInts.writeInt(buffer, health);
    }

    @Override
    public void decode(ByteBuf buffer) {
        health = VarInts.readInt(buffer);
    }
}
