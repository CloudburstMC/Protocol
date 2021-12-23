package com.nukkitx.protocol.bedrock.v332.serializer;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.packet.NetworkStackLatencyPacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NetworkStackLatencySerializer_v332 implements BedrockPacketSerializer<NetworkStackLatencyPacket> {
    public static final NetworkStackLatencySerializer_v332 INSTANCE = new NetworkStackLatencySerializer_v332();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, NetworkStackLatencyPacket packet) {
        buffer.writeLongLE(packet.getTimestamp());
        buffer.writeBoolean(packet.isFromServer());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, NetworkStackLatencyPacket packet) {
        packet.setTimestamp(buffer.readLongLE());
        packet.setFromServer(buffer.readBoolean());
    }
}
