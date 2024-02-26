package org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request;

public enum TextProcessingEventOrigin {
    SERVER_CHAT_PUBLIC,
    SERVER_CHAT_WHISPER,
    SIGN_TEXT,
    ANVIL_TEXT,
    BOOK_AND_QUILL_TEXT,
    COMMAND_BLOCK_TEXT,
    BLOCK_ENTITY_DATA_TEXT,
    JOIN_EVENT_TEXT,
    LEAVE_EVENT_TEXT,
    SLASH_COMMAND_TEXT,
    CARTOGRAPHY_TEXT,
    /**
     * @deprecated since v589
     */
    SLASH_COMMAND_NON_CHAT,
    /**
     * @since v560
     * @deprecated since v575
     */
    SCOREBOARD_TEXT,
    /**
     * @since v560
     * @deprecated since v575
     */
    TICKING_AREA_TEXT,
    /**
     * @since v589
     */
    KICK_COMMAND,
    /**
     * @since v589
     */
    TITLE_COMMAND,
    /**
     * @since v589
     */
    SUMMON_COMMAND,
    /**
     * @deprecated since v662
     * @since v618
     */
    @Deprecated
    PASS_THROUGH_WITHOUT_SIFT,
    /**
     * @since v662
     */
    SERVER_FORM
}