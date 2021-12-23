package com.nukkitx.protocol.bedrock.v465.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.data.BlockChangeEntry;
import com.nukkitx.protocol.bedrock.packet.UpdateSubChunkBlocksPacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class UpdateSubChunkBlocksSerializer_v465 implements BedrockPacketSerializer<UpdateSubChunkBlocksPacket> {

    public static final UpdateSubChunkBlocksSerializer_v465 INSTANCE = new UpdateSubChunkBlocksSerializer_v465();

    private static final BlockChangeEntry.MessageType[] VALUES = BlockChangeEntry.MessageType.values();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, UpdateSubChunkBlocksPacket packet) {
        VarInts.writeInt(buffer, packet.getChunkX());
        VarInts.writeUnsignedInt(buffer, packet.getChunkY());
        VarInts.writeInt(buffer, packet.getChunkZ());
        helper.writeArray(buffer, packet.getStandardBlocks(), this::writeBlockChangeEntry);
        helper.writeArray(buffer, packet.getExtraBlocks(), this::writeBlockChangeEntry);
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, UpdateSubChunkBlocksPacket packet) {
        packet.setChunkX(VarInts.readInt(buffer));
        packet.setChunkY(VarInts.readUnsignedInt(buffer));
        packet.setChunkZ(VarInts.readInt(buffer));
        helper.readArray(buffer, packet.getStandardBlocks(), this::readBlockChangeEntry);
        helper.readArray(buffer, packet.getExtraBlocks(), this::readBlockChangeEntry);
    }

    protected void writeBlockChangeEntry(ByteBuf buffer, BedrockPacketHelper helper, BlockChangeEntry entry) {
        helper.writeBlockPosition(buffer, entry.getPosition());
        VarInts.writeUnsignedInt(buffer, entry.getRuntimeId());
        VarInts.writeUnsignedInt(buffer, entry.getUpdateFlags());
        VarInts.writeUnsignedLong(buffer, entry.getMessageEntityId());
        VarInts.writeUnsignedInt(buffer, entry.getMessageType().ordinal());
    }

    protected BlockChangeEntry readBlockChangeEntry(ByteBuf buffer, BedrockPacketHelper helper) {
        return new BlockChangeEntry(
                helper.readBlockPosition(buffer),
                VarInts.readUnsignedInt(buffer),
                VarInts.readUnsignedInt(buffer),
                VarInts.readUnsignedLong(buffer),
                VALUES[VarInts.readUnsignedInt(buffer)]
        );
    }
}
