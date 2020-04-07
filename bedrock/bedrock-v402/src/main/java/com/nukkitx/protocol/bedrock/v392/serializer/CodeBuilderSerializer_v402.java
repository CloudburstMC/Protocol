package com.nukkitx.protocol.bedrock.v392.serializer;

import com.nukkitx.protocol.bedrock.packet.CodeBuilderPacket;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CodeBuilderSerializer_v402 implements PacketSerializer<CodeBuilderPacket> {

    public static final CodeBuilderSerializer_v402 INSTANCE = new CodeBuilderSerializer_v402();

    @Override
    public void serialize(ByteBuf buffer, CodeBuilderPacket packet) {

    }

    @Override
    public void deserialize(ByteBuf buffer, CodeBuilderPacket packet) {

    }
}
