package com.nukkitx.protocol.bedrock.v332.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.data.ContainerId;
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
        VarInts.writeUnsignedInt(buffer, packet.getContainerId().id());
        VarInts.writeUnsignedInt(buffer, packet.getInventorySlot());
        BedrockUtils.writeItemData(buffer, packet.getSlot());
    }

    @Override
    public void deserialize(ByteBuf buffer, InventorySlotPacket packet) {
        packet.setContainerId(ContainerId.byId(VarInts.readUnsignedInt(buffer)));
        packet.setInventorySlot(VarInts.readUnsignedInt(buffer));
        packet.setSlot(BedrockUtils.readItemData(buffer));
    }
}
