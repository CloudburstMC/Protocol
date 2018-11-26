package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.network.NetworkPacket;
import lombok.Data;

@Data
public abstract class PacketHeader implements NetworkPacket {
    protected int packetId;
    protected int senderId;
    protected int clientId;
}
