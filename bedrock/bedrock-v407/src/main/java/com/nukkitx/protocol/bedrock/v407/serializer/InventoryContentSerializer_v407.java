package com.nukkitx.protocol.bedrock.v407.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.data.inventory.ItemInstance;
import com.nukkitx.protocol.bedrock.packet.InventoryContentPacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InventoryContentSerializer_v407 implements BedrockPacketSerializer<InventoryContentPacket> {
    public static final InventoryContentSerializer_v407 INSTANCE = new InventoryContentSerializer_v407();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, InventoryContentPacket packet) {
        VarInts.writeUnsignedInt(buffer, packet.getContainerId());

        VarInts.writeUnsignedInt(buffer, packet.getItems().size());
        for (ItemInstance itemInstance : packet.getItems()) {
            helper.writeItemInstance(buffer, itemInstance);
        }
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, InventoryContentPacket packet) {
        packet.setContainerId(VarInts.readUnsignedInt(buffer));

        int count = VarInts.readUnsignedInt(buffer);
        for (int i = 0; i < count; i++) {
            packet.getItems().add(helper.readItemInstance(buffer));
        }
    }
}
