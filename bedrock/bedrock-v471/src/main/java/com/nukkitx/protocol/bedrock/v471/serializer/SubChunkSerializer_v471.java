package com.nukkitx.protocol.bedrock.v471.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.data.HeightMapDataType;
import com.nukkitx.protocol.bedrock.data.SubChunkData;
import com.nukkitx.protocol.bedrock.data.SubChunkRequestResult;
import com.nukkitx.protocol.bedrock.packet.SubChunkPacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SubChunkSerializer_v471 implements BedrockPacketSerializer<SubChunkPacket> {

    public static final SubChunkSerializer_v471 INSTANCE = new SubChunkSerializer_v471();

    protected static final int HEIGHT_MAP_LENGTH = 256;

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, SubChunkPacket packet) {
        VarInts.writeInt(buffer, packet.getDimension());
        SubChunkData subChunk = packet.getSubChunks().get(0);
        this.serializeSubChunk(buffer, helper, packet, subChunk);
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, SubChunkPacket packet) {
        packet.setDimension(VarInts.readInt(buffer));
        SubChunkData subChunk = this.deserializeSubChunk(buffer, helper, packet);
        packet.getSubChunks().add(subChunk);
    }

    protected void serializeSubChunk(ByteBuf buffer, BedrockPacketHelper helper, SubChunkPacket packet, SubChunkData subChunk) {
        helper.writeVector3i(buffer, subChunk.getPosition());
        helper.writeByteArray(buffer, subChunk.getData());
        VarInts.writeInt(buffer, subChunk.getResult().ordinal());
        buffer.writeByte(subChunk.getHeightMapType().ordinal());
        byte[] heightMapBuf = subChunk.getHeightMapData();
        buffer.writeBytes(heightMapBuf, 0, HEIGHT_MAP_LENGTH);
    }

    protected SubChunkData deserializeSubChunk(ByteBuf buffer, BedrockPacketHelper helper, SubChunkPacket packet) {
        SubChunkData subChunk = new SubChunkData();
        subChunk.setPosition(helper.readVector3i(buffer));
        subChunk.setData(helper.readByteArray(buffer));
        subChunk.setResult(SubChunkRequestResult.values()[VarInts.readInt(buffer)]);
        subChunk.setHeightMapType(HeightMapDataType.values()[buffer.readByte()]);

        byte[] heightMap = new byte[HEIGHT_MAP_LENGTH];
        buffer.readBytes(heightMap);
        subChunk.setHeightMapData(heightMap);
        return subChunk;
    }
}
