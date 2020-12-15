package com.nukkitx.protocol.bedrock.v313.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.packet.NetworkChunkPublisherUpdatePacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NetworkChunkPublisherUpdateSerializer_v313 implements BedrockPacketSerializer<NetworkChunkPublisherUpdatePacket> {
    public static final NetworkChunkPublisherUpdateSerializer_v313 INSTANCE = new NetworkChunkPublisherUpdateSerializer_v313();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, NetworkChunkPublisherUpdatePacket packet) {
        helper.writeVector3i(buffer, packet.getPosition());
        VarInts.writeUnsignedInt(buffer, packet.getRadius());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, NetworkChunkPublisherUpdatePacket packet) {
        packet.setPosition(helper.readVector3i(buffer));
        packet.setRadius(VarInts.readUnsignedInt(buffer));
    }
}
