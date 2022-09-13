package org.cloudburstmc.protocol.bedrock.codec.v553.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v388.serializer.NetworkSettingsSerializer_v388;
import org.cloudburstmc.protocol.bedrock.data.PacketCompressionAlgorithm;
import org.cloudburstmc.protocol.bedrock.packet.NetworkSettingsPacket;

public class NetworkSettingsSerializer_v553 extends NetworkSettingsSerializer_v388 {
    protected static final PacketCompressionAlgorithm[] ALGORITHMS = PacketCompressionAlgorithm.values();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, NetworkSettingsPacket packet) {
        super.serialize(buffer, helper, packet);

        buffer.writeShortLE(packet.getCompressionAlgorithm().ordinal());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, NetworkSettingsPacket packet) {
        super.deserialize(buffer, helper, packet);

        packet.setCompressionAlgorithm(ALGORITHMS[buffer.readUnsignedShortLE()]);
    }
}