package com.nukkitx.protocol.bedrock.v291.serializer;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.packet.BlockEntityDataPacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BlockEntityDataSerializer_v291 implements BedrockPacketSerializer<BlockEntityDataPacket> {
    public static final BlockEntityDataSerializer_v291 INSTANCE = new BlockEntityDataSerializer_v291();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, BlockEntityDataPacket packet) {
        helper.writeBlockPosition(buffer, packet.getBlockPosition());
        helper.writeTag(buffer, packet.getData());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, BlockEntityDataPacket packet) {
        packet.setBlockPosition(helper.readBlockPosition(buffer));
        packet.setData(helper.readTag(buffer));
    }
}
