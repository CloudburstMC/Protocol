package com.nukkitx.protocol.bedrock.v291.packet;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.ResourcePackDataInfoPacket;
import com.nukkitx.protocol.bedrock.v291.BedrockUtils;
import io.netty.buffer.ByteBuf;

import java.util.UUID;

public class ResourcePackDataInfoPacket_v291 extends ResourcePackDataInfoPacket {

    @Override
    public void encode(ByteBuf buffer) {
        BedrockUtils.writeString(buffer, packId.toString());
        buffer.writeIntLE(maxChunkSize);
        buffer.writeIntLE(chunkCount);
        buffer.writeLongLE(compressedPackSize);
        VarInts.writeUnsignedInt(buffer, hash.length);
        buffer.writeBytes(hash);
    }

    @Override
    public void decode(ByteBuf buffer) {
        packId = UUID.fromString(BedrockUtils.readString(buffer));
        maxChunkSize = buffer.readIntLE();
        chunkCount = buffer.readIntLE();
        compressedPackSize = buffer.readLongLE();
        hash = new byte[VarInts.readUnsignedInt(buffer)];
        buffer.readBytes(hash);
    }
}
