package com.nukkitx.protocol.bedrock.v291.packet;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.InventorySlotPacket;
import com.nukkitx.protocol.bedrock.v291.BedrockUtils;
import io.netty.buffer.ByteBuf;

public class InventorySlotPacket_v291 extends InventorySlotPacket {

    @Override
    public void encode(ByteBuf buffer) {
        VarInts.writeUnsignedInt(buffer, windowId);
        VarInts.writeUnsignedInt(buffer, inventorySlot);
        BedrockUtils.writeItemInstance(buffer, slot);
    }

    @Override
    public void decode(ByteBuf buffer) {
        windowId = VarInts.readUnsignedInt(buffer);
        inventorySlot = VarInts.readUnsignedInt(buffer);
        slot = BedrockUtils.readItemInstance(buffer);
    }
}
