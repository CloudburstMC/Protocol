package org.cloudburstmc.protocol.java.data.container.property;

public enum AnvilProperty implements ContainerProperty {
    REPAIR_COST;

    private static final AnvilProperty[] VALUES = values();

    public static AnvilProperty getById(int id) {
        return VALUES[id];
    }
}
