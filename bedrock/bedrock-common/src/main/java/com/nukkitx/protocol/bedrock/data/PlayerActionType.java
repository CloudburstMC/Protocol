package com.nukkitx.protocol.bedrock.data;

public enum PlayerActionType {
    START_BREAK,
    ABORT_BREAK,
    STOP_BREAK,
    GET_UPDATED_BLOCK,
    DROP_ITEM,
    START_SLEEP,
    STOP_SLEEP,
    RESPAWN,
    JUMP,
    START_SPRINT,
    STOP_SPRINT,
    START_SNEAK,
    STOP_SNEAK,
    DIMENSION_CHANGE_REQUEST_OR_CREATIVE_DESTROY_BLOCK,
    DIMENSION_CHANGE_SUCCESS,
    START_GLIDE,
    STOP_GLIDE,
    BUILD_DENIED,
    CONTINUE_BREAK,
    CHANGE_SKIN,
    SET_ENCHANTMENT_SEED,
    START_SWIMMING,
    STOP_SWIMMING,
    START_SPIN_ATTACK,
    STOP_SPIN_ATTACK,
    BLOCK_INTERACT,
    /**
     * @since v428
     */
    BLOCK_PREDICT_DESTROY,
    /**
     * @since v428
     */
    BLOCK_CONTINUE_DESTROY,
    /**
     * @since v526
     */
    ITEM_USE_ON_START,
    /**
     * @since v526
     */
    ITEM_USE_ON_STOP
}
