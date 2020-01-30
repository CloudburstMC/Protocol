package com.nukkitx.protocol.bedrock;

import com.nukkitx.protocol.MinecraftPacket;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import com.nukkitx.protocol.bedrock.packet.PacketHeader;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class BedrockPacket implements MinecraftPacket {
    private PacketHeader header;

    public abstract boolean handle(BedrockPacketHandler handler);

    public abstract BedrockPacketType getPacketType();
}
