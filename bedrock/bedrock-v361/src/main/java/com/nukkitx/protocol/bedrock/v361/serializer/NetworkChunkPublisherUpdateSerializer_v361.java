package com.nukkitx.protocol.bedrock.v361.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.NetworkChunkPublisherUpdatePacket;
import com.nukkitx.protocol.bedrock.v361.BedrockUtils;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NetworkChunkPublisherUpdateSerializer_v361 implements PacketSerializer<NetworkChunkPublisherUpdatePacket> {
    public static final NetworkChunkPublisherUpdateSerializer_v361 INSTANCE = new NetworkChunkPublisherUpdateSerializer_v361();

    @Override
    public void serialize(ByteBuf buffer, NetworkChunkPublisherUpdatePacket packet) {
        BedrockUtils.writeVector3i(buffer, packet.getPosition());
        VarInts.writeUnsignedInt(buffer, packet.getRadius());
    }

    @Override
    public void deserialize(ByteBuf buffer, NetworkChunkPublisherUpdatePacket packet) {
        packet.setPosition(BedrockUtils.readVector3i(buffer));
        packet.setRadius(VarInts.readUnsignedInt(buffer));
    }
}
