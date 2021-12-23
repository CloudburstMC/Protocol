package com.nukkitx.protocol.bedrock.v471.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.data.HeightMapDataType;
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
        helper.writeVector3i(buffer, packet.getSubChunkPosition());
        helper.writeByteArray(buffer, packet.getData());
        VarInts.writeInt(buffer, packet.getResult().ordinal());
        buffer.writeByte(packet.getHeightMapType().ordinal());
        buffer.writeBytes(packet.getHeightMapData(), 0, HEIGHT_MAP_LENGTH);
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, SubChunkPacket packet) {
        packet.setDimension(VarInts.readInt(buffer));
        packet.setSubChunkPosition(helper.readVector3i(buffer));
        packet.setData(helper.readByteArray(buffer));
        packet.setResult(SubChunkRequestResult.values()[VarInts.readInt(buffer)]);
        packet.setHeightMapType(HeightMapDataType.values()[buffer.readByte()]);

        byte[] heightMap = new byte[HEIGHT_MAP_LENGTH];
        buffer.readBytes(heightMap);

        packet.setHeightMapData(heightMap);
    }
}
