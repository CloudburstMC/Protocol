package org.cloudburstmc.protocol.bedrock.data;

public enum GraphicsMode {
    SIMPLE,
    FANCY,
    ADVANCED,
    RAY_TRACED;

    private static final GraphicsMode[] VALUES = values();

    public static GraphicsMode from(int id) {
        return VALUES[id];
    }
}