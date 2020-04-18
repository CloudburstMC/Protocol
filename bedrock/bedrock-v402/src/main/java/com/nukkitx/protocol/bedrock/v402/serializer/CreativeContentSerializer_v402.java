package com.nukkitx.protocol.bedrock.v402.serializer;

import com.nukkitx.protocol.bedrock.packet.CreativeContentPacket;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CreativeContentSerializer_v402 implements PacketSerializer<CreativeContentPacket> {

    public static final CreativeContentSerializer_v402 INSTANCE = new CreativeContentSerializer_v402();

    @Override
    public void serialize(ByteBuf buffer, CreativeContentPacket packet) {

    }

    @Override
    public void deserialize(ByteBuf buffer, CreativeContentPacket packet) {

    }
}
