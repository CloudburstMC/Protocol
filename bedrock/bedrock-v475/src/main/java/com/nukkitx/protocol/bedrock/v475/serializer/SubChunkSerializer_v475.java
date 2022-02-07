package com.nukkitx.protocol.bedrock.v475.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.data.HeightMapDataType;
import com.nukkitx.protocol.bedrock.data.SubChunkData;
import com.nukkitx.protocol.bedrock.data.SubChunkRequestResult;
import com.nukkitx.protocol.bedrock.packet.SubChunkPacket;
import com.nukkitx.protocol.bedrock.v471.serializer.SubChunkSerializer_v471;
import io.netty.buffer.ByteBuf;

public class SubChunkSerializer_v475 extends SubChunkSerializer_v471 {
    public static final SubChunkSerializer_v475 INSTANCE = new SubChunkSerializer_v475();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, SubChunkPacket packet) {
        super.serialize(buffer, helper, packet);
        if (packet.isCacheEnabled()) {
            buffer.writeLongLE(packet.getSubChunks().get(0).getBlobId());
        }
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, SubChunkPacket packet) {
        super.deserialize(buffer, helper, packet);
        if (packet.isCacheEnabled()) {
            packet.getSubChunks().get(0).setBlobId(buffer.readLongLE());
        }
    }

    @Override
    protected void serializeSubChunk(ByteBuf buffer, BedrockPacketHelper helper, SubChunkPacket packet, SubChunkData subChunk) {
        helper.writeVector3i(buffer, subChunk.getPosition());
        helper.writeByteArray(buffer, subChunk.getData());
        VarInts.writeInt(buffer, subChunk.getResult().ordinal());
        buffer.writeByte(subChunk.getHeightMapType().ordinal());
        if (subChunk.getHeightMapType() == HeightMapDataType.HAS_DATA) {
            byte[] heightMapBuf = subChunk.getHeightMapData();
            buffer.writeBytes(heightMapBuf, 0, HEIGHT_MAP_LENGTH);
        }
    }

    @Override
    protected SubChunkData deserializeSubChunk(ByteBuf buffer, BedrockPacketHelper helper, SubChunkPacket packet) {
        SubChunkData subChunk = new SubChunkData();
        subChunk.setPosition(helper.readVector3i(buffer));
        subChunk.setData(helper.readByteArray(buffer));
        subChunk.setResult(SubChunkRequestResult.values()[VarInts.readInt(buffer)]);
        subChunk.setHeightMapType(HeightMapDataType.values()[buffer.readByte()]);

        if (subChunk.getHeightMapType() == HeightMapDataType.HAS_DATA) {
            byte[] heightMap = new byte[HEIGHT_MAP_LENGTH];
            buffer.readBytes(heightMap);
            subChunk.setHeightMapData(heightMap);
        }
        return subChunk;
    }
}
