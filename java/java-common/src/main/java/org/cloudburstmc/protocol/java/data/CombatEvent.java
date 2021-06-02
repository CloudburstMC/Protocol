package org.cloudburstmc.protocol.java.data;

public enum CombatEvent {
    ENTER_COMBAT,
    EXIT_COMBAT,
    ENTITY_DIED;

    private static final CombatEvent[] VALUES = values();

    public static CombatEvent getById(int id) {
        return VALUES.length > id ? VALUES[id] : null;
    }
}
