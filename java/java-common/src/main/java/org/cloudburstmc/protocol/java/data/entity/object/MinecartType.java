package org.cloudburstmc.protocol.java.data.entity.object;

public enum MinecartType implements ObjectData {
    NORMAL,
    CHEST,
    POWERED,
    TNT,
    MOB_SPAWNER,
    HOPPER,
    COMMAND_BLOCK;

    private static final MinecartType[] VALUES = values();

    public static MinecartType getById(int id) {
        return VALUES[id];
    }
}
