package com.nukkitx.protocol.bedrock.v475;

import com.nukkitx.protocol.bedrock.data.LevelEventType;
import com.nukkitx.protocol.bedrock.data.SoundEvent;
import com.nukkitx.protocol.bedrock.v471.BedrockPacketHelper_v471;

public class BedrockPacketHelper_v475 extends BedrockPacketHelper_v471 {
    public static final BedrockPacketHelper_v475 INSTANCE = new BedrockPacketHelper_v475();

    @Override
    protected void registerLevelEvents() {
        super.registerLevelEvents();
        this.addLevelEvent(9801, LevelEventType.SLEEPING_PLAYERS);
    }

    @Override
    protected void registerSoundEvents() {
        super.registerSoundEvents();
        this.addSoundEvent(371, SoundEvent.RECORD_OTHERSIDE);
        this.addSoundEvent(372, SoundEvent.UNDEFINED);
    }
}
