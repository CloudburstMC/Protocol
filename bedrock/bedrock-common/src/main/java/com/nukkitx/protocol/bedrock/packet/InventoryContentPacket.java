package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.data.inventory.ItemData;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(doNotUseGetters = true, callSuper = false)
public class InventoryContentPacket extends BedrockPacket {
    private final Int2ObjectMap<ItemData> entries = new Int2ObjectOpenHashMap<>();
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
