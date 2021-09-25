package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import it.unimi.dsi.fastutil.longs.LongArrayList;
import it.unimi.dsi.fastutil.longs.LongList;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import lombok.ToString;

@Data
@ToString(doNotUseGetters = true, exclude = {"data"})
@Accessors(chain = true)
@EqualsAndHashCode(doNotUseGetters = true, callSuper = false)
public class LevelChunkPacket extends BedrockPacket {
    private int chunkX;
    private int chunkZ;
    private int subChunksLength;
    private boolean cachingEnabled;
    private final LongList blobIds = new LongArrayList();
    private byte[] data;

    public LevelChunkPacket addBlobId(long blobId) {
        this.blobIds.add(blobId);
        return this;
    }

    public LevelChunkPacket addBlobIds(long... blobIds) {
        for (long blobId : blobIds) {
            this.blobIds.add(blobId);
        }
        return this;
    }

    @Override
    public final boolean handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.LEVEL_CHUNK;
    }
}
