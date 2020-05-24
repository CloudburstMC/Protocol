package com.nukkitx.protocol.bedrock.v363.serializer;

import com.nukkitx.protocol.bedrock.packet.NetworkSettingsPacket;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class NetworkSettingsSerializer_v363 implements PacketSerializer<NetworkSettingsPacket> {

    public static final NetworkSettingsSerializer_v363 INSTANCE = new NetworkSettingsSerializer_v363();

    @Override
    public void serialize(ByteBuf buffer, NetworkSettingsPacket packet) {
        buffer.writeShortLE(packet.getCompressionThresholdByteSize());
    }

    @Override
    public void deserialize(ByteBuf buffer, NetworkSettingsPacket packet) {
        packet.setCompressionThresholdByteSize(buffer.readUnsignedShortLE());
    }
}
