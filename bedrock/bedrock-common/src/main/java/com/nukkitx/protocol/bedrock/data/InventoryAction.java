package com.nukkitx.protocol.bedrock.data;

import lombok.Value;

@Value
public class InventoryAction {
    private final InventorySource source;
    private final int slot;
    private final Item oldItem;
    private final Item newItem;
}
