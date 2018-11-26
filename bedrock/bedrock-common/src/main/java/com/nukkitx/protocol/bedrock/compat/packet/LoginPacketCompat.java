package com.nukkitx.protocol.bedrock.compat.packet;

import com.nukkitx.protocol.bedrock.packet.LoginPacket;
import io.netty.buffer.ByteBuf;

public class LoginPacketCompat extends LoginPacket {
    private boolean v1_2 = false;

    @Override
    public void encode(ByteBuf buffer) {

    }

    @Override
    public void decode(ByteBuf buffer) {

    }
}
