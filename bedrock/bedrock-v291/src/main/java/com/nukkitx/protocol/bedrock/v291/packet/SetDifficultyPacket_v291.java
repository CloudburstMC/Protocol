package com.nukkitx.protocol.bedrock.v291.packet;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.SetDifficultyPacket;
import io.netty.buffer.ByteBuf;

public class SetDifficultyPacket_v291 extends SetDifficultyPacket {

    @Override
    public void encode(ByteBuf buffer) {
        VarInts.writeInt(buffer, difficulty);
    }

    @Override
    public void decode(ByteBuf buffer) {
        difficulty = VarInts.readInt(buffer);
    }
}
