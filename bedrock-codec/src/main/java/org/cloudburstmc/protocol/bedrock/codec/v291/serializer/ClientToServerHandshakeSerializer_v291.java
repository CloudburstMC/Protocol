package org.cloudburstmc.protocol.bedrock.codec.v291.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.packet.ClientToServerHandshakePacket;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClientToServerHandshakeSerializer_v291 implements BedrockPacketSerializer<ClientToServerHandshakePacket> {
    public static final ClientToServerHandshakeSerializer_v291 INSTANCE = new ClientToServerHandshakeSerializer_v291();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, ClientToServerHandshakePacket packet) {
        // No payload
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, ClientToServerHandshakePacket packet) {
        // No payload
    }
}
