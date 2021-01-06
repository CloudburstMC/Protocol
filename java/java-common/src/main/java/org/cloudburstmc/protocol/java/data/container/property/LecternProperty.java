package org.cloudburstmc.protocol.java.data.container.property;

public enum LecternProperty implements ContainerProperty {
    PAGE_NUMBER;

    private static final LecternProperty[] VALUES = values();

    public static LecternProperty getById(int id) {
        return VALUES[id];
    }
}
