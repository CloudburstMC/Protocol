package com.nukkitx.protocol.bedrock.v486.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.packet.LevelChunkPacket;
import com.nukkitx.protocol.bedrock.v361.serializer.LevelChunkSerializer_v361;
import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.longs.LongList;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LevelChunkSerializer_v486 extends LevelChunkSerializer_v361 {
    public static final LevelChunkSerializer_v486 INSTANCE = new LevelChunkSerializer_v486();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, LevelChunkPacket packet) {
        VarInts.writeInt(buffer, packet.getChunkX());
        VarInts.writeInt(buffer, packet.getChunkZ());

        if (!packet.isRequestSubChunks()) {
            VarInts.writeUnsignedInt(buffer, packet.getSubChunksLength());
        } else if (packet.getSubChunkLimit() < 0) {
            VarInts.writeUnsignedInt(buffer, -1);
        } else {
            VarInts.writeUnsignedInt(buffer, -2);
            buffer.writeShortLE(packet.getSubChunkLimit());
        }

        buffer.writeBoolean(packet.isCachingEnabled());
        if (packet.isCachingEnabled()) {
            LongList blobIds = packet.getBlobIds();
            VarInts.writeUnsignedInt(buffer, blobIds.size());

            for (long blobId : blobIds) {
                buffer.writeLongLE(blobId);
            }
        }

        helper.writeByteArray(buffer, packet.getData());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, LevelChunkPacket packet) {
        packet.setChunkX(VarInts.readInt(buffer));
        packet.setChunkZ(VarInts.readInt(buffer));

        int subChunksCount = VarInts.readUnsignedInt(buffer);
        if (subChunksCount >= 0) {
            packet.setSubChunksLength(subChunksCount);
        } else {
            packet.setRequestSubChunks(true);
            if (subChunksCount == -1) {
                packet.setSubChunkLimit(subChunksCount);
            } else if (subChunksCount == -2) {
                packet.setSubChunkLimit(buffer.readUnsignedShortLE());
            }
        }

        packet.setCachingEnabled(buffer.readBoolean());

        if (packet.isCachingEnabled()) {
            LongList blobIds = packet.getBlobIds();
            int length = VarInts.readUnsignedInt(buffer);

            for (int i = 0; i < length; i++) {
                blobIds.add(buffer.readLongLE());
            }
        }
        packet.setData(helper.readByteArray(buffer));
    }
}
