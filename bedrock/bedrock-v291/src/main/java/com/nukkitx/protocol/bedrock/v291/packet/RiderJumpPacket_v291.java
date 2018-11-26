package com.nukkitx.protocol.bedrock.v291.packet;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.RiderJumpPacket;
import io.netty.buffer.ByteBuf;

public class RiderJumpPacket_v291 extends RiderJumpPacket {

    @Override
    public void encode(ByteBuf buffer) {
        VarInts.writeUnsignedInt(buffer, jumpStrength);
    }

    @Override
    public void decode(ByteBuf buffer) {
        jumpStrength = VarInts.readInt(buffer);
    }
}
