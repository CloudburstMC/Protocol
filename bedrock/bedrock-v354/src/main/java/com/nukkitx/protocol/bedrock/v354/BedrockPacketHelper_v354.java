package com.nukkitx.protocol.bedrock.v354;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.data.LevelEventType;
import com.nukkitx.protocol.bedrock.data.SoundEvent;
import com.nukkitx.protocol.bedrock.data.entity.EntityFlag;
import com.nukkitx.protocol.bedrock.v340.BedrockPacketHelper_v340;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.nukkitx.protocol.bedrock.data.entity.EntityData.TRADE_XP;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BedrockPacketHelper_v354 extends BedrockPacketHelper_v340 {

    public static final BedrockPacketHelper INSTANCE = new BedrockPacketHelper_v354();

    @Override
    protected void registerEntityData() {
        super.registerEntityData();

        this.addEntityData(102, TRADE_XP);
    }

    @Override
    protected void registerEntityFlags() {
        super.registerEntityFlags();

        this.addEntityFlag(74, EntityFlag.BLOCKED_USING_DAMAGED_SHIELD);
        this.addEntityFlag(75, EntityFlag.SLEEPING);
        this.addEntityFlag(76, EntityFlag.WANTS_TO_WAKE);
        this.addEntityFlag(77, EntityFlag.TRADE_INTEREST);
        this.addEntityFlag(78, EntityFlag.DOOR_BREAKER);
        this.addEntityFlag(79, EntityFlag.BREAKING_OBSTRUCTION);
        this.addEntityFlag(80, EntityFlag.DOOR_OPENER);
        this.addEntityFlag(81, EntityFlag.IS_ILLAGER_CAPTAIN);
        this.addEntityFlag(82, EntityFlag.STUNNED);
        this.addEntityFlag(83, EntityFlag.ROARING);
        this.addEntityFlag(84, EntityFlag.DELAYED_ATTACK);
        this.addEntityFlag(85, EntityFlag.IS_AVOIDING_MOBS);
        this.addEntityFlag(86, EntityFlag.FACING_TARGET_TO_RANGE_ATTACK);
    }

    @Override
    protected void registerSoundEvents() {
        super.registerSoundEvents();

        this.addSoundEvent(187, SoundEvent.FLETCHING_TABLE_USE);

        this.addSoundEvent(257, SoundEvent.GRINDSTONE_USE);
        this.addSoundEvent(258, SoundEvent.BELL);
        this.addSoundEvent(259, SoundEvent.CAMPFIRE_CRACKLE);
        this.addSoundEvent(262, SoundEvent.SWEET_BERRY_BUSH_HURT);
        this.addSoundEvent(263, SoundEvent.SWEET_BERRY_BUSH_PICK);
        this.addSoundEvent(260, SoundEvent.ROAR);
        this.addSoundEvent(261, SoundEvent.STUN);
        this.addSoundEvent(264, SoundEvent.CARTOGRAPHY_TABLE_USE);
        this.addSoundEvent(265, SoundEvent.STONECUTTER_USE);
        this.addSoundEvent(266, SoundEvent.COMPOSTER_EMPTY);
        this.addSoundEvent(267, SoundEvent.COMPOSTER_FILL);
        this.addSoundEvent(268, SoundEvent.COMPOSTER_FILL_LAYER);
        this.addSoundEvent(269, SoundEvent.COMPOSTER_READY);
        this.addSoundEvent(270, SoundEvent.BARREL_OPEN);
        this.addSoundEvent(271, SoundEvent.BARREL_CLOSE);
        this.addSoundEvent(272, SoundEvent.RAID_HORN);
        this.addSoundEvent(273, SoundEvent.LOOM_USE);
    }

    @Override
    protected void registerLevelEvents() {
        super.registerLevelEvents();

        this.addLevelEvent(22 | 2000, LevelEventType.PARTICLE_KNOCKBACK_ROAR);
    }
}
