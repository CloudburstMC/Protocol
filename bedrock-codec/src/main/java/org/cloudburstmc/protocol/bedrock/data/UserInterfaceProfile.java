package org.cloudburstmc.protocol.bedrock.data;

public enum UserInterfaceProfile {

    CLASSIC,
    POCKET,
    NONE;

    private static final UserInterfaceProfile[] VALUES = values();

    public static UserInterfaceProfile from(int id) {
        return VALUES[id];
    }
}
