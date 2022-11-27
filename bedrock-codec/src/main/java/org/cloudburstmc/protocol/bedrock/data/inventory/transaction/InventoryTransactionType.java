package org.cloudburstmc.protocol.bedrock.data.inventory.transaction;

public enum InventoryTransactionType {
    NORMAL,
    INVENTORY_MISMATCH,
    ITEM_USE,
    ITEM_USE_ON_ENTITY,
    ITEM_RELEASE
}
