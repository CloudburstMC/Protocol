package org.cloudburstmc.protocol.bedrock.codec.v471.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.packet.SubChunkRequestPacket;
import org.cloudburstmc.protocol.common.util.VarInts;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SubChunkRequestSerializer_v471 implements BedrockPacketSerializer<SubChunkRequestPacket> {

    public static final SubChunkRequestSerializer_v471 INSTANCE = new SubChunkRequestSerializer_v471();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, SubChunkRequestPacket packet) {
        VarInts.writeInt(buffer, packet.getDimension());
        helper.writeVector3i(buffer, packet.getSubChunkPosition());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, SubChunkRequestPacket packet) {
        packet.setDimension(VarInts.readInt(buffer));
        packet.setSubChunkPosition(helper.readVector3i(buffer));
    }
}
