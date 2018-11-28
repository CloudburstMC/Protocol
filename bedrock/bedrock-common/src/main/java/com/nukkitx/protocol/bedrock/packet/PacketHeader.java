package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.MinecraftPacket;
import lombok.Data;

@Data
public class PacketHeader implements MinecraftPacket {
    protected int packetId;
    protected int senderId;
    protected int clientId;
}
