package org.cloudburstmc.protocol.bedrock.codec.v465.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.data.BlockChangeEntry;
import org.cloudburstmc.protocol.bedrock.packet.UpdateSubChunkBlocksPacket;
import org.cloudburstmc.protocol.common.util.VarInts;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class UpdateSubChunkBlocksSerializer_v465 implements BedrockPacketSerializer<UpdateSubChunkBlocksPacket> {
    public static final UpdateSubChunkBlocksSerializer_v465 INSTANCE = new UpdateSubChunkBlocksSerializer_v465();

    private static final BlockChangeEntry.MessageType[] VALUES = BlockChangeEntry.MessageType.values();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, UpdateSubChunkBlocksPacket packet) {
        VarInts.writeInt(buffer, packet.getChunkX());
        VarInts.writeUnsignedInt(buffer, packet.getChunkY());
        VarInts.writeInt(buffer, packet.getChunkZ());
        helper.writeArray(buffer, packet.getStandardBlocks(), this::writeBlockChangeEntry);
        helper.writeArray(buffer, packet.getExtraBlocks(), this::writeBlockChangeEntry);
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, UpdateSubChunkBlocksPacket packet) {
        packet.setChunkX(VarInts.readInt(buffer));
        packet.setChunkY(VarInts.readUnsignedInt(buffer));
        packet.setChunkZ(VarInts.readInt(buffer));
        helper.readArray(buffer, packet.getStandardBlocks(), this::readBlockChangeEntry);
        helper.readArray(buffer, packet.getExtraBlocks(), this::readBlockChangeEntry);
    }

    protected void writeBlockChangeEntry(ByteBuf buffer, BedrockCodecHelper helper, BlockChangeEntry entry) {
        helper.writeBlockPosition(buffer, entry.getPosition());
        VarInts.writeUnsignedInt(buffer, entry.getDefinition().getRuntimeId());
        VarInts.writeUnsignedInt(buffer, entry.getUpdateFlags());
        VarInts.writeUnsignedLong(buffer, entry.getMessageEntityId());
        VarInts.writeUnsignedInt(buffer, entry.getMessageType().ordinal());
    }

    protected BlockChangeEntry readBlockChangeEntry(ByteBuf buffer, BedrockCodecHelper helper) {
        return new BlockChangeEntry(
                helper.readBlockPosition(buffer),
                helper.getBlockDefinitions().getDefinition(VarInts.readUnsignedInt(buffer)),
                VarInts.readUnsignedInt(buffer),
                VarInts.readUnsignedLong(buffer),
                VALUES[VarInts.readUnsignedInt(buffer)]
        );
    }
}
