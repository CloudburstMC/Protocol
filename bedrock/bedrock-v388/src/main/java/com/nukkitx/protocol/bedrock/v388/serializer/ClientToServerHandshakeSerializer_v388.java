package com.nukkitx.protocol.bedrock.v388.serializer;

import com.nukkitx.protocol.bedrock.packet.ClientToServerHandshakePacket;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ClientToServerHandshakeSerializer_v388 implements PacketSerializer<ClientToServerHandshakePacket> {
    public static final ClientToServerHandshakeSerializer_v388 INSTANCE = new ClientToServerHandshakeSerializer_v388();

    @Override
    public void serialize(ByteBuf buffer, ClientToServerHandshakePacket packet) {
        // No payload
    }

    @Override
    public void deserialize(ByteBuf buffer, ClientToServerHandshakePacket packet) {
        // No payload
    }
}
