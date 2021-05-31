package org.cloudburstmc.protocol.bedrock.codec.v388.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.data.inventory.ItemUseType;
import org.cloudburstmc.protocol.bedrock.packet.CompletedUsingItemPacket;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class CompletedUsingItemSerializer_v388 implements BedrockPacketSerializer<CompletedUsingItemPacket> {

    public static final CompletedUsingItemSerializer_v388 INSTANCE = new CompletedUsingItemSerializer_v388();

    private static final ItemUseType[] VALUES = ItemUseType.values();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, CompletedUsingItemPacket packet) {
        buffer.writeShortLE(packet.getItemId());
        buffer.writeIntLE(packet.getType().ordinal() - 1); // Enum starts at -1
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, CompletedUsingItemPacket packet) {
        packet.setItemId(buffer.readUnsignedShortLE());
        packet.setType(VALUES[buffer.readIntLE() + 1]); // Enum starts at -1
    }
}
