package com.nukkitx.protocol.bedrock.v291.serializer;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.packet.ItemFrameDropItemPacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ItemFrameDropItemSerializer_v291 implements BedrockPacketSerializer<ItemFrameDropItemPacket> {
    public static final ItemFrameDropItemSerializer_v291 INSTANCE = new ItemFrameDropItemSerializer_v291();


    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, ItemFrameDropItemPacket packet) {
        helper.writeVector3i(buffer, packet.getBlockPosition());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, ItemFrameDropItemPacket packet) {
        packet.setBlockPosition(helper.readVector3i(buffer));
    }
}
