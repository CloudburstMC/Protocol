package org.cloudburstmc.protocol.bedrock.packet;

import io.netty.buffer.ByteBuf;
import io.netty.util.AbstractReferenceCounted;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.common.PacketSignal;

import java.util.UUID;

@Data
@EqualsAndHashCode(doNotUseGetters = true, callSuper = false)
@ToString(doNotUseGetters = true, exclude = {"data"})
public class ResourcePackChunkDataPacket extends AbstractReferenceCounted implements BedrockPacket {
    private UUID packId;
    private String packVersion;
    private int chunkIndex;
    private long progress;
    private ByteBuf data;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.RESOURCE_PACK_CHUNK_DATA;
    }

    @Override
    protected void deallocate() {
        this.data.release();
    }

    @Override
    public ResourcePackChunkDataPacket touch(Object hint) {
        data.touch(hint);
        return this;
    }

    @Override
    public ResourcePackChunkDataPacket clone() {
        throw new UnsupportedOperationException("Can not clone reference counted packet");
    }
}

