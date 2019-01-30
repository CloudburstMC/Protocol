package com.nukkitx.protocol.bedrock.v332.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.InventorySlotPacket;
import com.nukkitx.protocol.bedrock.v332.BedrockUtils;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class InventorySlotSerializer_v332 implements PacketSerializer<InventorySlotPacket> {
    public static final InventorySlotSerializer_v332 INSTANCE = new InventorySlotSerializer_v332();


    @Override
    public void serialize(ByteBuf buffer, InventorySlotPacket packet) {
        VarInts.writeUnsignedInt(buffer, packet.getWindowId());
        VarInts.writeUnsignedInt(buffer, packet.getInventorySlot());
        BedrockUtils.writeItemInstance(buffer, packet.getSlot());
    }

    @Override
    public void deserialize(ByteBuf buffer, InventorySlotPacket packet) {
        packet.setWindowId(VarInts.readUnsignedInt(buffer));
        packet.setInventorySlot(VarInts.readUnsignedInt(buffer));
        packet.setSlot(BedrockUtils.readItemInstance(buffer));
    }
}
