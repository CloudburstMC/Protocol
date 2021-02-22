package com.nukkitx.protocol.genoa.v425.serializer;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.genoa.packet.GenoaNetworkTransformPacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GenoaNetworkTransformSerializer implements BedrockPacketSerializer<GenoaNetworkTransformPacket> {
    public static final GenoaNetworkTransformSerializer INSTANCE = new GenoaNetworkTransformSerializer();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, GenoaNetworkTransformPacket packet) {
        System.out.println(packet);
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, GenoaNetworkTransformPacket packet) {
        System.out.println(packet);
    }
}


