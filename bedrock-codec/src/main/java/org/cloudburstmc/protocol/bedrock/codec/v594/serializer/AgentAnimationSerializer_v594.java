package org.cloudburstmc.protocol.bedrock.codec.v594.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.packet.AgentAnimationPacket;
import org.cloudburstmc.protocol.common.util.VarInts;

public class AgentAnimationSerializer_v594 implements BedrockPacketSerializer<AgentAnimationPacket> {

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, AgentAnimationPacket packet) {
        buffer.writeByte(packet.getAnimation());
        VarInts.writeUnsignedLong(buffer, packet.getRuntimeEntityId());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, AgentAnimationPacket packet) {
        packet.setAnimation(buffer.readByte());
        packet.setRuntimeEntityId(VarInts.readUnsignedLong(buffer));
    }
}
