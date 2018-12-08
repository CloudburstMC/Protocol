package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.MinecraftPacket;
import lombok.Data;

@Data
public class PacketHeader implements MinecraftPacket {
    private int packetId;
    private int senderId;
    private int clientId;
}
