package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(exclude = {"data"})
public class ResourcePackChunkDataPacket extends BedrockPacket {
    private UUID packId;
    private String packVersion;
    private int chunkIndex;
    private long progress;
    private byte[] data;

    @Override
    public final boolean handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }
}
