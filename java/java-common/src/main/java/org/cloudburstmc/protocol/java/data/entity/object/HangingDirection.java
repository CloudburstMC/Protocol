package org.cloudburstmc.protocol.java.data.entity.object;

public enum HangingDirection implements ObjectData {
    DOWN,
    UP,
    NORTH,
    SOUTH,
    WEST,
    EAST;

    private static final HangingDirection[] VALUES = values();

    public static HangingDirection getById(int id) {
        return VALUES[id];
    }
}
