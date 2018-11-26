package com.nukkitx.protocol.bedrock.v291.packet;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.SetLastHurtByPacket;
import io.netty.buffer.ByteBuf;

public class SetLastHurtByPacket_v291 extends SetLastHurtByPacket {

    @Override
    public void encode(ByteBuf buffer) {
        VarInts.writeInt(buffer, entityTypeId);
    }

    @Override
    public void decode(ByteBuf buffer) {
        entityTypeId = VarInts.readInt(buffer);
    }
}
