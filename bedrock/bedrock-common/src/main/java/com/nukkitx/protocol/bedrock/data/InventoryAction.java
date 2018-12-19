package com.nukkitx.protocol.bedrock.data;

import lombok.Value;

@Value
public class InventoryAction {
    private final InventorySource source;
    private final int slot;
    private final Item fromItem;
    private final Item toItem;

    public InventoryAction reverse() {
        return new InventoryAction(source, slot, toItem, fromItem);
    }
}
