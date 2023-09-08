package org.cloudburstmc.protocol.bedrock.codec.v618.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.packet.RefreshEntitlementsPacket;

public class RefreshEntitlementsSerializer_v618 implements BedrockPacketSerializer<RefreshEntitlementsPacket> {

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, RefreshEntitlementsPacket packet) {
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, RefreshEntitlementsPacket packet) {
    }
}
