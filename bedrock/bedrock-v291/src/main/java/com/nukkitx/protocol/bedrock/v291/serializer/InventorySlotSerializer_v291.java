package com.nukkitx.protocol.bedrock.v291.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.packet.InventorySlotPacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InventorySlotSerializer_v291 implements BedrockPacketSerializer<InventorySlotPacket> {
    public static final InventorySlotSerializer_v291 INSTANCE = new InventorySlotSerializer_v291();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, InventorySlotPacket packet) {
        VarInts.writeUnsignedInt(buffer, packet.getContainerId());
        VarInts.writeUnsignedInt(buffer, packet.getSlot());
        helper.writeItemInstance(buffer, packet.getItemInstance());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, InventorySlotPacket packet) {
        packet.setContainerId(VarInts.readUnsignedInt(buffer));
        packet.setSlot(VarInts.readUnsignedInt(buffer));
        packet.setItemInstance(helper.readItemInstance(buffer));
    }
}
