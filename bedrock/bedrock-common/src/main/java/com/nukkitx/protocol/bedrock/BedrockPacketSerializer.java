package com.nukkitx.protocol.bedrock;

import com.nukkitx.protocol.serializer.PacketSerializer;

public interface BedrockPacketSerializer<T extends BedrockPacket> extends PacketSerializer<T, BedrockPacketHelper> {
}
