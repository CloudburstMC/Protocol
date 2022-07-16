package com.nukkitx.protocol.bedrock.v486.serializer;

import com.nukkitx.math.vector.Vector3i;
import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.packet.SubChunkRequestPacket;
import com.nukkitx.protocol.bedrock.v471.serializer.SubChunkRequestSerializer_v471;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class SubChunkRequestSerializer_v486 extends SubChunkRequestSerializer_v471 {
    public static final SubChunkRequestSerializer_v486 INSTANCE = new SubChunkRequestSerializer_v486();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, SubChunkRequestPacket packet) {
        VarInts.writeInt(buffer, packet.getDimension());
        helper.writeVector3i(buffer, packet.getSubChunkPosition());

        helper.writeArray(buffer, packet.getPositionOffsets(), ByteBuf::writeIntLE, this::writeSubChunkOffset);
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, SubChunkRequestPacket packet) {
        packet.setDimension(VarInts.readInt(buffer));
        packet.setSubChunkPosition(helper.readVector3i(buffer));

        helper.readArray(buffer, packet.getPositionOffsets(), ByteBuf::readIntLE, this::readSubChunkOffset);
    }

    protected void writeSubChunkOffset(ByteBuf buffer, BedrockPacketHelper helper, Vector3i offsetPosition) {
        buffer.writeByte(offsetPosition.getX());
        buffer.writeByte(offsetPosition.getY());
        buffer.writeByte(offsetPosition.getZ());
    }

    protected Vector3i readSubChunkOffset(ByteBuf buffer, BedrockPacketHelper helper) {
        return Vector3i.from(buffer.readByte(), buffer.readByte(), buffer.readByte());
    }
}