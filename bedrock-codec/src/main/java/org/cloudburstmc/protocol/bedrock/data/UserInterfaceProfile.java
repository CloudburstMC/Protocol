package org.cloudburstmc.protocol.bedrock.data;

public enum UserInterfaceProfile {

    CLASSIC,
    POCKET;

    private static final UserInterfaceProfile[] VALUES = values();

    public static UserInterfaceProfile from(int id) {
        return VALUES[id];
    }
}
