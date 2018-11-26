package com.nukkitx.protocol.bedrock.v291.packet;

import com.nukkitx.protocol.bedrock.packet.ClientToServerHandshakePacket;
import io.netty.buffer.ByteBuf;

public class ClientToServerHandshakePacket_v291 extends ClientToServerHandshakePacket {

    @Override
    public void encode(ByteBuf buffer) {
        // No payload
    }

    @Override
    public void decode(ByteBuf buffer) {
        // No payload
    }
}
