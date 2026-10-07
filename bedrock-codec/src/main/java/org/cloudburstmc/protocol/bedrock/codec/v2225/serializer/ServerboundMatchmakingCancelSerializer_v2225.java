package org.cloudburstmc.protocol.bedrock.codec.v2225.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.packet.ServerboundMatchmakingCancelPacket;

public class ServerboundMatchmakingCancelSerializer_v2225 implements BedrockPacketSerializer<ServerboundMatchmakingCancelPacket> {

    public static final ServerboundMatchmakingCancelSerializer_v2225 INSTANCE = new ServerboundMatchmakingCancelSerializer_v2225();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, ServerboundMatchmakingCancelPacket packet) {
        // nothing
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, ServerboundMatchmakingCancelPacket packet) {
        // nothing
    }
}
