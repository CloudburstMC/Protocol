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
     * @deprecated since v588
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
     * @since  v588
     */
    KICK_COMMAND,
    /**
     * @since  v588
     */
    TITLE_COMMAND,
    /**
     * @since  v588
     */
    SUMMON_COMMAND
}