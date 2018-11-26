package com.nukkitx.protocol.bedrock.v291.packet;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.RequestChunkRadiusPacket;
import io.netty.buffer.ByteBuf;

public class RequestChunkRadiusPacket_v291 extends RequestChunkRadiusPacket {

    @Override
    public void encode(ByteBuf buffer) {
        VarInts.writeInt(buffer, radius);
    }

    @Override
    public void decode(ByteBuf buffer) {
        radius = VarInts.readInt(buffer);
    }
}
