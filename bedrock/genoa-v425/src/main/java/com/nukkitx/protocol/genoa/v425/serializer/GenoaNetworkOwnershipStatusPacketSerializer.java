package com.nukkitx.protocol.genoa.v425.serializer;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.genoa.packet.GenoaNetworkOwnershipStatusPacket;
import io.netty.buffer.ByteBuf;

public class GenoaNetworkOwnershipStatusPacketSerializer implements BedrockPacketSerializer<GenoaNetworkOwnershipStatusPacket> {
    public static final GenoaNetworkOwnershipStatusPacketSerializer INSTANCE = new GenoaNetworkOwnershipStatusPacketSerializer();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, GenoaNetworkOwnershipStatusPacket packet) {
        buffer.writeLong(packet.getUnsignedLong1());
        // TODO: Array Func implementation
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, GenoaNetworkOwnershipStatusPacket packet) {
        packet.setUnsignedLong1(buffer.readLong());
    }
}
