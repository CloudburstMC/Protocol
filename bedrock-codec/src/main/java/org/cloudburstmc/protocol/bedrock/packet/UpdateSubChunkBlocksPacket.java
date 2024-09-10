package org.cloudburstmc.protocol.bedrock.packet;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.bedrock.data.BlockChangeEntry;
import org.cloudburstmc.protocol.common.PacketSignal;

import java.util.List;

@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class UpdateSubChunkBlocksPacket implements BedrockPacket {
    private int chunkX;
    private int chunkY;
    private int chunkZ;

    private final List<BlockChangeEntry> standardBlocks = new ObjectArrayList<>();
    private final List<BlockChangeEntry> extraBlocks = new ObjectArrayList<>();

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public BedrockPacketType getPacketType() {
        return BedrockPacketType.UPDATE_SUB_CHUNK_BLOCKS;
    }

    @Override
    public UpdateSubChunkBlocksPacket clone() {
        try {
            return (UpdateSubChunkBlocksPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}

