package com.nukkitx.protocol.bedrock.v291.serializer;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.packet.ClientToServerHandshakePacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClientToServerHandshakeSerializer_v291 implements BedrockPacketSerializer<ClientToServerHandshakePacket> {
    public static final ClientToServerHandshakeSerializer_v291 INSTANCE = new ClientToServerHandshakeSerializer_v291();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, ClientToServerHandshakePacket packet) {
        // No payload
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, ClientToServerHandshakePacket packet) {
        // No payload
    }
}
