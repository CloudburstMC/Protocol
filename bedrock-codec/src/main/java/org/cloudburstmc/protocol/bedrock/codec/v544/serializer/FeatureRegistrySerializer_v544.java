package org.cloudburstmc.protocol.bedrock.codec.v544.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.data.definitions.FeatureDefinition;
import org.cloudburstmc.protocol.bedrock.packet.FeatureRegistryPacket;

public class FeatureRegistrySerializer_v544 implements BedrockPacketSerializer<FeatureRegistryPacket> {

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, FeatureRegistryPacket packet) {
        helper.writeArray(buffer, packet.getFeatures(), (buf, aHelper, data) -> {
            helper.writeString(buf, data.getName());
            helper.writeString(buf, data.getJson());
        });
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, FeatureRegistryPacket packet) {
        helper.readArray(buffer, packet.getFeatures(), (buf, aHelper) -> {
            String name = helper.readString(buf);
            String json = helper.readString(buf);
            return new FeatureDefinition(name, json);
        });
    }
}
