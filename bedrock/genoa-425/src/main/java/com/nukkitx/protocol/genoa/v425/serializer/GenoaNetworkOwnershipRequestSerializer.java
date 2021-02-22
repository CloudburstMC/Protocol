package com.nukkitx.protocol.genoa.v425.serializer;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.genoa.packet.GenoaNetworkOwnershipRequestPacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GenoaNetworkOwnershipRequestSerializer implements BedrockPacketSerializer<GenoaNetworkOwnershipRequestPacket> {
    public static final GenoaNetworkOwnershipRequestSerializer INSTANCE = new GenoaNetworkOwnershipRequestSerializer();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, GenoaNetworkOwnershipRequestPacket packet) {
        System.out.println(packet);
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, GenoaNetworkOwnershipRequestPacket packet) {
        System.out.println(packet);
    }
}


