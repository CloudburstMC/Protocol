package com.nukkitx.protocol.bedrock.beta;

import com.nukkitx.protocol.bedrock.data.LevelEventType;
import com.nukkitx.protocol.bedrock.data.SoundEvent;
import com.nukkitx.protocol.bedrock.data.entity.EntityData;
import com.nukkitx.protocol.bedrock.data.entity.EntityFlag;
import com.nukkitx.protocol.bedrock.v503.BedrockPacketHelper_v503;

public class BedrockPacketHelperBeta extends BedrockPacketHelper_v503 {
    public static final BedrockPacketHelperBeta INSTANCE = new BedrockPacketHelperBeta();

    @Override
    protected void registerEntityData() {
        super.registerEntityData();

        this.addEntityData(128, EntityData.PLAYER_LAST_DEATH_POS);
        this.addEntityData(129, EntityData.PLAYER_LAST_DEATH_DIMENSION);
        this.addEntityData(130, EntityData.PLAYER_HAS_DIED);
    }

    @Override
    protected void registerEntityFlags() {
        super.registerEntityFlags();

        this.addEntityFlag(105, EntityFlag.SONIC_BOOM);
    }


    @Override
    protected void registerLevelEvents() {
        super.registerLevelEvents();

        this.addLevelEvent(2000 + 39, LevelEventType.SONIC_EXPLOSION);
        this.addLevelEvent(0x4000 + 83, LevelEventType.PARTICLE_SCULK_SOUL);
    }

    @Override
    protected void registerSoundEvents() {
        super.registerSoundEvents();

        this.addSoundEvent(426, SoundEvent.IMITATE_WARDEN);
        this.addSoundEvent(427, SoundEvent.LISTENING_ANGRY);
        this.addSoundEvent(428, SoundEvent.ITEM_GIVEN);
        this.addSoundEvent(429, SoundEvent.ITEM_TAKEN);
        this.addSoundEvent(430, SoundEvent.DISAPPEARED);
        this.addSoundEvent(431, SoundEvent.REAPPEARED);
        this.addSoundEvent(433, SoundEvent.FROGSPAWN_HATCHED);
        this.addSoundEvent(434, SoundEvent.LAY_SPAWN);
        this.addSoundEvent(435, SoundEvent.FROGSPAWN_BREAK);
        this.addSoundEvent(436, SoundEvent.SONIC_BOOM);
        this.addSoundEvent(437, SoundEvent.SONIC_CHARGE);
        this.addSoundEvent(438, SoundEvent.ITEM_THROWN);
        this.addSoundEvent(439, SoundEvent.RECORD_5);
        this.addSoundEvent(440, SoundEvent.CONVERT_TO_FROG);
        this.addSoundEvent(441, SoundEvent.UNDEFINED);
    }
}
