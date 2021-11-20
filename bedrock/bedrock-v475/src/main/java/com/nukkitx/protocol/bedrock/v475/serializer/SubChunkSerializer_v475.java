package com.nukkitx.protocol.bedrock.v475.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.data.HeightMapDataType;
import com.nukkitx.protocol.bedrock.data.SubChunkRequestResult;
import com.nukkitx.protocol.bedrock.packet.SubChunkPacket;
import com.nukkitx.protocol.bedrock.v471.serializer.SubChunkSerializer_v471;
import io.netty.buffer.ByteBuf;

public class SubChunkSerializer_v475 extends SubChunkSerializer_v471 {
    public static final SubChunkSerializer_v475 INSTANCE = new SubChunkSerializer_v475();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, SubChunkPacket packet) {
        VarInts.writeInt(buffer, packet.getDimension());
        helper.writeVector3i(buffer, packet.getSubChunkPosition());
        helper.writeByteArray(buffer, packet.getData());
        VarInts.writeInt(buffer, packet.getResult().ordinal());
        buffer.writeByte(packet.getHeightMapType().ordinal());
        if (packet.getHeightMapType() == HeightMapDataType.HAS_DATA) {
            byte[] heightMapBuf = packet.getHeightMapData();
            buffer.writeBytes(heightMapBuf);
        }
        buffer.writeBoolean(packet.isCacheEnabled());
        if (packet.isCacheEnabled()) {
            buffer.writeLongLE(packet.getBlobId());
        }
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, SubChunkPacket packet) {
        packet.setDimension(VarInts.readInt(buffer));
        packet.setSubChunkPosition(helper.readVector3i(buffer));
        packet.setData(helper.readByteArray(buffer));
        packet.setResult(SubChunkRequestResult.values()[VarInts.readInt(buffer)]);
        packet.setHeightMapType(HeightMapDataType.values()[buffer.readByte()]);

        if (packet.getHeightMapType() == HeightMapDataType.HAS_DATA) {
            ByteBuf heightMapBuffer = buffer.readBytes(buffer.readableBytes());
            byte[] heightMap = new byte[heightMapBuffer.readableBytes()];
            heightMapBuffer.readBytes(heightMap);
        }
        packet.setCacheEnabled(buffer.readBoolean());
        if (packet.isCacheEnabled()) {
            packet.setBlobId(buffer.readLongLE());
        }
    }
}
