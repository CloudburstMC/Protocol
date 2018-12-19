package com.nukkitx.protocol.bedrock.data;

import lombok.Value;

@Value
public class NetworkInventoryAction {
    private final InventorySource source;
    private final int slot;
    private final Item oldItem;
    private final Item newItem;
}
