package com.nukkitx.protocol.bedrock.v291.packet;

import com.nukkitx.protocol.bedrock.packet.NetworkStackLatencyPacket;
import io.netty.buffer.ByteBuf;

public class NetworkStackLatencyPacket_v291 extends NetworkStackLatencyPacket {

    @Override
    public void encode(ByteBuf buffer) {
        buffer.writeLongLE(timestamp);
    }

    @Override
    public void decode(ByteBuf buffer) {
        timestamp = buffer.readLongLE();
    }
}
