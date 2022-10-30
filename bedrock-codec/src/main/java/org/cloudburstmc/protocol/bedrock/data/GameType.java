package org.cloudburstmc.protocol.bedrock.data;

public enum GameType {
    SURVIVAL,
    CREATIVE,
    ADVENTURE,
    @Deprecated
    SURVIVAL_VIEWER,
    @Deprecated
    CREATIVE_VIEWER,
    DEFAULT,
    /**
     * @since v503
     */
    SPECTATOR;

    private static final GameType[] VALUES = values();

    public static GameType from(int id) {
        return VALUES[id];
    }
}
