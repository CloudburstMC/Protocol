package org.cloudburstmc.protocol.bedrock.codec.v471.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.data.HeightMapDataType;
import org.cloudburstmc.protocol.bedrock.data.SubChunkRequestResult;
import org.cloudburstmc.protocol.bedrock.packet.SubChunkPacket;
import org.cloudburstmc.protocol.common.util.VarInts;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SubChunkSerializer_v471 implements BedrockPacketSerializer<SubChunkPacket> {

    private static final int HEIGHT_MAP_LENGTH = 256;

    public static final SubChunkSerializer_v471 INSTANCE = new SubChunkSerializer_v471();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, SubChunkPacket packet) {
        VarInts.writeInt(buffer, packet.getDimension());
        helper.writeVector3i(buffer, packet.getSubChunkPosition());
        helper.writeByteBuf(buffer, packet.getData());
        VarInts.writeInt(buffer, packet.getResult().ordinal());
        buffer.writeByte(packet.getHeightMapType().ordinal());
        ByteBuf heightMapBuf = packet.getHeightMapData();
        buffer.writeBytes(heightMapBuf, heightMapBuf.readerIndex(), HEIGHT_MAP_LENGTH);
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, SubChunkPacket packet) {
        packet.setDimension(VarInts.readInt(buffer));
        packet.setSubChunkPosition(helper.readVector3i(buffer));
        packet.setData(helper.readByteBuf(buffer));
        packet.setResult(SubChunkRequestResult.values()[VarInts.readInt(buffer)]);
        packet.setHeightMapType(HeightMapDataType.values()[buffer.readByte()]);
        packet.setHeightMapData(buffer.readRetainedSlice(HEIGHT_MAP_LENGTH));
    }
}
