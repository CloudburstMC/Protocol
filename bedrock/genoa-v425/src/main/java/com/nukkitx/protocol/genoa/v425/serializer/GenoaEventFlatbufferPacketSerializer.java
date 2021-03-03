package com.nukkitx.protocol.genoa.v425.serializer;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.genoa.packet.GenoaEventFlatbufferPacket;
import io.netty.buffer.ByteBuf;

import java.util.UUID;

public class GenoaEventFlatbufferPacketSerializer implements BedrockPacketSerializer<GenoaEventFlatbufferPacket> {
    public static final GenoaEventFlatbufferPacketSerializer INSTANCE = new GenoaEventFlatbufferPacketSerializer();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, GenoaEventFlatbufferPacket packet) {
        // TODO: Array
        helper.writeArray(buffer,packet.getEventIds(),((byteBuf, bedrockPacketHelper, uuid) -> helper.writeLEAsciiString(buffer,uuid)));
        helper.writeString(buffer,packet.getPlayerId());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, GenoaEventFlatbufferPacket packet) {
        helper.readArray(buffer, packet.getEventIds(), (buf,help) -> help.readLEAsciiString(buf));
        packet.setPlayerId(helper.readString(buffer));
    }
}
