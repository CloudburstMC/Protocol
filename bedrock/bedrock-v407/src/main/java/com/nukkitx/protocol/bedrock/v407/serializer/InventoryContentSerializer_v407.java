package com.nukkitx.protocol.bedrock.v407.serializer;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.data.inventory.ItemData;
import com.nukkitx.protocol.bedrock.packet.InventoryContentPacket;
import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InventoryContentSerializer_v407 implements BedrockPacketSerializer<InventoryContentPacket> {
    public static final InventoryContentSerializer_v407 INSTANCE = new InventoryContentSerializer_v407();


    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, InventoryContentPacket packet) {
        VarInts.writeUnsignedInt(buffer, packet.getContainerId());

        VarInts.writeUnsignedInt(buffer, packet.getEntries().size());
        for (Int2ObjectMap.Entry<ItemData> entry : packet.getEntries().int2ObjectEntrySet()) {
            VarInts.writeInt(buffer, entry.getIntKey());
            helper.writeItem(buffer, entry.getValue());
        }
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, InventoryContentPacket packet) {
        packet.setContainerId(VarInts.readUnsignedInt(buffer));

        int count = VarInts.readUnsignedInt(buffer);
        for (int i = 0; i < count; i++) {
            int networkId = VarInts.readInt(buffer);
            packet.getEntries().put(networkId, helper.readItem(buffer));
        }
    }
}
