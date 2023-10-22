package org.cloudburstmc.protocol.bedrock.data;

public enum UserInterfaceType {

    CLASSIC,
    POCKET;

    private static final UserInterfaceType[] VALUES = values();

    public static UserInterfaceType from(int id) {
        return VALUES[id];
    }
}
