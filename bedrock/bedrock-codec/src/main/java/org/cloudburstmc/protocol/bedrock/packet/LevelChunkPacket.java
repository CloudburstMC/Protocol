package org.cloudburstmc.protocol.bedrock.packet;

import io.netty.buffer.ByteBuf;
import io.netty.util.ReferenceCounted;
import it.unimi.dsi.fastutil.longs.LongArrayList;
import it.unimi.dsi.fastutil.longs.LongList;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.common.PacketSignal;

@Data
@ToString(doNotUseGetters = true, exclude = {"data"})
@EqualsAndHashCode(doNotUseGetters = true)
public class LevelChunkPacket implements BedrockPacket, ReferenceCounted {
    private int chunkX;
    private int chunkZ;
    private int subChunksLength;
    private boolean cachingEnabled;
    private final LongList blobIds = new LongArrayList();
    private ByteBuf data;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.LEVEL_CHUNK;
    }

    @Override
    public int refCnt() {
        return this.data == null ? 0 : data.refCnt();
    }

    @Override
    public LevelChunkPacket retain() {
        if (this.data != null) {
            this.data.retain();
        }
        return this;
    }

    @Override
    public LevelChunkPacket retain(int increment) {
        if (this.data != null) {
            this.data.retain(increment);
        }
        return this;
    }

    @Override
    public LevelChunkPacket touch() {
        if (this.data != null) {
            this.data.touch();
        }
        return this;
    }

    @Override
    public LevelChunkPacket touch(Object hint) {
        if (this.data != null) {
            this.data.touch(hint);
        }
        return this;
    }

    @Override
    public boolean release() {
        return this.data == null || this.data.release();
    }

    @Override
    public boolean release(int decrement) {
        return this.data == null || this.data.release(decrement);
    }
}
