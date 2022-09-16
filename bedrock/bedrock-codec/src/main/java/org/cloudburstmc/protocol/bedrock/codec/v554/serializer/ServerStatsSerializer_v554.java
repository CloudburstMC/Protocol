package org.cloudburstmc.protocol.bedrock.codec.v554.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.packet.ServerStatsPacket;

public class ServerStatsSerializer_v554 implements BedrockPacketSerializer<ServerStatsPacket> {

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, ServerStatsPacket packet) {
        buffer.writeFloatLE(packet.getServerTime());
        buffer.writeFloatLE(packet.getNetworkTime());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, ServerStatsPacket packet) {
        packet.setServerTime(buffer.readFloatLE());
        packet.setNetworkTime(buffer.readFloatLE());
    }
}