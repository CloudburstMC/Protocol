package com.nukkitx.protocol.bedrock.v553.serializer;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.packet.ServerStatsPacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ServerStatsSerializer_v553 implements BedrockPacketSerializer<ServerStatsPacket> {

    public static final ServerStatsSerializer_v553 INSTANCE = new ServerStatsSerializer_v553();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, ServerStatsPacket packet) {
        buffer.writeFloatLE(packet.getServerTime());
        buffer.writeFloatLE(packet.getNetworkTime());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, ServerStatsPacket packet) {
        packet.setServerTime(buffer.readFloatLE());
        packet.setNetworkTime(buffer.readFloatLE());
    }
}
