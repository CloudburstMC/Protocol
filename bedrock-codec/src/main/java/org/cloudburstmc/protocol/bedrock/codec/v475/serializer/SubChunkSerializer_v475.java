package org.cloudburstmc.protocol.bedrock.codec.v475.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v471.serializer.SubChunkSerializer_v471;
import org.cloudburstmc.protocol.bedrock.data.HeightMapDataType;
import org.cloudburstmc.protocol.bedrock.data.SubChunkData;
import org.cloudburstmc.protocol.bedrock.data.SubChunkRequestResult;
import org.cloudburstmc.protocol.bedrock.packet.SubChunkPacket;
import org.cloudburstmc.protocol.common.util.VarInts;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SubChunkSerializer_v475 extends SubChunkSerializer_v471 {
    public static final SubChunkSerializer_v475 INSTANCE = new SubChunkSerializer_v475();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, SubChunkPacket packet) {
        super.serialize(buffer, helper, packet);
        buffer.writeBoolean(packet.isCacheEnabled());
        if (packet.isCacheEnabled()) {
            buffer.writeLongLE(packet.getSubChunks().get(0).getBlobId());
        }
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, SubChunkPacket packet) {
        super.deserialize(buffer, helper, packet);
        packet.setCacheEnabled(buffer.readBoolean());
        if (packet.isCacheEnabled()) {
            packet.getSubChunks().get(0).setBlobId(buffer.readLongLE());
        }
    }

    @Override
    protected void serializeSubChunk(ByteBuf buffer, BedrockCodecHelper helper, SubChunkPacket packet, SubChunkData subChunk) {
        helper.writeVector3i(buffer, subChunk.getPosition());
        helper.writeByteBuf(buffer, subChunk.getData());
        VarInts.writeInt(buffer, subChunk.getResult().ordinal());
        buffer.writeByte(subChunk.getHeightMapType().ordinal());
        if (subChunk.getHeightMapType() == HeightMapDataType.HAS_DATA) {
            ByteBuf heightMapBuf = subChunk.getHeightMapData();
            buffer.writeBytes(heightMapBuf, heightMapBuf.readerIndex(), HEIGHT_MAP_LENGTH);
        }
    }

    @Override
    protected SubChunkData deserializeSubChunk(ByteBuf buffer, BedrockCodecHelper helper, SubChunkPacket packet) {
        SubChunkData subChunk = new SubChunkData();
        subChunk.setPosition(helper.readVector3i(buffer));
        subChunk.setData(helper.readByteBuf(buffer));
        subChunk.setResult(SubChunkRequestResult.values()[VarInts.readInt(buffer)]);
        subChunk.setHeightMapType(HeightMapDataType.values()[buffer.readByte()]);

        if (subChunk.getHeightMapType() == HeightMapDataType.HAS_DATA) {
            subChunk.setHeightMapData(buffer.readRetainedSlice(HEIGHT_MAP_LENGTH));
        }
        return subChunk;
    }
}
