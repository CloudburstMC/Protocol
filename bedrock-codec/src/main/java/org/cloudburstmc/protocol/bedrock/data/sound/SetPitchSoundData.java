package org.cloudburstmc.protocol.bedrock.data.sound;

import lombok.Value;

@Value
public class SetPitchSoundData implements SoundData {
    float pitch;

    @Override
    public SoundDataType getType() {
        return SoundDataType.SET_PITCH;
    }
}
