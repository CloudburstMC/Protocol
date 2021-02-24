package com.nukkitx.protocol.genoa.v425.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.genoa.packet.GenoaEventFlatbufferPacket;
import com.nukkitx.protocol.genoa.packet.GenoaGuestPlayerJoinResponsePacket;
import io.netty.buffer.ByteBuf;

public class GenoaEventFlatbufferPacketSerializer implements BedrockPacketSerializer<GenoaEventFlatbufferPacket> {
    public static final GenoaEventFlatbufferPacketSerializer INSTANCE = new GenoaEventFlatbufferPacketSerializer();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, GenoaEventFlatbufferPacket packet) {
        // TODO: Array
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, GenoaEventFlatbufferPacket packet) {
    }
}
