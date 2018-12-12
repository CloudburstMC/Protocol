package com.nukkitx.protocol.bedrock.v313.serializer;

import com.nukkitx.protocol.bedrock.packet.AvailableCommandsPacket;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AvailableCommandsSerializer_v313 implements PacketSerializer<AvailableCommandsPacket> {
    public static final AvailableCommandsSerializer_v313 INSTANCE = new AvailableCommandsSerializer_v313();
    //TODO: Everything

    @Override
    public void serialize(ByteBuf buffer, AvailableCommandsPacket packet) {
    }

    @Override
    public void deserialize(ByteBuf buffer, AvailableCommandsPacket packet) {
    }
}
