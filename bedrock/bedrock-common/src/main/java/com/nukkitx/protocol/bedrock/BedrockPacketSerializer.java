package com.nukkitx.protocol.bedrock;

import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;

public interface BedrockPacketSerializer<T extends BedrockPacket> extends PacketSerializer<T, BedrockPacketHelper> {

    @Override
    default void serialize(ByteBuf buffer, BedrockPacketHelper helper, T packet) {
    }

    default void serialize(ByteBuf buffer, BedrockPacketHelper helper, T packet, BedrockSession session) {
        this.serialize(buffer, helper, packet);
    }

    @Override
    default void deserialize(ByteBuf buffer, BedrockPacketHelper helper, T packet) {
    }

    default void deserialize(ByteBuf buffer, BedrockPacketHelper helper, T packet, BedrockSession session) {
        this.deserialize(buffer, helper, packet);
    }
}
