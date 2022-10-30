package org.cloudburstmc.protocol.bedrock.codec.v291.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.nbt.NbtMap;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.packet.BlockEntityDataPacket;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BlockEntityDataSerializer_v291 implements BedrockPacketSerializer<BlockEntityDataPacket> {
    public static final BlockEntityDataSerializer_v291 INSTANCE = new BlockEntityDataSerializer_v291();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, BlockEntityDataPacket packet) {
        helper.writeBlockPosition(buffer, packet.getBlockPosition());
        helper.writeTag(buffer, packet.getData());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, BlockEntityDataPacket packet) {
        packet.setBlockPosition(helper.readBlockPosition(buffer));
        packet.setData(helper.readTag(buffer, NbtMap.class));
    }
}
