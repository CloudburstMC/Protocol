package com.nukkitx.protocol.bedrock.v332.serializer;

import com.nukkitx.protocol.bedrock.packet.NetworkStackLatencyPacket;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NetworkStackLatencySerializer_v332 implements PacketSerializer<NetworkStackLatencyPacket> {
    public static final NetworkStackLatencySerializer_v332 INSTANCE = new NetworkStackLatencySerializer_v332();


    @Override
    public void serialize(ByteBuf buffer, NetworkStackLatencyPacket packet) {
        buffer.writeLongLE(packet.getTimestamp());
        buffer.writeBoolean(packet.isSendBack());
    }

    @Override
    public void deserialize(ByteBuf buffer, NetworkStackLatencyPacket packet) {
        packet.setTimestamp(buffer.readLongLE());
        packet.setSendBack(buffer.readBoolean());
    }
}
