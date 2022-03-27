package org.cloudburstmc.protocol.bedrock.codec.v486.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.math.vector.Vector3i;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v475.serializer.SubChunkSerializer_v475;
import org.cloudburstmc.protocol.bedrock.data.HeightMapDataType;
import org.cloudburstmc.protocol.bedrock.data.SubChunkData;
import org.cloudburstmc.protocol.bedrock.data.SubChunkRequestResult;
import org.cloudburstmc.protocol.bedrock.packet.SubChunkPacket;
import org.cloudburstmc.protocol.common.util.VarInts;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SubChunkSerializer_v486 extends SubChunkSerializer_v475 {

    public static final SubChunkSerializer_v486 INSTANCE = new SubChunkSerializer_v486();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, SubChunkPacket packet) {
        buffer.writeBoolean(packet.isCacheEnabled());
        VarInts.writeInt(buffer, packet.getDimension());
        helper.writeVector3i(buffer, packet.getCenterPosition());

        buffer.writeIntLE(packet.getSubChunks().size());
        packet.getSubChunks().forEach(subChunk -> this.serializeSubChunk(buffer, helper, packet, subChunk));
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, SubChunkPacket packet) {
        packet.setCacheEnabled(buffer.readBoolean());
        packet.setDimension(VarInts.readInt(buffer));
        packet.setCenterPosition(helper.readVector3i(buffer));

        int size = buffer.readIntLE(); // Unsigned but realistically, we're not going to read that many.
        for (int i = 0; i < size; i++) {
            packet.getSubChunks().add(this.deserializeSubChunk(buffer, helper, packet));
        }
    }

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
        if (packet.isCacheEnabled()) {
            subChunk.setBlobId(buffer.readLongLE());
        }
        return subChunk;
    }

    protected void writeSubChunkOffset(ByteBuf buffer, Vector3i offsetPosition) {
        buffer.writeByte(offsetPosition.getX());
        buffer.writeByte(offsetPosition.getY());
        buffer.writeByte(offsetPosition.getZ());
    }

    protected Vector3i readSubChunkOffset(ByteBuf buffer) {
        return Vector3i.from(buffer.readByte(), buffer.readByte(), buffer.readByte());
    }
}
