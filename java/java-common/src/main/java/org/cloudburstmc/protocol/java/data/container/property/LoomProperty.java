package org.cloudburstmc.protocol.java.data.container.property;

public enum LoomProperty implements ContainerProperty {
    SELECTED_PATTERN;

    private static final LoomProperty[] VALUES = values();

    public static LoomProperty getById(int id) {
        return VALUES[id];
    }
}
