package com.nukkitx.protocol.bedrock.v361.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.LevelChunkPacket;
import com.nukkitx.protocol.bedrock.v361.BedrockUtils;
import com.nukkitx.protocol.serializer.PacketSerializer;
import gnu.trove.list.TLongList;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LevelChunkSerializer_v361 implements PacketSerializer<LevelChunkPacket> {
    public static final LevelChunkSerializer_v361 INSTANCE = new LevelChunkSerializer_v361();


    @Override
    public void serialize(ByteBuf buffer, LevelChunkPacket packet) {
        VarInts.writeInt(buffer, packet.getChunkX());
        VarInts.writeInt(buffer, packet.getChunkZ());
        VarInts.writeUnsignedInt(buffer, packet.getSubChunksLength());
        buffer.writeBoolean(packet.isCachingEnabled());
        if (packet.isCachingEnabled()) {
            TLongList blobIds = packet.getBlobIds();
            VarInts.writeUnsignedInt(buffer, blobIds.size());

            blobIds.forEach(blobId -> {
                buffer.writeLongLE(blobId);
                return true;
            });
        }

        BedrockUtils.writeByteArray(buffer, packet.getData());
    }

    @Override
    public void deserialize(ByteBuf buffer, LevelChunkPacket packet) {
        packet.setChunkX(VarInts.readInt(buffer));
        packet.setChunkZ(VarInts.readInt(buffer));
        packet.setSubChunksLength(VarInts.readUnsignedInt(buffer));
        packet.setCachingEnabled(buffer.readBoolean());

        if (packet.isCachingEnabled()) {
            TLongList blobIds = packet.getBlobIds();
            int length = VarInts.readUnsignedInt(buffer);

            for (int i = 0; i < length; i++) {
                blobIds.add(buffer.readLongLE());
            }
        }
        packet.setData(BedrockUtils.readByteArray(buffer));
    }
}
