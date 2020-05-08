package com.nukkitx.protocol.bedrock.v363.serializer;

import com.nukkitx.protocol.bedrock.data.ItemAction;
import com.nukkitx.protocol.bedrock.packet.CompletedUsingItemPacket;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class CompletedUsingItemSerializer_v363 implements PacketSerializer<CompletedUsingItemPacket> {

    public static final CompletedUsingItemSerializer_v363 INSTANCE = new CompletedUsingItemSerializer_v363();

    private static final ItemAction[] VALUES = ItemAction.values();

    @Override
    public void serialize(ByteBuf buffer, CompletedUsingItemPacket packet) {
        buffer.writeShortLE(packet.getItemId());
        buffer.writeIntLE(packet.getAction().ordinal() - 1); // Enum starts at -1
    }

    @Override
    public void deserialize(ByteBuf buffer, CompletedUsingItemPacket packet) {
        packet.setItemId(buffer.readUnsignedShortLE());
        packet.setAction(VALUES[buffer.readIntLE() + 1]); // Enum starts at -1
    }
}
