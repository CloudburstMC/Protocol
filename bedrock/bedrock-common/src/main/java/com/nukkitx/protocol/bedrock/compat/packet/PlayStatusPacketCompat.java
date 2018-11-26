package com.nukkitx.protocol.bedrock.compat.packet;

import com.nukkitx.protocol.bedrock.packet.PlayStatusPacket;
import io.netty.buffer.ByteBuf;
import lombok.Setter;

public class PlayStatusPacketCompat extends PlayStatusPacket {
    @Setter
    private boolean v1_2 = false;

    @Override
    public void encode(ByteBuf buffer) {
        if (v1_2) {
            buffer.writeByte(0).writeByte(0);
        }
        buffer.writeInt(status.ordinal());
    }

    @Override
    public void decode(ByteBuf buffer) {
        throw new UnsupportedOperationException();
    }
}
