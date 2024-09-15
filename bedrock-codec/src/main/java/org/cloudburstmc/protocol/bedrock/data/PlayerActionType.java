package org.cloudburstmc.protocol.bedrock.data;

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
    /**
     * @deprecated since v729
     */
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
     * @since v527
     */
    START_ITEM_USE_ON,
    /**
     * @since v527
     */
    STOP_ITEM_USE_ON,
    /**
     * @since v567
     */
    HANDLED_TELEPORT,
    /**
     * @since v594
     */
    MISSED_SWING,
    /**
     * @since v594
     */
    START_CRAWLING,
    /**
     * @since v594
     */
    STOP_CRAWLING,
    /**
     * @since v618
     */
    START_FLYING,
    /**
     * @since v618
     */
    STOP_FLYING,
    /**
     * @since v622
     */
    RECEIVED_SERVER_DATA
}
