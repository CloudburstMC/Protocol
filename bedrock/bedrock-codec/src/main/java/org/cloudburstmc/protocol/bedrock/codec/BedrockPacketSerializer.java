package org.cloudburstmc.protocol.bedrock.codec;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.packet.BedrockPacket;

public interface BedrockPacketSerializer<T extends BedrockPacket> {

    void serialize(ByteBuf buffer, BedrockCodecHelper helper, T packet);

    void deserialize(ByteBuf buffer, BedrockCodecHelper helper, T packet);
}
