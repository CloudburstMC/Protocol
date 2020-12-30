package org.cloudburstmc.protocol.java.data;

public enum Direction {
    SOUTH,
    WEST,
    NORTH,
    EAST;

    private static final Direction[] VALUES = values();

    public static Direction getById(int id) {
        return VALUES[id];
    }
}
