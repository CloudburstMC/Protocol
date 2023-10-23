package org.cloudburstmc.protocol.bedrock.data;

public enum BuildPlatform {

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

    private static final BuildPlatform[] VALUES = values();

    public static BuildPlatform from(int id) {
        return id < VALUES.length ? VALUES[id] : VALUES[0];
    }
}
