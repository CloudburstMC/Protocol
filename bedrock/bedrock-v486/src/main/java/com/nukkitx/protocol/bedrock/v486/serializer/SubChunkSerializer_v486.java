package com.nukkitx.protocol.bedrock.v486.serializer;

import com.nukkitx.math.vector.Vector3i;
import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.data.HeightMapDataType;
import com.nukkitx.protocol.bedrock.data.SubChunkData;
import com.nukkitx.protocol.bedrock.data.SubChunkRequestResult;
import com.nukkitx.protocol.bedrock.packet.SubChunkPacket;
import com.nukkitx.protocol.bedrock.v475.serializer.SubChunkSerializer_v475;
import io.netty.buffer.ByteBuf;

public class SubChunkSerializer_v486 extends SubChunkSerializer_v475 {
    public static final SubChunkSerializer_v486 INSTANCE = new SubChunkSerializer_v486();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, SubChunkPacket packet) {
        buffer.writeBoolean(packet.isCacheEnabled());
        VarInts.writeInt(buffer, packet.getDimension());
        helper.writeVector3i(buffer, packet.getCenterPosition());

        buffer.writeIntLE(packet.getSubChunks().size());
        packet.getSubChunks().forEach(subChunk -> this.serializeSubChunk(buffer, helper, packet, subChunk));
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, SubChunkPacket packet) {
        packet.setCacheEnabled(buffer.readBoolean());
        packet.setDimension(VarInts.readInt(buffer));
        packet.setCenterPosition(helper.readVector3i(buffer));

        int size = buffer.readIntLE(); // Unsigned but realistically, we're not going to read that many.
        for (int i = 0; i < size; i++) {
            packet.getSubChunks().add(this.deserializeSubChunk(buffer, helper, packet));
        }
    }

    @Override
    protected void serializeSubChunk(ByteBuf buffer, BedrockPacketHelper helper, SubChunkPacket packet, SubChunkData subChunk) {
        this.writeSubChunkOffset(buffer, subChunk.getPosition());
        buffer.writeByte(subChunk.getResult().ordinal());
        if (subChunk.getResult() != SubChunkRequestResult.SUCCESS_ALL_AIR || !packet.isCacheEnabled()) {
            helper.writeByteArray(buffer, subChunk.getData());
        }
        buffer.writeByte(subChunk.getHeightMapType().ordinal());
        if (subChunk.getHeightMapType() == HeightMapDataType.HAS_DATA) {
            byte[] heightMapBuf = subChunk.getHeightMapData();
            buffer.writeBytes(heightMapBuf, 0, HEIGHT_MAP_LENGTH);
        }
        if (packet.isCacheEnabled()) {
            buffer.writeLongLE(subChunk.getBlobId());
        }
    }

    @Override
    protected SubChunkData deserializeSubChunk(ByteBuf buffer, BedrockPacketHelper helper, SubChunkPacket packet) {
        SubChunkData subChunk = new SubChunkData();
        subChunk.setPosition(this.readSubChunkOffset(buffer));
        subChunk.setResult(SubChunkRequestResult.values()[buffer.readByte()]);
        if (subChunk.getResult() != SubChunkRequestResult.SUCCESS_ALL_AIR || !packet.isCacheEnabled()) {
            subChunk.setData(helper.readByteArray(buffer));
        }
        subChunk.setHeightMapType(HeightMapDataType.values()[buffer.readByte()]);
        if (subChunk.getHeightMapType() == HeightMapDataType.HAS_DATA) {
            byte[] heightMap = new byte[HEIGHT_MAP_LENGTH];
            buffer.readBytes(heightMap);
            subChunk.setHeightMapData(heightMap);
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
