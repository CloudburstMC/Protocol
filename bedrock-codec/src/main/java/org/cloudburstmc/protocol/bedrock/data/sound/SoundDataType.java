package org.cloudburstmc.protocol.bedrock.data.sound;

public enum SoundDataType {
    STOP,
    SET_VOLUME,
    SET_PITCH,
    FADE,
    SEEK_TO,
    PAUSE,
    RESUME;

    private static final SoundDataType[] VALUES = SoundDataType.values();

    public static SoundDataType from(int id) {
        return VALUES[id];
    }
}
