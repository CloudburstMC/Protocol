package com.nukkitx.protocol.bedrock.v407.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.data.inventory.ItemDataInstance;
import com.nukkitx.protocol.bedrock.packet.InventorySlotPacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InventorySlotSerializer_v407 implements BedrockPacketSerializer<InventorySlotPacket> {
    public static final InventorySlotSerializer_v407 INSTANCE = new InventorySlotSerializer_v407();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, InventorySlotPacket packet) {
        VarInts.writeUnsignedInt(buffer, packet.getInstance().getNetworkId());
        VarInts.writeUnsignedInt(buffer, packet.getContainerId());
        VarInts.writeUnsignedInt(buffer, packet.getSlot());
        helper.writeItem(buffer, packet.getInstance().getItem());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, InventorySlotPacket packet) {
        int networkId = VarInts.readInt(buffer);
        packet.setContainerId(VarInts.readUnsignedInt(buffer));
        packet.setSlot(VarInts.readUnsignedInt(buffer));

        packet.setInstance(ItemDataInstance.of(networkId, helper.readItem(buffer)));
    }
}
