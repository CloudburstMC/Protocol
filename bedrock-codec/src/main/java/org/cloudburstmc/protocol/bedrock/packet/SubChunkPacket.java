package org.cloudburstmc.protocol.bedrock.packet;

import io.netty.util.AbstractReferenceCounted;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.math.vector.Vector3i;
import org.cloudburstmc.protocol.bedrock.data.SubChunkData;
import org.cloudburstmc.protocol.common.PacketSignal;

import java.util.List;

@Data
@EqualsAndHashCode(doNotUseGetters = true, callSuper = false)
@ToString(doNotUseGetters = true)
public class SubChunkPacket extends AbstractReferenceCounted implements BedrockPacket {
    private int dimension;
    private boolean cacheEnabled;
    /**
     * @since v485
     */
    private Vector3i centerPosition;
    private List<SubChunkData> subChunks = new ObjectArrayList<>();

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.SUB_CHUNK;
    }

    @Override
    public SubChunkPacket touch(Object o) {
        this.subChunks.forEach(SubChunkData::touch);
        return this;
    }

    @Override
    protected void deallocate() {
        this.subChunks.forEach(SubChunkData::release);
    }

    @Override
    public SubChunkPacket clone() {
        throw new UnsupportedOperationException("Can not clone reference counted packet");
    }
}

