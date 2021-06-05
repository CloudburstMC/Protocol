package org.cloudburstmc.protocol.bedrock.codec.v313.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.packet.NetworkChunkPublisherUpdatePacket;
import org.cloudburstmc.protocol.common.util.VarInts;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NetworkChunkPublisherUpdateSerializer_v313 implements BedrockPacketSerializer<NetworkChunkPublisherUpdatePacket> {
    public static final NetworkChunkPublisherUpdateSerializer_v313 INSTANCE = new NetworkChunkPublisherUpdateSerializer_v313();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, NetworkChunkPublisherUpdatePacket packet) {
        helper.writeVector3i(buffer, packet.getPosition());
        VarInts.writeUnsignedInt(buffer, packet.getRadius());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, NetworkChunkPublisherUpdatePacket packet) {
        packet.setPosition(helper.readVector3i(buffer));
        packet.setRadius(VarInts.readUnsignedInt(buffer));
    }
}
