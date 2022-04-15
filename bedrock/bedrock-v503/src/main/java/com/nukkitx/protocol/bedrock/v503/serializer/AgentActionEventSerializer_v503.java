package com.nukkitx.protocol.bedrock.v503.serializer;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.data.ee.AgentActionType;
import com.nukkitx.protocol.bedrock.packet.AgentActionEventPacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AgentActionEventSerializer_v503 implements BedrockPacketSerializer<AgentActionEventPacket> {
    public static final AgentActionEventSerializer_v503 INSTANCE = new AgentActionEventSerializer_v503();

    private static final AgentActionType[] VALUES = AgentActionType.values();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, AgentActionEventPacket packet) {
        helper.writeString(buffer, packet.getRequestId());
        buffer.writeIntLE(packet.getActionType().ordinal());
        helper.writeString(buffer, packet.getResponseJson());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, AgentActionEventPacket packet) {
        packet.setRequestId(helper.readString(buffer));
        packet.setActionType(VALUES[buffer.readIntLE()]);
        packet.setResponseJson(helper.readString(buffer));
    }
}