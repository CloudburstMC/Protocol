package com.nukkitx.protocol.bedrock.v392.serializer;

import com.nukkitx.protocol.bedrock.packet.EmoteListPacket;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EmoteListSerializer_v402 implements PacketSerializer<EmoteListPacket> {

    public static final EmoteListSerializer_v402 INSTANCE = new EmoteListSerializer_v402();

    @Override
    public void serialize(ByteBuf buffer, EmoteListPacket packet) {

    }

    @Override
    public void deserialize(ByteBuf buffer, EmoteListPacket packet) {

    }
}
