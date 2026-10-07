package org.cloudburstmc.protocol.bedrock.data.sound;

import lombok.Value;

@Value
public class SetVolumeSoundData implements SoundData {
    float volume;

    @Override
    public SoundDataType getType() {
        return SoundDataType.SET_VOLUME;
    }
}
