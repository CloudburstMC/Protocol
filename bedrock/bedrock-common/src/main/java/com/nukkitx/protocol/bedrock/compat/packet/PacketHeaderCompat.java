package com.nukkitx.protocol.bedrock.compat.packet;

import com.nukkitx.protocol.bedrock.packet.PacketHeader;
import io.netty.buffer.ByteBuf;

public class PacketHeaderCompat extends PacketHeader {
    @Override
    public void encode(ByteBuf buffer) {
        buffer.writeByte(packetId);
    }

    @Override
    public void decode(ByteBuf buffer) {
        packetId = buffer.readByte();
    }
}
