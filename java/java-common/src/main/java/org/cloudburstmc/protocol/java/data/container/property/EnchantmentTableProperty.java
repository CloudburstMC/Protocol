package org.cloudburstmc.protocol.java.data.container.property;

public enum EnchantmentTableProperty implements ContainerProperty {
    LEVEL_SLOT_1,
    LEVEL_SLOT_2,
    LEVEL_SLOT_3,
    XP_SEED,
    ENCH_ID_SLOT_1,
    ENCH_ID_SLOT_2,
    ENCH_ID_SLOT_3,
    ENCH_LEVEL_SLOT_1,
    ENCH_LEVEL_SLOT_2,
    ENCH_LEVEL_SLOT_3;

    private static final EnchantmentTableProperty[] VALUES = values();

    public static EnchantmentTableProperty getById(int id) {
        return VALUES[id];
    }
}
