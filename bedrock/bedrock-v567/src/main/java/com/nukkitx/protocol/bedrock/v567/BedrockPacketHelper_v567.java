package com.nukkitx.protocol.bedrock.v567;

import com.nukkitx.protocol.bedrock.data.SoundEvent;
import com.nukkitx.protocol.bedrock.v560.BedrockPacketHelper_v560;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BedrockPacketHelper_v567 extends BedrockPacketHelper_v560 {

    public static final BedrockPacketHelper_v567 INSTANCE = new BedrockPacketHelper_v567();

    @Override
    protected void registerSoundEvents() {
        super.registerSoundEvents();

        this.soundEvents.remove(458); // Remove old undefined value
        this.soundEvents.put(458, SoundEvent.INSERT);
        this.soundEvents.put(459, SoundEvent.PICKUP);
        this.soundEvents.put(460, SoundEvent.INSERT_ENCHANTED);
        this.soundEvents.put(461, SoundEvent.PICKUP_ENCHANTED);
        this.soundEvents.put(462, SoundEvent.UNDEFINED);
    }
}
