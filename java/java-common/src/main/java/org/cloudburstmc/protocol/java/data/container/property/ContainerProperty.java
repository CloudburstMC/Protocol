package org.cloudburstmc.protocol.java.data.container.property;

public enum ContainerProperty {
    BURN_TIME(0),
    CURRENT_ITEM_BURN_TIME(0),
    COOK_TIME(0),
    TOTAL_COOK_TIME(0),
    LEVEL_SLOT_1(1),
    LEVEL_SLOT_2(1),
    LEVEL_SLOT_3(1),
    XP_SEED(1),
    ENCH_ID_SLOT_1(1),
    ENCH_ID_SLOT_2(1),
    ENCH_ID_SLOT_3(1),
    ENCH_LEVEL_SLOT_1(1),
    ENCH_LEVEL_SLOT_2(1),
    ENCH_LEVEL_SLOT_3(1),
    POWER_LEVEL(2),
    EFFECT_1(2),
    EFFECT_2(2),
    REPAIR_COST(3),
    BREW_TIME(4),
    FUEL_TIME(4),
    SELECTED_RECIPE(5),
    SELECTED_PATTERN(6),
    PAGE_NUMBER(7);

    public final int id;

    ContainerProperty(int id) {
        this.id = id;
    }

    private static final ContainerProperty[] VALUES = values();

    public static ContainerProperty getById(int id) {
        return VALUES[id];
    }
}
