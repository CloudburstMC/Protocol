package org.cloudburstmc.protocol.java.data.container.property;

public enum StonecutterProperty implements ContainerProperty {
    SELECTED_RECIPE;

    private static final StonecutterProperty[] VALUES = values();

    public static StonecutterProperty getById(int id) {
        return VALUES[id];
    }
}
