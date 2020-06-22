package com.nukkitx.protocol.bedrock;

import com.nukkitx.protocol.MinecraftPacket;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import lombok.Data;

@Data
public abstract class BedrockPacket implements MinecraftPacket {
    private int packetId;
    private int senderId;
    private int clientId;

    public abstract boolean handle(BedrockPacketHandler handler);

    public abstract BedrockPacketType getPacketType();
}
