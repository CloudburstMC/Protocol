package com.nukkitx.protocol.bedrock;

import lombok.Value;

import java.util.function.Supplier;

@Value
public class BedrockPacketDefinition<T extends BedrockPacket> {
    int id;
    Supplier<T> factory;
    BedrockPacketSerializer<T> serializer;
}
