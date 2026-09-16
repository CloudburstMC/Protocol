package org.cloudburstmc.protocol.bedrock.codec.v2193.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.packet.RecordStartedPacket;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class RecordStartedSerializer_v2193 implements BedrockPacketSerializer<RecordStartedPacket> {

    public static final RecordStartedSerializer_v2193 INSTANCE = new RecordStartedSerializer_v2193();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, RecordStartedPacket packet) {
        helper.writeVector3i(buffer, packet.getBlockPos());
        buffer.writeLongLE(packet.getServerSoundHandle());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, RecordStartedPacket packet) {
        packet.setBlockPos(helper.readVector3i(buffer));
        packet.setServerSoundHandle(buffer.readLongLE());
    }
}
