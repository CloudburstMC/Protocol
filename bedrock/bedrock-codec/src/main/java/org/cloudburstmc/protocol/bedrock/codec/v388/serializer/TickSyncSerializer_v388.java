package org.cloudburstmc.protocol.bedrock.codec.v388.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.packet.TickSyncPacket;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TickSyncSerializer_v388 implements BedrockPacketSerializer<TickSyncPacket> {

    public static final TickSyncSerializer_v388 INSTANCE = new TickSyncSerializer_v388();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, TickSyncPacket packet) {
        buffer.writeLongLE(packet.getRequestTimestamp());
        buffer.writeLongLE(packet.getResponseTimestamp());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, TickSyncPacket packet) {
        packet.setRequestTimestamp(buffer.readLongLE());
        packet.setResponseTimestamp(buffer.readLongLE());
    }
}
