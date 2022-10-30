package org.cloudburstmc.protocol.bedrock.codec.v291.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.packet.ItemFrameDropItemPacket;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ItemFrameDropItemSerializer_v291 implements BedrockPacketSerializer<ItemFrameDropItemPacket> {
    public static final ItemFrameDropItemSerializer_v291 INSTANCE = new ItemFrameDropItemSerializer_v291();


    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, ItemFrameDropItemPacket packet) {
        helper.writeBlockPosition(buffer, packet.getBlockPosition());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, ItemFrameDropItemPacket packet) {
        packet.setBlockPosition(helper.readBlockPosition(buffer));
    }
}
