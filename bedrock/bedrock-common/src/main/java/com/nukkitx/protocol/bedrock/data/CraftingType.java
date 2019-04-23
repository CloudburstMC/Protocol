package com.nukkitx.protocol.bedrock.data;

public enum CraftingType {
    SHAPELESS,
    SHAPED,
    FURNACE,
    FURNACE_DATA,
    MULTI,
    SHULKER_BOX,
    SHAPELESS_CHEMISTRY,
    SHAPED_CHEMISTRY;

    private static final CraftingType[] VALUES = values();

    public static CraftingType byId(int id) {
        if (id >= 0 && id < VALUES.length) {
            return VALUES[id];
        }
        return null;
    }
}
