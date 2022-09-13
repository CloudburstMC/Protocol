package com.nukkitx.protocol.bedrock.v553.serializer;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.data.PacketCompressionAlgorithm;
import com.nukkitx.protocol.bedrock.packet.NetworkSettingsPacket;
import com.nukkitx.protocol.bedrock.v388.serializer.NetworkSettingsSerializer_v388;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NetworkSettingsSerializer_v553 extends NetworkSettingsSerializer_v388 {

    public static final NetworkSettingsSerializer_v553 INSTANCE = new NetworkSettingsSerializer_v553();

    protected static final PacketCompressionAlgorithm[] ALGORITHMS = PacketCompressionAlgorithm.values();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, NetworkSettingsPacket packet) {
        super.serialize(buffer, helper, packet);

        buffer.writeShortLE(packet.getCompressionAlgorithm().ordinal());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, NetworkSettingsPacket packet) {
        super.deserialize(buffer, helper, packet);

        packet.setCompressionAlgorithm(ALGORITHMS[buffer.readUnsignedShortLE()]);
    }
}
