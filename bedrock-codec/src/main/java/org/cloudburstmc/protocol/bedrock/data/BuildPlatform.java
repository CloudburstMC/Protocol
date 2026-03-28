package org.cloudburstmc.protocol.bedrock.data;

public enum BuildPlatform {
    UNKNOWN,
    /**
     * Android
     */
    GOOGLE,
    IOS,
    /**
     * macOS
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
     * Education Edition
     */
    WIN32,
    DEDICATED,
    /**
     * Apple TV
     */
    TV_OS,
    /**
     * PlayStation
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
