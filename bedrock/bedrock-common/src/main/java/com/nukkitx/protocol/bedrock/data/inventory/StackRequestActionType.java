package com.nukkitx.protocol.bedrock.data.inventory;

public enum StackRequestActionType {
    TAKE,
    PLACE,
    SWAP,
    DROP,
    DESTROY,
    CONSUME,
    CREATE,
    LAB_TABLE_COMBINE,
    BEACON_PAYMENT,
    CRAFT_RECIPE,
    CRAFT_RECIPE_AUTO,
    CRAFT_CREATIVE,
    CRAFT_NON_IMPLEMENTED_DEPRECATED,
    CRAFT_RESULTS_DEPRECATED;

    private static final StackRequestActionType[] VALUES = values();

    public static StackRequestActionType byId(int id) {
        if (id >= 0 && id < VALUES.length) {
            return VALUES[id];
        }
        throw new UnsupportedOperationException("Unknown StackRequestActionType ID: " + id);
    }
}
