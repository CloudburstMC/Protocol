package com.nukkitx.protocol.bedrock.v291.packet;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.PacketHeader;
import io.netty.buffer.ByteBuf;

public class PacketHeader_v291 extends PacketHeader {

    @Override
    public void encode(ByteBuf buffer) {
        long header = 0;
        header |= (packetId & 0x3ff);
        header |= (senderId & 3) << 10;
        header |= (clientId & 3) << 12;
        VarInts.writeUnsignedInt(buffer, header);
    }

    @Override
    public void decode(ByteBuf buffer) {
        int header = VarInts.readUnsignedInt(buffer);
        packetId = header & 0x3ff;
        senderId = (header >>> 10) & 3;
        clientId = (header >>> 12) & 3;
    }
}
