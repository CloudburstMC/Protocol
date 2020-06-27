package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.data.inventory.ItemData;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import it.unimi.dsi.fastutil.ints.Int2ObjectRBTreeMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectSortedMap;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(doNotUseGetters = true, callSuper = false)
public class InventoryContentPacket extends BedrockPacket {
    private final Int2ObjectSortedMap<ItemData> entries = new Int2ObjectRBTreeMap<>();
    private int containerId;

    @Override
    public final boolean handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.INVENTORY_CONTENT;
    }

    @Deprecated
    public final ItemData[] getContents() {
        return entries.values().toArray(new ItemData[0]);
    }

    @Deprecated
    public void setContents(ItemData[] data) {
        entries.clear();
        for(int i = 0;i < data.length; i++) {
            entries.put(i, data[i]);
        }
    }

}
