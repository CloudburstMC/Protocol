package org.cloudburstmc.protocol.java.data.entity;

import lombok.Value;

import java.util.UUID;

@Value
public class AttributeModifier {
    UUID id;
    double amount;
    Operation operation;

    public enum Operation {
        ADD,
        ADD_MULTIPLIED,
        MULTIPLY;

        private static final Operation[] VALUES = values();

        public static Operation getById(int id) {
            return VALUES.length > id ? VALUES[id] : null;
        }
    }
}
