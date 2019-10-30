package com.nukkitx.protocol.bedrock.v388.serializer;

import com.nukkitx.protocol.bedrock.packet.NetworkSettingsPacket;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class NetworkSettingsSerializer_v388 implements PacketSerializer<NetworkSettingsPacket> {

    public static final NetworkSettingsSerializer_v388 INSTANCE = new NetworkSettingsSerializer_v388();

    @Override
    public void serialize(ByteBuf buffer, NetworkSettingsPacket packet) {
        buffer.writeShortLE(packet.getCompressionThresholdByteSize());
    }

    @Override
    public void deserialize(ByteBuf buffer, NetworkSettingsPacket packet) {
        packet.setCompressionThresholdByteSize(buffer.readUnsignedShortLE());
    }
}
