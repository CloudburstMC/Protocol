package org.cloudburstmc.protocol.bedrock.data.inventory;

public enum ItemUseType {
    UNKNOWN,
    EQUIP_ARMOR,
    EAT,
    ATTACK,
    CONSUME,
    THROW,
    SHOOT,
    PLACE,
    FILL_BOTTLE,
    FILL_BUCKET,
    POUR_BUCKET,
    USE_TOOL,
    INTERACT,
    RETRIEVED,
    DYED,
    TRADED,
    /**
     * @since 594
     */
    BRUSHING_COMPLETED,
    /**
     * @since v685
     */
    OPENED_VAULT
}
