package com.nukkitx.protocol.bedrock.v291.packet;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.SetPlayerGameTypePacket;
import io.netty.buffer.ByteBuf;

public class SetPlayerGameTypePacket_v291 extends SetPlayerGameTypePacket {

    @Override
    public void encode(ByteBuf buffer) {
        VarInts.writeInt(buffer, gamemode);
    }

    @Override
    public void decode(ByteBuf buffer) {
        gamemode = VarInts.readInt(buffer);
    }
}
