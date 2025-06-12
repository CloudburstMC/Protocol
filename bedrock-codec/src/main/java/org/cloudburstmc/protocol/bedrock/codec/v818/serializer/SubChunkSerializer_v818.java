package org.cloudburstmc.protocol.bedrock.codec.v818.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v486.serializer.SubChunkSerializer_v486;
import org.cloudburstmc.protocol.bedrock.data.HeightMapDataType;
import org.cloudburstmc.protocol.bedrock.data.SubChunkData;
import org.cloudburstmc.protocol.bedrock.data.SubChunkRequestResult;
import org.cloudburstmc.protocol.bedrock.packet.SubChunkPacket;

public class SubChunkSerializer_v818 extends SubChunkSerializer_v486 {

    public static final SubChunkSerializer_v818 INSTANCE = new SubChunkSerializer_v818();

    @Override
    protected void serializeSubChunk(ByteBuf buffer, BedrockCodecHelper helper, SubChunkPacket packet, SubChunkData subChunk) {
        this.writeSubChunkOffset(buffer, subChunk.getPosition());
        buffer.writeByte(subChunk.getResult().ordinal());
        if (subChunk.getResult() != SubChunkRequestResult.SUCCESS_ALL_AIR || !packet.isCacheEnabled()) {
            helper.writeByteBuf(buffer, subChunk.getData());
        }
        buffer.writeByte(subChunk.getHeightMapType().ordinal());
        if (subChunk.getHeightMapType() == HeightMapDataType.HAS_DATA) {
            ByteBuf heightMapBuf = subChunk.getHeightMapData();
            buffer.writeBytes(heightMapBuf, heightMapBuf.readerIndex(), HEIGHT_MAP_LENGTH);
        }
        buffer.writeByte(subChunk.getRenderHeightMapType().ordinal());
        if (subChunk.getRenderHeightMapType() == HeightMapDataType.HAS_DATA) {
            ByteBuf renderHeightMapBuf = subChunk.getRenderHeightMapData();
            buffer.writeBytes(renderHeightMapBuf, renderHeightMapBuf.readerIndex(), HEIGHT_MAP_LENGTH);
        }
        if (packet.isCacheEnabled()) {
            buffer.writeLongLE(subChunk.getBlobId());
        }
    }

    @Override
    protected SubChunkData deserializeSubChunk(ByteBuf buffer, BedrockCodecHelper helper, SubChunkPacket packet) {
        SubChunkData subChunk = new SubChunkData();
        subChunk.setPosition(this.readSubChunkOffset(buffer));
        subChunk.setResult(SubChunkRequestResult.values()[buffer.readByte()]);
        if (subChunk.getResult() != SubChunkRequestResult.SUCCESS_ALL_AIR || !packet.isCacheEnabled()) {
            subChunk.setData(helper.readByteBuf(buffer));
        }
        subChunk.setHeightMapType(HeightMapDataType.values()[buffer.readByte()]);
        if (subChunk.getHeightMapType() == HeightMapDataType.HAS_DATA) {
            subChunk.setHeightMapData(buffer.readRetainedSlice(HEIGHT_MAP_LENGTH));
        }
        subChunk.setRenderHeightMapType(HeightMapDataType.values()[buffer.readByte()]);
        if (subChunk.getRenderHeightMapType() == HeightMapDataType.HAS_DATA) {
            subChunk.setRenderHeightMapData(buffer.readRetainedSlice(HEIGHT_MAP_LENGTH));
        }
        if (packet.isCacheEnabled()) {
            subChunk.setBlobId(buffer.readLongLE());
        }
        return subChunk;
    }
}
