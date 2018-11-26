package com.nukkitx.protocol.bedrock.v291.packet;

import com.nukkitx.protocol.bedrock.packet.ResourcePackChunkDataPacket;
import com.nukkitx.protocol.bedrock.v291.BedrockUtils;
import io.netty.buffer.ByteBuf;

import java.util.UUID;

public class ResourcePackChunkDataPacket_v291 extends ResourcePackChunkDataPacket {

    @Override
    public void encode(ByteBuf buffer) {
        BedrockUtils.writeString(buffer, packId.toString());
        buffer.writeIntLE(chunkIndex);
        buffer.writeLongLE(progress);
        buffer.writeIntLE(data.length);
        buffer.writeBytes(data);
    }

    @Override
    public void decode(ByteBuf buffer) {
        packId = UUID.fromString(BedrockUtils.readString(buffer));
        chunkIndex = buffer.readIntLE();
        progress = buffer.readLongLE();
        data = new byte[buffer.readIntLE()];
        buffer.readBytes(data);
    }
}
