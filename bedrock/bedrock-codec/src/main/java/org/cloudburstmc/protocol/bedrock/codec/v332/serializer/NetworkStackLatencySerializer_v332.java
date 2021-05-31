package org.cloudburstmc.protocol.bedrock.codec.v332.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.packet.NetworkStackLatencyPacket;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NetworkStackLatencySerializer_v332 implements BedrockPacketSerializer<NetworkStackLatencyPacket> {
    public static final NetworkStackLatencySerializer_v332 INSTANCE = new NetworkStackLatencySerializer_v332();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, NetworkStackLatencyPacket packet) {
        buffer.writeLongLE(packet.getTimestamp());
        buffer.writeBoolean(packet.isFromServer());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, NetworkStackLatencyPacket packet) {
        packet.setTimestamp(buffer.readLongLE());
        packet.setFromServer(buffer.readBoolean());
    }
}
