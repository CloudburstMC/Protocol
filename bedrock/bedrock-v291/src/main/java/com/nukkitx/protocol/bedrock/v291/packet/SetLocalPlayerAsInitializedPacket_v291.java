package com.nukkitx.protocol.bedrock.v291.packet;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.SetLocalPlayerAsInitializedPacket;
import io.netty.buffer.ByteBuf;

public class SetLocalPlayerAsInitializedPacket_v291 extends SetLocalPlayerAsInitializedPacket {

    @Override
    public void encode(ByteBuf buffer) {
        VarInts.writeUnsignedLong(buffer, runtimeEntityId);
    }

    @Override
    public void decode(ByteBuf buffer) {
        runtimeEntityId = VarInts.readUnsignedLong(buffer);
    }
}
