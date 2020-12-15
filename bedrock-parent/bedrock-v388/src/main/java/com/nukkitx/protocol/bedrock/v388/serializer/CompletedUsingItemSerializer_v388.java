package com.nukkitx.protocol.bedrock.v388.serializer;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.data.inventory.ItemUseType;
import com.nukkitx.protocol.bedrock.packet.CompletedUsingItemPacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class CompletedUsingItemSerializer_v388 implements BedrockPacketSerializer<CompletedUsingItemPacket> {

    public static final CompletedUsingItemSerializer_v388 INSTANCE = new CompletedUsingItemSerializer_v388();

    private static final ItemUseType[] VALUES = ItemUseType.values();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, CompletedUsingItemPacket packet) {
        buffer.writeShortLE(packet.getItemId());
        buffer.writeIntLE(packet.getType().ordinal() - 1); // Enum starts at -1
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, CompletedUsingItemPacket packet) {
        packet.setItemId(buffer.readUnsignedShortLE());
        packet.setType(VALUES[buffer.readIntLE() + 1]); // Enum starts at -1
    }
}
