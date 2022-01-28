package org.cloudburstmc.protocol.bedrock.codec.v485.serializer;

import com.nukkitx.math.vector.Vector3i;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v471.serializer.SubChunkRequestSerializer_v471;
import org.cloudburstmc.protocol.bedrock.packet.SubChunkRequestPacket;
import org.cloudburstmc.protocol.common.util.VarInts;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SubChunkRequestSerializer_v485 extends SubChunkRequestSerializer_v471 {

    public static final SubChunkRequestSerializer_v485 INSTANCE = new SubChunkRequestSerializer_v485();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, SubChunkRequestPacket packet) {
        VarInts.writeInt(buffer, packet.getDimension());
        helper.writeVector3i(buffer, packet.getSubChunkPosition());
        buffer.writeIntLE(packet.getRequestCount());

        buffer.writeShortLE(packet.getPositionOffsets().size());
        packet.getPositionOffsets().forEach(position -> this.writeSubChunkOffset(buffer, position));
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, SubChunkRequestPacket packet) {
        packet.setDimension(VarInts.readInt(buffer));
        packet.setSubChunkPosition(helper.readVector3i(buffer));

        int size = buffer.readUnsignedShortLE();
        for (int i = 0; i < size; i++) {
            packet.getPositionOffsets().add(this.readSubChunkOffset(buffer));
        }
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
