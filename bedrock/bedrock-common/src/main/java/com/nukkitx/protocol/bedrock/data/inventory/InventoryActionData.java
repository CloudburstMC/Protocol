package com.nukkitx.protocol.bedrock.data.inventory;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class InventoryActionData {
    private final InventorySource source;
    private final int slot;
    private final ItemData fromItem;
    private final ItemData toItem;
    private final int stackNetworkId;

    public InventoryActionData(InventorySource source, int slot, ItemData fromItem, ItemData toItem) {
        this(source, slot, fromItem, toItem, 0);
    }

    public InventoryActionData reverse() {
        return new InventoryActionData(source, slot, toItem, fromItem, stackNetworkId);
    }
}
