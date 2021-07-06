package com.nukkitx.protocol.bedrock.v448.serializer;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.data.SimulationType;
import com.nukkitx.protocol.bedrock.packet.SimulationTypePacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class SimulationTypeSerializer_v448 implements BedrockPacketSerializer<SimulationTypePacket> {
    public static final SimulationTypeSerializer_v448 INSTANCE = new SimulationTypeSerializer_v448();

    private static final SimulationType[] VALUES = SimulationType.values();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, SimulationTypePacket packet) {
        buffer.writeByte(packet.getType().ordinal());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, SimulationTypePacket packet) {
        packet.setType(VALUES[buffer.readUnsignedByte()]);
    }
}
