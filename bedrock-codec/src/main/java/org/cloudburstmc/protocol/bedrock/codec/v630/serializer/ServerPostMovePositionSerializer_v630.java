package org.cloudburstmc.protocol.bedrock.codec.v630.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.packet.ServerPostMovePositionPacket;

public class ServerPostMovePositionSerializer_v630 implements BedrockPacketSerializer<ServerPostMovePositionPacket> {

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, ServerPostMovePositionPacket packet) {
        helper.writeVector3f(buffer, packet.getPosition());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, ServerPostMovePositionPacket packet) {
        packet.setPosition(helper.readVector3f(buffer));
    }
}
