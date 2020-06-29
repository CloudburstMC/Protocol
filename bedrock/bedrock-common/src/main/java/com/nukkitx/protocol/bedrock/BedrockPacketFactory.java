package com.nukkitx.protocol.bedrock;

@FunctionalInterface
public interface BedrockPacketFactory<T extends BedrockPacket> {

    BedrockPacket newInstance();

    @SuppressWarnings("unchecked")
    default Class<BedrockPacket> getPacketClass() {
        return (Class<BedrockPacket>) newInstance().getClass();
    }
}
