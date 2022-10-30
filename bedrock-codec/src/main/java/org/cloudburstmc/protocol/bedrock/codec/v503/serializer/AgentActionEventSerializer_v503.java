package org.cloudburstmc.protocol.bedrock.codec.v503.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.data.ee.AgentActionType;
import org.cloudburstmc.protocol.bedrock.packet.AgentActionEventPacket;

public class AgentActionEventSerializer_v503 implements BedrockPacketSerializer<AgentActionEventPacket> {

    private static final AgentActionType[] VALUES = AgentActionType.values();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, AgentActionEventPacket packet) {
        helper.writeString(buffer, packet.getRequestId());
        buffer.writeIntLE(packet.getActionType().ordinal());
        helper.writeString(buffer, packet.getResponseJson());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, AgentActionEventPacket packet) {
        packet.setRequestId(helper.readString(buffer));
        packet.setActionType(VALUES[buffer.readIntLE()]);
        packet.setResponseJson(helper.readString(buffer));
    }
}
