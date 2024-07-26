package org.cloudburstmc.protocol.bedrock.codec.v712.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.packet.CurrentStructureFeaturePacket;

public class CurrentStructureFeatureSerializer_v712 implements BedrockPacketSerializer<CurrentStructureFeaturePacket> {
    public static final CurrentStructureFeatureSerializer_v712 INSTANCE = new CurrentStructureFeatureSerializer_v712();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, CurrentStructureFeaturePacket packet) {
        helper.writeString(buffer, packet.getCurrentStructureFeature());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, CurrentStructureFeaturePacket packet) {
        packet.setCurrentStructureFeature(helper.readString(buffer));
    }
}