package org.cloudburstmc.protocol.bedrock.data.sound;

import lombok.Value;

@Value
public class SeekToSoundData implements SoundData {
    float seconds;

    @Override
    public SoundDataType getType() {
        return SoundDataType.SEEK_TO;
    }
}
