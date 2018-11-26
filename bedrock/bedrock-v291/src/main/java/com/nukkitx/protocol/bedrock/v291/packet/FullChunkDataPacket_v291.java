package com.nukkitx.protocol.bedrock.v291.packet;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.FullChunkDataPacket;
import io.netty.buffer.ByteBuf;

public class FullChunkDataPacket_v291 extends FullChunkDataPacket {

    @Override
    public void encode(ByteBuf buffer) {
        VarInts.writeInt(buffer, chunkX);
        VarInts.writeInt(buffer, chunkZ);
        VarInts.writeUnsignedInt(buffer, data.readableBytes());
        buffer.writeBytes(data);
        data.release();
    }

    @Override
    public void decode(ByteBuf buffer) {
        chunkX = VarInts.readInt(buffer);
        chunkZ = VarInts.readInt(buffer);
        int length = VarInts.readUnsignedInt(buffer);
        data = buffer.readRetainedSlice(length);
    }
}
