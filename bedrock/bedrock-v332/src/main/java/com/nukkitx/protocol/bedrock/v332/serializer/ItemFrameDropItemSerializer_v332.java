package com.nukkitx.protocol.bedrock.v332.serializer;

import com.nukkitx.protocol.bedrock.packet.ItemFrameDropItemPacket;
import com.nukkitx.protocol.bedrock.v332.BedrockUtils;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ItemFrameDropItemSerializer_v332 implements PacketSerializer<ItemFrameDropItemPacket> {
    public static final ItemFrameDropItemSerializer_v332 INSTANCE = new ItemFrameDropItemSerializer_v332();


    @Override
    public void serialize(ByteBuf buffer, ItemFrameDropItemPacket packet) {
        BedrockUtils.writeVector3i(buffer, packet.getBlockPosition());
    }

    @Override
    public void deserialize(ByteBuf buffer, ItemFrameDropItemPacket packet) {
        packet.setBlockPosition(BedrockUtils.readVector3i(buffer));
    }
}
