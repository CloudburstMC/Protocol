package com.nukkitx.protocol.bedrock.data;

public enum GameType {
    ADVENTURE,
    CREATIVE,
    CREATIVE_VIEWER,
    DEFAULT,
    SURVIVAL,
    SURVIVAL_VIEWER,
    WORLD_DEFAULT;

    private static final GameType[] VALUES = values();

    public static GameType from(int id) {
        return VALUES[id];
    }
}
