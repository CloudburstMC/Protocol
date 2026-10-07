package org.cloudburstmc.protocol.bedrock.data.sound;

import lombok.Value;

@Value
public class FadeSoundData implements SoundData {
    float targetVolume;
    float duration;

    @Override
    public SoundDataType getType() {
        return SoundDataType.FADE;
    }
}
