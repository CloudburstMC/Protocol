package com.nukkitx.protocol.bedrock.v544.serializer;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.data.FeatureData;
import com.nukkitx.protocol.bedrock.packet.FeatureRegistryPacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FeatureRegistrySerializer_v544 implements BedrockPacketSerializer<FeatureRegistryPacket> {

    public static final FeatureRegistrySerializer_v544 INSTANCE = new FeatureRegistrySerializer_v544();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, FeatureRegistryPacket packet) {
        helper.writeArray(buffer, packet.getFeatures(), (buf, aHelper, data) -> {
            helper.writeString(buf, data.getName());
            helper.writeString(buf, data.getJson());
        });
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, FeatureRegistryPacket packet) {
        helper.readArray(buffer, packet.getFeatures(), (buf, aHelper) -> {
            String name = helper.readString(buf);
            String json = helper.readString(buf);
            return new FeatureData(name, json);
        });
    }
}
