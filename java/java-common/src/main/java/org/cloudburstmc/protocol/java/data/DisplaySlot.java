package org.cloudburstmc.protocol.java.data;

public enum DisplaySlot {
    PLAYER_LIST,
    SIDEBAR,
    BELOW_NAME,

    SIDEBAR_TEAM_BLACK,
    SIDEBAR_TEAM_DARK_BLUE,
    SIDEBAR_TEAM_DARK_GREEN,
    SIDEBAR_TEAM_DARK_AQUA,
    SIDEBAR_TEAM_DARK_RED,
    SIDEBAR_TEAM_DARK_PURPLE,
    SIDEBAR_TEAM_GOLD,
    SIDEBAR_TEAM_GRAY,
    SIDEBAR_TEAM_DARK_GRAY,
    SIDEBAR_TEAM_BLUE,
    SIDEBAR_TEAM_GREEN,
    SIDEBAR_TEAM_AQUA,
    SIDEBAR_TEAM_RED,
    SIDEBAR_TEAM_LIGHT_PURPLE,
    SIDEBAR_TEAM_YELLOW,
    SIDEBAR_TEAM_WHITE;

    private static final DisplaySlot[] VALUES = values();

    public static DisplaySlot getById(int id) {
        return VALUES[id];
    }
}
