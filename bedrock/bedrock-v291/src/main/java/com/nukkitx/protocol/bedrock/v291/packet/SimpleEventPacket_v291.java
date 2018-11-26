package com.nukkitx.protocol.bedrock.v291.packet;

import com.nukkitx.protocol.bedrock.packet.SimpleEventPacket;
import io.netty.buffer.ByteBuf;

public class SimpleEventPacket_v291 extends SimpleEventPacket {

    @Override
    public void encode(ByteBuf buffer) {
        buffer.writeShortLE(event);
    }

    @Override
    public void decode(ByteBuf buffer) {
        event = buffer.readShortLE();
    }
}
