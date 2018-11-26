package com.nukkitx.protocol.bedrock.v291.packet;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.EntityEventPacket;
import io.netty.buffer.ByteBuf;

public class EntityEventPacket_v291 extends EntityEventPacket {

    @Override
    public void encode(ByteBuf buffer) {
        VarInts.writeUnsignedLong(buffer, runtimeEntityId);
        buffer.writeByte(event);
        VarInts.writeInt(buffer, data);
    }

    @Override
    public void decode(ByteBuf buffer) {
        runtimeEntityId = VarInts.readUnsignedLong(buffer);
        event = buffer.readByte();
        data = VarInts.readInt(buffer);
    }
}
