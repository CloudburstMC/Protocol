package com.nukkitx.protocol.bedrock.v313.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.InventorySlotPacket;
import com.nukkitx.protocol.bedrock.v313.BedrockUtils;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class InventorySlotSerializer_v313 implements PacketSerializer<InventorySlotPacket> {
    public static final InventorySlotSerializer_v313 INSTANCE = new InventorySlotSerializer_v313();


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
