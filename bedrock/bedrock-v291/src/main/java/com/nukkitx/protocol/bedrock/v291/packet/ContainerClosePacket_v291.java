package com.nukkitx.protocol.bedrock.v291.packet;

import com.nukkitx.protocol.bedrock.packet.ContainerClosePacket;
import io.netty.buffer.ByteBuf;

public class ContainerClosePacket_v291 extends ContainerClosePacket {

    @Override
    public void encode(ByteBuf buffer) {
        buffer.writeByte(windowId);
    }

    @Override
    public void decode(ByteBuf buffer) {
        windowId = buffer.readByte();
    }
}
