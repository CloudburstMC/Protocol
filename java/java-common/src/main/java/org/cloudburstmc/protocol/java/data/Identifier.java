package org.cloudburstmc.protocol.java.data;

import lombok.Value;

@Value
public class Identifier {
    private Identifier() {
    }

    public static String formalize(String identifier) {
        return !identifier.contains(":") ? "minecraft:" + identifier : identifier;
    }
}
