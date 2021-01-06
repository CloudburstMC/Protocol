package org.cloudburstmc.protocol.java.data.container.property;

public enum FurnaceProperty implements ContainerProperty {
    BURN_TIME,
    CURRENT_ITEM_BURN_TIME,
    COOK_TIME,
    TOTAL_COOK_TIME;

    private static final FurnaceProperty[] VALUES = values();

    public static FurnaceProperty getById(int id) {
        return VALUES[id];
    }
}
