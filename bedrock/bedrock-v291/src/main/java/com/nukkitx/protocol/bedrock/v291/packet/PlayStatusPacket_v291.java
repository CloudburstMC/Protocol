package com.nukkitx.protocol.bedrock.v291.packet;

import com.nukkitx.protocol.bedrock.packet.PlayStatusPacket;
import io.netty.buffer.ByteBuf;

public class PlayStatusPacket_v291 extends PlayStatusPacket {

    @Override
    public void encode(ByteBuf buffer) {
        buffer.writeInt(status.ordinal());
    }

    @Override
    public void decode(ByteBuf buffer) {
        status = Status.values()[buffer.readInt()];
    }
}
