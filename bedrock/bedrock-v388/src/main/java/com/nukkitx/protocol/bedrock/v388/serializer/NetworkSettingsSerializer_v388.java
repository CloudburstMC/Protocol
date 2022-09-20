package com.nukkitx.protocol.bedrock.v388.serializer;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.packet.NetworkSettingsPacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class NetworkSettingsSerializer_v388 implements BedrockPacketSerializer<NetworkSettingsPacket> {
    public static final NetworkSettingsSerializer_v388 INSTANCE = new NetworkSettingsSerializer_v388();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, NetworkSettingsPacket packet) {
        buffer.writeShortLE(packet.getCompressionThreshold());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, NetworkSettingsPacket packet) {
        packet.setCompressionThreshold(buffer.readUnsignedShortLE());
    }
}
