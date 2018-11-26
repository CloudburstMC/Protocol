package com.nukkitx.protocol.bedrock.v291.packet;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.PlayerHotbarPacket;
import io.netty.buffer.ByteBuf;

public class PlayerHotbarPacket_v291 extends PlayerHotbarPacket {

    @Override
    public void encode(ByteBuf buffer) {
        VarInts.writeUnsignedInt(buffer, selectedHotbarSlot);
        buffer.writeByte(windowId);
        buffer.writeBoolean(selectHotbarSlot);
    }

    @Override
    public void decode(ByteBuf buffer) {
        selectedHotbarSlot = VarInts.readUnsignedInt(buffer);
        windowId = buffer.readByte();
        selectHotbarSlot = buffer.readBoolean();
    }
}
