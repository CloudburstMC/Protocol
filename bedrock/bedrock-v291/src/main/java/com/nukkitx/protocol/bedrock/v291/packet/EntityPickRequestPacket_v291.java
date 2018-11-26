package com.nukkitx.protocol.bedrock.v291.packet;

import com.nukkitx.protocol.bedrock.packet.EntityPickRequestPacket;
import io.netty.buffer.ByteBuf;

public class EntityPickRequestPacket_v291 extends EntityPickRequestPacket {

    @Override
    public void encode(ByteBuf buffer) {
        buffer.writeLongLE(runtimeEntityId);
        buffer.writeByte(hotbarSlot);
    }

    @Override
    public void decode(ByteBuf buffer) {
        runtimeEntityId = buffer.readLongLE();
        hotbarSlot = buffer.readByte();
    }
}
