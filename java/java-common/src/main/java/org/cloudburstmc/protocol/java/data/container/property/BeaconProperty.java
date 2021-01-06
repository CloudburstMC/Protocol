package org.cloudburstmc.protocol.java.data.container.property;

public enum BeaconProperty implements ContainerProperty {
    POWER_LEVEL,
    EFFECT_1,
    EFFECT_2;

    private static final BeaconProperty[] VALUES = values();

    public static BeaconProperty getById(int id) {
        return VALUES[id];
    }
}
