package com.nukkitx.protocol.bedrock.v340.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.InventorySlotPacket;
import com.nukkitx.protocol.bedrock.v340.BedrockUtils;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class InventorySlotSerializer_v340 implements PacketSerializer<InventorySlotPacket> {
    public static final InventorySlotSerializer_v340 INSTANCE = new InventorySlotSerializer_v340();

    @Override
    public void serialize(ByteBuf buffer, InventorySlotPacket packet) {
        VarInts.writeUnsignedInt(buffer, packet.getContainerId());
        VarInts.writeUnsignedInt(buffer, packet.getInventorySlot());
        BedrockUtils.writeItemData(buffer, packet.getSlot());
    }

    @Override
    public void deserialize(ByteBuf buffer, InventorySlotPacket packet) {
        packet.setContainerId(VarInts.readUnsignedInt(buffer));
        packet.setInventorySlot(VarInts.readUnsignedInt(buffer));
        packet.setSlot(BedrockUtils.readItemData(buffer));
    }
}
