package com.nukkitx.protocol.bedrock;

import com.nukkitx.protocol.MinecraftPacket;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import lombok.Data;
import lombok.ToString;

@Data
public abstract class BedrockPacket implements MinecraftPacket {
    private int packetId;
    private int senderId;
    private int clientId;

    // @TODO Remove Debugging
    @ToString.Exclude
    private byte[] raw;

    public abstract boolean handle(BedrockPacketHandler handler);

    public abstract BedrockPacketType getPacketType();
}
