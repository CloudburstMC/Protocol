package org.cloudburstmc.protocol.bedrock.data.inventory;

public enum InventoryLayout {
    NONE,
    SURVIVAL,
    RECIPE_BOOK,
    CREATIVE;

    public static final InventoryLayout[] VALUES = values();
}