package com.nukkitx.protocol.bedrock.v291.serializer;

import com.nukkitx.protocol.bedrock.packet.NetworkStackLatencyPacket;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NetworkStackLatencySerializer_v291 implements PacketSerializer<NetworkStackLatencyPacket> {
    public static final NetworkStackLatencySerializer_v291 INSTANCE = new NetworkStackLatencySerializer_v291();


    @Override
    public void serialize(ByteBuf buffer, NetworkStackLatencyPacket packet) {
        buffer.writeLongLE(packet.getTimestamp());
    }

    @Override
    public void deserialize(ByteBuf buffer, NetworkStackLatencyPacket packet) {
        packet.setTimestamp(buffer.readLongLE());
    }
}
