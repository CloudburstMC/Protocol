package com.nukkitx.protocol.bedrock.v291.packet;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.EntityFallPacket;
import io.netty.buffer.ByteBuf;

public class EntityFallPacket_v291 extends EntityFallPacket {

    @Override
    public void encode(ByteBuf buffer) {
        VarInts.writeUnsignedLong(buffer, runtimeEntityId);
        buffer.writeFloatLE(fallDistance);
        buffer.writeBoolean(inVoid);
    }

    @Override
    public void decode(ByteBuf buffer) {
        runtimeEntityId = VarInts.readUnsignedLong(buffer);
        fallDistance = buffer.readFloatLE();
        inVoid = buffer.readBoolean();
    }
}
