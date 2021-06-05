package org.cloudburstmc.protocol.bedrock.codec.v291.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.packet.BlockEventPacket;
import org.cloudburstmc.protocol.common.util.VarInts;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BlockEventSerializer_v291 implements BedrockPacketSerializer<BlockEventPacket> {
    public static final BlockEventSerializer_v291 INSTANCE = new BlockEventSerializer_v291();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, BlockEventPacket packet) {
        helper.writeBlockPosition(buffer, packet.getBlockPosition());
        VarInts.writeInt(buffer, packet.getEventType());
        VarInts.writeInt(buffer, packet.getEventData());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, BlockEventPacket packet) {
        packet.setBlockPosition(helper.readBlockPosition(buffer));
        packet.setEventType(VarInts.readInt(buffer));
        packet.setEventData(VarInts.readInt(buffer));
    }
}
