package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@Data
@EqualsAndHashCode(doNotUseGetters = true, callSuper = false)
public class ResourcePackDataInfoPacket extends BedrockPacket {
    private UUID packId;
    private String packVersion;
    private long maxChunkSize;
    private long chunkCount;
    private long compressedPackSize;
    private byte[] hash;
    private boolean premium;
    private Type type;

    @Override
    public final boolean handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.RESOURCE_PACK_DATA_INFO;
    }

    public enum Type {
        INVALID,
        RESOURCE,
        BEHAVIOR,
        WORLD_TEMPLATE,
        ADDON,
        SKINS,
        CACHED,
        COPY_PROTECTED,
        PERSONA_PIECE
    }
}
