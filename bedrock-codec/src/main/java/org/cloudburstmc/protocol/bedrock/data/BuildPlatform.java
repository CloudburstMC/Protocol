package org.cloudburstmc.protocol.bedrock.data;

public enum BuildPlatform {

    UNDEFINED,
    /**
     * Android
     */
    GOOGLE,
    IOS,
    /**
     * Mac OS
     */
    OSX,
    /**
     * Kindle, FireTV
     */
    AMAZON,
    GEAR_VR,
    HOLOLENS,
    /**
     * Windows Store version
     */
    UWP,
    /**
     * Educational edition
     */
    WIN_32,
    DEDICATED,
    /**
     * Apple TV
     */
    TV_OS,
    /**
     * Playstation
     */
    SONY,
    /**
     * Nintendo Switch
     */
    NX,
    XBOX,
    WINDOWS_PHONE,
    LINUX;

    private static final BuildPlatform[] VALUES = values();

    public static BuildPlatform from(int id) {
        return id > 0 && id < VALUES.length ? VALUES[id] : VALUES[0];
    }
}
