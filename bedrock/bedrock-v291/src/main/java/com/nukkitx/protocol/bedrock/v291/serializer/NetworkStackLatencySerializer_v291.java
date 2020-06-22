package com.nukkitx.protocol.bedrock.v291.serializer;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.packet.NetworkStackLatencyPacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NetworkStackLatencySerializer_v291 implements BedrockPacketSerializer<NetworkStackLatencyPacket> {
    public static final NetworkStackLatencySerializer_v291 INSTANCE = new NetworkStackLatencySerializer_v291();


    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, NetworkStackLatencyPacket packet) {
        buffer.writeLongLE(packet.getTimestamp());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, NetworkStackLatencyPacket packet) {
        packet.setTimestamp(buffer.readLongLE());
    }
}
