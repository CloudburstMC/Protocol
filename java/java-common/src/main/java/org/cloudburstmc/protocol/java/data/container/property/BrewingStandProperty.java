package org.cloudburstmc.protocol.java.data.container.property;

public enum BrewingStandProperty implements ContainerProperty {
    BREW_TIME,
    FUEL_TIME;

    private static final BrewingStandProperty[] VALUES = values();

    public static BrewingStandProperty getById(int id) {
        return VALUES[id];
    }
}
