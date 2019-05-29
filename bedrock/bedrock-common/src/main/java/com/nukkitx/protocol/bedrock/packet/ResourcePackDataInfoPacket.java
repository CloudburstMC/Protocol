package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
public class ResourcePackDataInfoPacket extends BedrockPacket {
    private UUID packId;
    private String packVersion;
    private int maxChunkSize;
    private int chunkCount;
    private long compressedPackSize;
    private byte[] hash;

    @Override
    public final boolean handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }
}
