package org.cloudburstmc.protocol.bedrock.data;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum BuildPlatform {
    UNKNOWN(-1),
    /**
     * Android
     */
    GOOGLE(1),
    IOS(2),
    /**
     * macOS
     */
    OSX(3),
    /**
     * Kindle, FireTV
     */
    AMAZON(4),
    GEAR_VR(5),
    HOLOLENS(6),
    /**
     * Windows Store version
     */
    UWP(7),
    /**
     * Education Edition
     */
    WIN32(8),
    DEDICATED(9),
    /**
     * Apple TV
     */
    TV_OS(10),
    /**
     * PlayStation
     */
    SONY(11),
    /**
     * Nintendo Switch
     */
    NX(12),
    XBOX(13),
    WINDOWS_PHONE(14),
    LINUX(15);

    private static final BuildPlatform[] VALUES = values();

    @Getter
    private final int id;

    public static BuildPlatform from(int id) {
        for (BuildPlatform value : VALUES) {
            if (value.getId() == id) {
                return value;
            }
        }
        return UNKNOWN;
    }
}
