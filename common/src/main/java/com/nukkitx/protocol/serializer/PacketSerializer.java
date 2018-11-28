package com.nukkitx.protocol.serializer;

import com.nukkitx.protocol.MinecraftPacket;
import io.netty.buffer.ByteBuf;

public interface PacketSerializer<T extends MinecraftPacket> {

    void serialize(ByteBuf buffer, T packet);

    void deserialize(ByteBuf buffer, T packet);
}
