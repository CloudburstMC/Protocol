package org.cloudburstmc.protocol.bedrock.codec.v389;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v388.BedrockCodecHelper_v388;
import org.cloudburstmc.protocol.bedrock.data.LevelEvent;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BedrockCodecHelper_v389 extends BedrockCodecHelper_v388 {

    public static final BedrockCodecHelper INSTANCE = new BedrockCodecHelper_v389();

    @Override
    protected void registerLevelEvents() {
        super.registerLevelEvents();

        this.addLevelEvent(23 + 2000, LevelEvent.PARTICLE_TELEPORT_TRAIL);

        this.addLevelEvent(9810, LevelEvent.JUMP_PREVENTED);

        int legacy = 0x4000;
        this.addLevelEvent(28 + legacy, LevelEvent.PARTICLE_DRIP_HONEY);
        this.addLevelEvent(29 + legacy, LevelEvent.PARTICLE_FALLING_DUST);
        this.addLevelEvent(30 + legacy, LevelEvent.PARTICLE_MOB_SPELL);
        this.addLevelEvent(31 + legacy, LevelEvent.PARTICLE_MOB_SPELL_AMBIENT);
        this.addLevelEvent(32 + legacy, LevelEvent.PARTICLE_MOB_SPELL_INSTANTANEOUS);
        this.addLevelEvent(33 + legacy, LevelEvent.PARTICLE_INK);
        this.addLevelEvent(34 + legacy, LevelEvent.PARTICLE_SLIME);
        this.addLevelEvent(35 + legacy, LevelEvent.PARTICLE_RAIN_SPLASH);
        this.addLevelEvent(36 + legacy, LevelEvent.PARTICLE_VILLAGER_ANGRY);
        this.addLevelEvent(37 + legacy, LevelEvent.PARTICLE_VILLAGER_HAPPY);
        this.addLevelEvent(38 + legacy, LevelEvent.PARTICLE_ENCHANTMENT_TABLE);
        this.addLevelEvent(39 + legacy, LevelEvent.PARTICLE_TRACKING_EMITTER);
        this.addLevelEvent(40 + legacy, LevelEvent.PARTICLE_NOTE);
        this.addLevelEvent(41 + legacy, LevelEvent.PARTICLE_WITCH_SPELL);
        this.addLevelEvent(42 + legacy, LevelEvent.PARTICLE_CARROT);
        this.addLevelEvent(43 + legacy, LevelEvent.PARTICLE_MOB_APPEARANCE);
        this.addLevelEvent(44 + legacy, LevelEvent.PARTICLE_END_ROD);
        this.addLevelEvent(45 + legacy, LevelEvent.PARTICLE_DRAGONS_BREATH);
        this.addLevelEvent(46 + legacy, LevelEvent.PARTICLE_SPIT);
        this.addLevelEvent(47 + legacy, LevelEvent.PARTICLE_TOTEM);
        this.addLevelEvent(48 + legacy, LevelEvent.PARTICLE_FOOD);
        this.addLevelEvent(49 + legacy, LevelEvent.PARTICLE_FIREWORKS_STARTER);
        this.addLevelEvent(50 + legacy, LevelEvent.PARTICLE_FIREWORKS_SPARK);
        this.addLevelEvent(51 + legacy, LevelEvent.PARTICLE_FIREWORKS_OVERLAY);
        this.addLevelEvent(52 + legacy, LevelEvent.PARTICLE_BALLOON_GAS);
        this.addLevelEvent(53 + legacy, LevelEvent.PARTICLE_COLORED_FLAME);
        this.addLevelEvent(54 + legacy, LevelEvent.PARTICLE_SPARKLER);
        this.addLevelEvent(55 + legacy, LevelEvent.PARTICLE_CONDUIT);
        this.addLevelEvent(56 + legacy, LevelEvent.PARTICLE_BUBBLE_COLUMN_UP);
        this.addLevelEvent(57 + legacy, LevelEvent.PARTICLE_BUBBLE_COLUMN_DOWN);
        this.addLevelEvent(58 + legacy, LevelEvent.PARTICLE_SNEEZE);
        this.addLevelEvent(59 + legacy, LevelEvent.PARTICLE_SHULKER_BULLET);
        this.addLevelEvent(60 + legacy, LevelEvent.PARTICLE_BLEACH);
        this.addLevelEvent(61 + legacy, LevelEvent.PARTICLE_DRAGON_DESTROY_BLOCK);
        this.addLevelEvent(62 + legacy, LevelEvent.PARTICLE_MYCELIUM_DUST);
        this.addLevelEvent(63 + legacy, LevelEvent.PARTICLE_FALLING_RED_DUST);
        this.addLevelEvent(64 + legacy, LevelEvent.PARTICLE_CAMPFIRE_SMOKE);
        this.addLevelEvent(65 + legacy, LevelEvent.PARTICLE_TALL_CAMPFIRE_SMOKE);
        this.addLevelEvent(66 + legacy, LevelEvent.PARTICLE_RISING_DRAGONS_BREATH);
        this.addLevelEvent(67 + legacy, LevelEvent.PARTICLE_DRAGONS_BREATH);
    }
}
