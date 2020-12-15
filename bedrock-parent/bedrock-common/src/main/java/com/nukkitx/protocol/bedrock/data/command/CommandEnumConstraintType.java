package com.nukkitx.protocol.bedrock.data.command;

public enum CommandEnumConstraintType {
    CHEATS_ENABLED,
    UNKNOWN_1,
    UNKNOWN_2;

    private static final CommandEnumConstraintType[] VALUES = values();

    public static CommandEnumConstraintType byId(int id) {
        if (id >= 0 && id < VALUES.length) {
            return VALUES[id];
        }
        throw new UnsupportedOperationException("Unknown CommandEnumConstraintType ID: " + id);
    }
}
