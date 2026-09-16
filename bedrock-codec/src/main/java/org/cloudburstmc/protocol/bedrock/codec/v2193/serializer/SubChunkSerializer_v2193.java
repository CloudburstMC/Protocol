package org.cloudburstmc.protocol.bedrock.codec.v2193.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v2168.serializer.SubChunkSerializer_v2168;
import org.cloudburstmc.protocol.bedrock.data.HeightMapDataType;
import org.cloudburstmc.protocol.bedrock.data.SubChunkData;
import org.cloudburstmc.protocol.common.util.VarInts;

public class SubChunkSerializer_v2193 extends SubChunkSerializer_v2168 {

    public static final SubChunkSerializer_v2193 INSTANCE = new SubChunkSerializer_v2193();

    @Override
    protected void writeHeightMapData(ByteBuf buffer, BedrockCodecHelper helper, SubChunkData subChunk) {
        buffer.writeByte(subChunk.getHeightMapType().ordinal());

        helper.writeOptional(buffer, o -> subChunk.getHeightMapData() != null, subChunk.getHeightMapData(),
                (buf, toWrite) -> {
            ByteBuf heightMapBuf = subChunk.getHeightMapData();
            for (int offset = 0; offset < HEIGHT_MAP_LENGTH; offset += 16) {
                VarInts.writeUnsignedInt(buf, 16);
                buf.writeBytes(heightMapBuf, heightMapBuf.readerIndex() + offset, 16);
            }
        });

        buffer.writeByte(subChunk.getRenderHeightMapType().ordinal());

        helper.writeOptional(buffer, o -> subChunk.getRenderHeightMapData() != null, subChunk.getRenderHeightMapData(),
                (buf, toWrite) -> {
            ByteBuf renderHeightMapBuf = subChunk.getRenderHeightMapData();
            for (int offset = 0; offset < HEIGHT_MAP_LENGTH; offset += 16) {
                VarInts.writeUnsignedInt(buf, 16);
                buf.writeBytes(renderHeightMapBuf, renderHeightMapBuf.readerIndex() + offset, 16);
            }
        });
    }

    @Override
    protected void readHeightMapData(ByteBuf buffer, BedrockCodecHelper helper, SubChunkData subChunk) {
        subChunk.setHeightMapType(HeightMapDataType.values()[buffer.readUnsignedByte()]);
        subChunk.setHeightMapData(helper.readOptional(buffer, null, this::readHeightMap));
        subChunk.setRenderHeightMapType(HeightMapDataType.values()[buffer.readUnsignedByte()]);
        subChunk.setRenderHeightMapData(helper.readOptional(buffer, null, this::readHeightMap));
    }

    private ByteBuf readHeightMap(ByteBuf buf) {
        ByteBuf result = buf.alloc().buffer(HEIGHT_MAP_LENGTH);
        try {
            for (int offset = 0; offset < HEIGHT_MAP_LENGTH; offset += 16) {
                int size = VarInts.readUnsignedInt(buf);
                if (size != 16) {
                    throw new IllegalStateException("Expected height map chunk size of 16, got " + size);
                }
                result.writeBytes(buf, 16);
            }
            return result;
        } catch (Throwable t) {
            result.release();
            throw t;
        }
    }
}
