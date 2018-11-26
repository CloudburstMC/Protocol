package com.nukkitx.protocol.bedrock.v291.packet;

import com.nukkitx.protocol.bedrock.packet.ResourcePackChunkRequestPacket;
import com.nukkitx.protocol.bedrock.v291.BedrockUtils;
import io.netty.buffer.ByteBuf;

import java.util.UUID;

public class ResourcePackChunkRequestPacket_v291 extends ResourcePackChunkRequestPacket {

    @Override
    public void encode(ByteBuf buffer) {
        BedrockUtils.writeString(buffer, packId.toString());
        buffer.writeIntLE(chunkIndex);
    }

    @Override
    public void decode(ByteBuf buffer) {
        packId = UUID.fromString(BedrockUtils.readString(buffer));
        chunkIndex = buffer.readIntLE();
    }
}
