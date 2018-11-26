package com.nukkitx.protocol.bedrock.v291.packet;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.MapInfoRequestPacket;
import io.netty.buffer.ByteBuf;

public class MapInfoRequestPacket_v291 extends MapInfoRequestPacket {

    @Override
    public void encode(ByteBuf buffer) {
        VarInts.writeLong(buffer, uniqueMapId);
    }

    @Override
    public void decode(ByteBuf buffer) {
        uniqueMapId = VarInts.readLong(buffer);
    }
}
