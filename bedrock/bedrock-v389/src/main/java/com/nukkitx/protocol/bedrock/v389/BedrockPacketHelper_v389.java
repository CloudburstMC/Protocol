package com.nukkitx.protocol.bedrock.v389;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.data.LevelEventType;
import com.nukkitx.protocol.bedrock.v388.BedrockPacketHelper_v388;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BedrockPacketHelper_v389 extends BedrockPacketHelper_v388 {

    public static final BedrockPacketHelper INSTANCE = new BedrockPacketHelper_v389();

    @Override
    protected void registerLevelEvents() {
        super.registerLevelEvents();

        this.addLevelEvent(23 + 2000, LevelEventType.PARTICLE_TELEPORT_TRAIL);

        this.addLevelEvent(9810, LevelEventType.JUMP_PREVENTED);

        int legacy = 0x4000;
        this.addLevelEvent(28 + legacy, LevelEventType.PARTICLE_DRIP_HONEY);
        this.addLevelEvent(29 + legacy, LevelEventType.PARTICLE_FALLING_DUST);
        this.addLevelEvent(30 + legacy, LevelEventType.PARTICLE_MOB_SPELL);
        this.addLevelEvent(31 + legacy, LevelEventType.PARTICLE_MOB_SPELL_AMBIENT);
        this.addLevelEvent(32 + legacy, LevelEventType.PARTICLE_MOB_SPELL_INSTANTANEOUS);
        this.addLevelEvent(33 + legacy, LevelEventType.PARTICLE_INK);
        this.addLevelEvent(34 + legacy, LevelEventType.PARTICLE_SLIME);
        this.addLevelEvent(35 + legacy, LevelEventType.PARTICLE_RAIN_SPLASH);
        this.addLevelEvent(36 + legacy, LevelEventType.PARTICLE_VILLAGER_ANGRY);
        this.addLevelEvent(37 + legacy, LevelEventType.PARTICLE_VILLAGER_HAPPY);
        this.addLevelEvent(38 + legacy, LevelEventType.PARTICLE_ENCHANTMENT_TABLE);
        this.addLevelEvent(39 + legacy, LevelEventType.PARTICLE_TRACKING_EMITTER);
        this.addLevelEvent(40 + legacy, LevelEventType.PARTICLE_NOTE);
        this.addLevelEvent(41 + legacy, LevelEventType.PARTICLE_WITCH_SPELL);
        this.addLevelEvent(42 + legacy, LevelEventType.PARTICLE_CARROT);
        this.addLevelEvent(43 + legacy, LevelEventType.PARTICLE_MOB_APPEARANCE);
        this.addLevelEvent(44 + legacy, LevelEventType.PARTICLE_END_ROD);
        this.addLevelEvent(45 + legacy, LevelEventType.PARTICLE_DRAGONS_BREATH);
        this.addLevelEvent(46 + legacy, LevelEventType.PARTICLE_SPIT);
        this.addLevelEvent(47 + legacy, LevelEventType.PARTICLE_TOTEM);
        this.addLevelEvent(48 + legacy, LevelEventType.PARTICLE_FOOD);
        this.addLevelEvent(49 + legacy, LevelEventType.PARTICLE_FIREWORKS_STARTER);
        this.addLevelEvent(50 + legacy, LevelEventType.PARTICLE_FIREWORKS_SPARK);
        this.addLevelEvent(51 + legacy, LevelEventType.PARTICLE_FIREWORKS_OVERLAY);
        this.addLevelEvent(52 + legacy, LevelEventType.PARTICLE_BALLOON_GAS);
        this.addLevelEvent(53 + legacy, LevelEventType.PARTICLE_COLORED_FLAME);
        this.addLevelEvent(54 + legacy, LevelEventType.PARTICLE_SPARKLER);
        this.addLevelEvent(55 + legacy, LevelEventType.PARTICLE_CONDUIT);
        this.addLevelEvent(56 + legacy, LevelEventType.PARTICLE_BUBBLE_COLUMN_UP);
        this.addLevelEvent(57 + legacy, LevelEventType.PARTICLE_BUBBLE_COLUMN_DOWN);
        this.addLevelEvent(58 + legacy, LevelEventType.PARTICLE_SNEEZE);
        this.addLevelEvent(59 + legacy, LevelEventType.PARTICLE_SHULKER_BULLET);
        this.addLevelEvent(60 + legacy, LevelEventType.PARTICLE_BLEACH);
        this.addLevelEvent(61 + legacy, LevelEventType.PARTICLE_DRAGON_DESTROY_BLOCK);
        this.addLevelEvent(62 + legacy, LevelEventType.PARTICLE_MYCELIUM_DUST);
        this.addLevelEvent(63 + legacy, LevelEventType.PARTICLE_FALLING_RED_DUST);
        this.addLevelEvent(64 + legacy, LevelEventType.PARTICLE_CAMPFIRE_SMOKE);
        this.addLevelEvent(65 + legacy, LevelEventType.PARTICLE_TALL_CAMPFIRE_SMOKE);
        this.addLevelEvent(66 + legacy, LevelEventType.PARTICLE_RISING_DRAGONS_BREATH);
        this.addLevelEvent(67 + legacy, LevelEventType.PARTICLE_DRAGONS_BREATH);
    }
}
