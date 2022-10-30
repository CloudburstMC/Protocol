package org.cloudburstmc.protocol.bedrock.codec.v361.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.packet.ClientCacheStatusPacket;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClientCacheStatusSerializer_v361 implements BedrockPacketSerializer<ClientCacheStatusPacket> {
    public static final ClientCacheStatusSerializer_v361 INSTANCE = new ClientCacheStatusSerializer_v361();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, ClientCacheStatusPacket packet) {
        buffer.writeBoolean(packet.isSupported());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, ClientCacheStatusPacket packet) {
        packet.setSupported(buffer.readBoolean());
    }
}
