package com.nukkitx.protocol.bedrock.v291.packet;

import com.nukkitx.protocol.bedrock.packet.ServerSettingsRequestPacket;
import io.netty.buffer.ByteBuf;

public class ServerSettingsRequestPacket_v291 extends ServerSettingsRequestPacket {

    @Override
    public void encode(ByteBuf buffer) {
        // No payload
    }

    @Override
    public void decode(ByteBuf buffer) {
        // No payload
    }
}
