package com.nukkitx.protocol.bedrock.data.inventory;

import lombok.Value;

@Value
public class InventoryActionData {
    private final InventorySource source;
    private final int slot;
    private final ItemData fromItem;
    private final ItemData toItem;

    public InventoryActionData reverse() {
        return new InventoryActionData(source, slot, toItem, fromItem);
    }
}
