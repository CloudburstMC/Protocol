package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.data.BlockChangeEntry;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import lombok.ToString;

import java.util.Arrays;
import java.util.List;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(doNotUseGetters = true, callSuper = false)
@ToString(doNotUseGetters = true)
public class UpdateSubChunkBlocksPacket extends BedrockPacket {

    private int chunkX;
    private int chunkY;
    private int chunkZ;

    private final List<BlockChangeEntry> standardBlocks = new ObjectArrayList<>();
    private final List<BlockChangeEntry> extraBlocks = new ObjectArrayList<>();

    public UpdateSubChunkBlocksPacket addStandardBlock(BlockChangeEntry standardBlock) {
        this.standardBlocks.add(standardBlock);
        return this;
    }

    public UpdateSubChunkBlocksPacket addStandardBlocks(BlockChangeEntry... standardBlocks) {
        this.standardBlocks.addAll(Arrays.asList(standardBlocks));
        return this;
    }

    public UpdateSubChunkBlocksPacket addExtraBlock(BlockChangeEntry extraBlock) {
        this.extraBlocks.add(extraBlock);
        return this;
    }

    public UpdateSubChunkBlocksPacket addExtraBlocks(BlockChangeEntry... extraBlocks) {
        this.extraBlocks.addAll(Arrays.asList(extraBlocks));
        return this;
    }

    @Override
    public boolean handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public BedrockPacketType getPacketType() {
        return BedrockPacketType.UPDATE_SUB_CHUNK_BLOCKS;
    }

}
