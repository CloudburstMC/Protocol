package com.nukkitx.protocol.bedrock.v554.serializer;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.data.PacketCompressionAlgorithm;
import com.nukkitx.protocol.bedrock.packet.NetworkSettingsPacket;
import com.nukkitx.protocol.bedrock.v388.serializer.NetworkSettingsSerializer_v388;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NetworkSettingsSerializer_v554 extends NetworkSettingsSerializer_v388 {

    public static final NetworkSettingsSerializer_v554 INSTANCE = new NetworkSettingsSerializer_v554();

    protected static final PacketCompressionAlgorithm[] ALGORITHMS = PacketCompressionAlgorithm.values();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, NetworkSettingsPacket packet) {
        super.serialize(buffer, helper, packet);

        buffer.writeShortLE(packet.getCompressionAlgorithm().ordinal());
        buffer.writeBoolean(packet.isClientThrottleEnabled());
        buffer.writeByte(packet.getClientThrottleThreshold());
        buffer.writeFloatLE(packet.getClientThrottleScalar());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, NetworkSettingsPacket packet) {
        super.deserialize(buffer, helper, packet);

        packet.setCompressionAlgorithm(ALGORITHMS[buffer.readUnsignedShortLE()]);
        packet.setClientThrottleEnabled(buffer.readBoolean());
        packet.setCompressionThreshold(buffer.readUnsignedByte());
        packet.setClientThrottleScalar(buffer.readFloatLE());
    }
}
