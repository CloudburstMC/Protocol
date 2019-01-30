package com.nukkitx.protocol.bedrock.v332.serializer;

import com.nukkitx.protocol.bedrock.packet.ClientToServerHandshakePacket;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ClientToServerHandshakeSerializer_v332 implements PacketSerializer<ClientToServerHandshakePacket> {
    public static final ClientToServerHandshakeSerializer_v332 INSTANCE = new ClientToServerHandshakeSerializer_v332();

    @Override
    public void serialize(ByteBuf buffer, ClientToServerHandshakePacket packet) {
        // No payload
    }

    @Override
    public void deserialize(ByteBuf buffer, ClientToServerHandshakePacket packet) {
        // No payload
    }
}
