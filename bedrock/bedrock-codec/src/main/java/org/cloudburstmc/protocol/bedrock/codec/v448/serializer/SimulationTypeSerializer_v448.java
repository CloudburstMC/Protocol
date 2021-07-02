package org.cloudburstmc.protocol.bedrock.codec.v448.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.data.SimulationType;
import org.cloudburstmc.protocol.bedrock.packet.SimulationTypePacket;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class SimulationTypeSerializer_v448 implements BedrockPacketSerializer<SimulationTypePacket> {
    public static final SimulationTypeSerializer_v448 INSTANCE = new SimulationTypeSerializer_v448();

    private static final SimulationType[] VALUES = SimulationType.values();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, SimulationTypePacket packet) {
        buffer.writeByte(packet.getType().ordinal());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, SimulationTypePacket packet) {
        packet.setType(VALUES[buffer.readUnsignedByte()]);
    }
}
