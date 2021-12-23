package com.nukkitx.protocol.bedrock.v388.serializer;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.packet.TickSyncPacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TickSyncSerializer_v388 implements BedrockPacketSerializer<TickSyncPacket> {

    public static final TickSyncSerializer_v388 INSTANCE = new TickSyncSerializer_v388();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, TickSyncPacket packet) {
        buffer.writeLongLE(packet.getRequestTimestamp());
        buffer.writeLongLE(packet.getResponseTimestamp());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, TickSyncPacket packet) {
        packet.setRequestTimestamp(buffer.readLongLE());
        packet.setResponseTimestamp(buffer.readLongLE());
    }
}
