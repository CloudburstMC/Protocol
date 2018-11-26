package com.nukkitx.protocol.bedrock.compat.packet;

import com.nukkitx.protocol.bedrock.packet.PlayStatusPacket;
import io.netty.buffer.ByteBuf;

public class PlayStatusPacketCompat extends PlayStatusPacket {
    private boolean v1_2 = false;

    @Override
    public void encode(ByteBuf buffer) {

    }

    @Override
    public void decode(ByteBuf buffer) {

    }
}
