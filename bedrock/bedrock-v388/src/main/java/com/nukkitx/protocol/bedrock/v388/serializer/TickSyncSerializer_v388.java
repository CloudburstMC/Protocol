package com.nukkitx.protocol.bedrock.v388.serializer;

import com.nukkitx.protocol.bedrock.packet.TickSyncPacket;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TickSyncSerializer_v388 implements PacketSerializer<TickSyncPacket> {

    public static final TickSyncSerializer_v388 INSTANCE = new TickSyncSerializer_v388();

    @Override
    public void serialize(ByteBuf buffer, TickSyncPacket packet) {
        buffer.writeLongLE(packet.getRequestTimestamp());
        buffer.writeLongLE(packet.getResponseTimestamp());
    }

    @Override
    public void deserialize(ByteBuf buffer, TickSyncPacket packet) {
        packet.setRequestTimestamp(buffer.readLongLE());
        packet.setResponseTimestamp(buffer.readLongLE());
    }
}
