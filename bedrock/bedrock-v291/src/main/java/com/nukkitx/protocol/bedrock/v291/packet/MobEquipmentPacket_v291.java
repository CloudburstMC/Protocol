package com.nukkitx.protocol.bedrock.v291.packet;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.MobEquipmentPacket;
import com.nukkitx.protocol.bedrock.v291.BedrockUtils;
import io.netty.buffer.ByteBuf;

public class MobEquipmentPacket_v291 extends MobEquipmentPacket {

    @Override
    public void encode(ByteBuf buffer) {
        VarInts.writeUnsignedLong(buffer, runtimeEntityId);
        BedrockUtils.writeItemInstance(buffer, item);
        buffer.writeByte(inventorySlot);
        buffer.writeByte(hotbarSlot);
        buffer.writeByte(windowId);
    }

    @Override
    public void decode(ByteBuf buffer) {
        runtimeEntityId = VarInts.readUnsignedLong(buffer);
        item = BedrockUtils.readItemInstance(buffer);
        inventorySlot = buffer.readByte();
        hotbarSlot = buffer.readByte();
        windowId = buffer.readByte();
    }
}
