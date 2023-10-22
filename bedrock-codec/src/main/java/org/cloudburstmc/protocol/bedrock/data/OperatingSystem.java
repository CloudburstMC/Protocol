package org.cloudburstmc.protocol.bedrock.data;

public enum OperatingSystem {

    UNDEFINED,
    ANDROID,
    IOS,
    OSX,
    AMAZON,
    GEAR_VR,
    HOLOLENS,
    UWP,
    WIN_32,
    DEDICATED,
    APPLE_TV,
    PLAYSTATION,
    NINTENDO_SWITCH,
    XBOX,
    WINDOWS_PHONE;

    private static final OperatingSystem[] VALUES = values();

    public static OperatingSystem from(int id) {
        return id < VALUES.length ? VALUES[id] : VALUES[0];
    }
}
