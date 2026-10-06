package org.cloudburstmc.protocol.bedrock.codec.v2225.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v2168.serializer.LevelChunkSerializer_v2168;
import org.cloudburstmc.protocol.bedrock.packet.LevelChunkPacket;

public class LevelChunkSerializer_v2225 extends LevelChunkSerializer_v2168 {

    public static final LevelChunkSerializer_v2225 INSTANCE = new LevelChunkSerializer_v2225();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, LevelChunkPacket packet) {
        super.serialize(buffer, helper, packet);
        buffer.writeBoolean(packet.isClientBiomeUpdate());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, LevelChunkPacket packet) {
        super.deserialize(buffer, helper, packet);
        packet.setClientBiomeUpdate(buffer.readBoolean());
    }
}
