package org.cloudburstmc.protocol.bedrock.data.sound;

import lombok.Value;

@Value
public class StopSoundData implements SoundData {

    @Override
    public SoundDataType getType() {
        return SoundDataType.STOP;
    }
}
