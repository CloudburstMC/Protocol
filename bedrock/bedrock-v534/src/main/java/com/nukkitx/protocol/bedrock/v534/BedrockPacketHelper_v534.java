package com.nukkitx.protocol.bedrock.v534;

import com.nukkitx.protocol.bedrock.data.SoundEvent;
import com.nukkitx.protocol.bedrock.data.entity.EntityEventType;
import com.nukkitx.protocol.bedrock.v527.BedrockPacketHelper_v527;

public class BedrockPacketHelper_v534 extends BedrockPacketHelper_v527 {
    public static final BedrockPacketHelper_v534 INSTANCE = new BedrockPacketHelper_v534();

    @Override
    protected void registerEntityEvents() {
        super.registerEntityEvents();

        this.addEntityEvent(78, EntityEventType.DRINK_MILK);
    }

    @Override
    protected void registerSoundEvents() {
        super.registerSoundEvents();

        this.addSoundEvent(432, SoundEvent.MILK_DRINK);
        this.addSoundEvent(441, SoundEvent.RECORD_PLAYING);
        this.addSoundEvent(442, SoundEvent.UNDEFINED);
    }
}
