package org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.action;

public enum ItemStackRequestActionType {
    TAKE,
    PLACE,
    SWAP,
    DROP,
    DESTROY,
    CONSUME,
    CREATE,
    LAB_TABLE_COMBINE,
    BEACON_PAYMENT,
    MINE_BLOCK,
    CRAFT_RECIPE,
    CRAFT_RECIPE_AUTO,
    CRAFT_CREATIVE,
    CRAFT_RECIPE_OPTIONAL,
    CRAFT_REPAIR_AND_DISENCHANT,
    CRAFT_LOOM,
    CRAFT_NON_IMPLEMENTED_DEPRECATED,
    CRAFT_RESULTS_DEPRECATED,
    /**
     * @deprecated since 712
     */
    PLACE_IN_ITEM_CONTAINER,
    /**
     * @deprecated since 712
     */
    TAKE_FROM_ITEM_CONTAINER
}
