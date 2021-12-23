package com.nukkitx.protocol.bedrock.v448;

import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockSession;
import com.nukkitx.protocol.bedrock.data.LevelEventType;
import com.nukkitx.protocol.bedrock.data.SoundEvent;
import com.nukkitx.protocol.bedrock.data.entity.EntityFlag;
import com.nukkitx.protocol.bedrock.data.inventory.stackrequestactions.AutoCraftRecipeStackRequestActionData;
import com.nukkitx.protocol.bedrock.data.inventory.stackrequestactions.StackRequestActionData;
import com.nukkitx.protocol.bedrock.data.inventory.stackrequestactions.StackRequestActionType;
import com.nukkitx.protocol.bedrock.v440.BedrockPacketHelper_v440;
import io.netty.buffer.ByteBuf;

public class BedrockPacketHelper_v448 extends BedrockPacketHelper_v440 {
    public static final BedrockPacketHelper_v448 INSTANCE = new BedrockPacketHelper_v448();

    @Override
    protected void registerEntityFlags() {
        super.registerEntityFlags();

        this.addEntityFlag(98, EntityFlag.IN_ASCENDABLE_BLOCK);
        this.addEntityFlag(99, EntityFlag.OVER_DESCENDABLE_BLOCK);
    }

    @Override
    protected void registerSoundEvents() {
        super.registerSoundEvents();

        this.addSoundEvent(360, SoundEvent.CAKE_ADD_CANDLE);
        this.addSoundEvent(360, SoundEvent.EXTINGUISH_CANDLE);
        this.addSoundEvent(360, SoundEvent.AMBIENT_CANDLE);
        this.addSoundEvent(363, SoundEvent.UNDEFINED);
    }

    @Override
    protected void registerLevelEvents() {
        super.registerLevelEvents();

        int legacy = 0x4000;
        this.addLevelEvent(9 + legacy, LevelEventType.PARTICLE_CANDLE_FLAME);
        this.addLevelEvent(10 + legacy, LevelEventType.PARTICLE_LAVA);
        this.addLevelEvent(11 + legacy, LevelEventType.PARTICLE_LARGE_SMOKE);
        this.addLevelEvent(12 + legacy, LevelEventType.PARTICLE_REDSTONE);
        this.addLevelEvent(13 + legacy, LevelEventType.PARTICLE_RISING_RED_DUST);
        this.addLevelEvent(14 + legacy, LevelEventType.PARTICLE_ITEM_BREAK);
        this.addLevelEvent(15 + legacy, LevelEventType.PARTICLE_SNOWBALL_POOF);
        this.addLevelEvent(16 + legacy, LevelEventType.PARTICLE_HUGE_EXPLODE);
        this.addLevelEvent(17 + legacy, LevelEventType.PARTICLE_HUGE_EXPLODE_SEED);
        this.addLevelEvent(18 + legacy, LevelEventType.PARTICLE_MOB_FLAME);
        this.addLevelEvent(19 + legacy, LevelEventType.PARTICLE_HEART);
        this.addLevelEvent(20 + legacy, LevelEventType.PARTICLE_TERRAIN);
        this.addLevelEvent(21 + legacy, LevelEventType.PARTICLE_TOWN_AURA);
        this.addLevelEvent(22 + legacy, LevelEventType.PARTICLE_PORTAL);
        this.addLevelEvent(23 + legacy, LevelEventType.PARTICLE_MOB_PORTAL);
        this.addLevelEvent(24 + legacy, LevelEventType.PARTICLE_SPLASH);
        this.addLevelEvent(25 + legacy, LevelEventType.PARTICLE_SPLASH_MANUAL);
        this.addLevelEvent(26 + legacy, LevelEventType.PARTICLE_WATER_WAKE);
        this.addLevelEvent(27 + legacy, LevelEventType.PARTICLE_DRIP_WATER);
        this.addLevelEvent(28 + legacy, LevelEventType.PARTICLE_DRIP_LAVA);
        this.addLevelEvent(29 + legacy, LevelEventType.PARTICLE_DRIP_HONEY);
        this.addLevelEvent(30 + legacy, LevelEventType.PARTICLE_STALACTITE_DRIP_WATER);
        this.addLevelEvent(31 + legacy, LevelEventType.PARTICLE_STALACTITE_DRIP_LAVA);
        this.addLevelEvent(32 + legacy, LevelEventType.PARTICLE_FALLING_DUST);
        this.addLevelEvent(33 + legacy, LevelEventType.PARTICLE_MOB_SPELL);
        this.addLevelEvent(34 + legacy, LevelEventType.PARTICLE_MOB_SPELL_AMBIENT);
        this.addLevelEvent(35 + legacy, LevelEventType.PARTICLE_MOB_SPELL_INSTANTANEOUS);
        this.addLevelEvent(36 + legacy, LevelEventType.PARTICLE_INK);
        this.addLevelEvent(37 + legacy, LevelEventType.PARTICLE_SLIME);
        this.addLevelEvent(38 + legacy, LevelEventType.PARTICLE_RAIN_SPLASH);
        this.addLevelEvent(39 + legacy, LevelEventType.PARTICLE_VILLAGER_ANGRY);
        this.addLevelEvent(40 + legacy, LevelEventType.PARTICLE_VILLAGER_HAPPY);
        this.addLevelEvent(41 + legacy, LevelEventType.PARTICLE_ENCHANTMENT_TABLE);
        this.addLevelEvent(42 + legacy, LevelEventType.PARTICLE_TRACKING_EMITTER);
        this.addLevelEvent(43 + legacy, LevelEventType.PARTICLE_NOTE);
        this.addLevelEvent(44 + legacy, LevelEventType.PARTICLE_WITCH_SPELL);
        this.addLevelEvent(45 + legacy, LevelEventType.PARTICLE_CARROT);
        this.addLevelEvent(46 + legacy, LevelEventType.PARTICLE_MOB_APPEARANCE);
        this.addLevelEvent(47 + legacy, LevelEventType.PARTICLE_END_ROD);
        this.addLevelEvent(48 + legacy, LevelEventType.PARTICLE_DRAGONS_BREATH);
        this.addLevelEvent(49 + legacy, LevelEventType.PARTICLE_SPIT);
        this.addLevelEvent(50 + legacy, LevelEventType.PARTICLE_TOTEM);
        this.addLevelEvent(51 + legacy, LevelEventType.PARTICLE_FOOD);
        this.addLevelEvent(52 + legacy, LevelEventType.PARTICLE_FIREWORKS_STARTER);
        this.addLevelEvent(53 + legacy, LevelEventType.PARTICLE_FIREWORKS_SPARK);
        this.addLevelEvent(54 + legacy, LevelEventType.PARTICLE_FIREWORKS_OVERLAY);
        this.addLevelEvent(55 + legacy, LevelEventType.PARTICLE_BALLOON_GAS);
        this.addLevelEvent(56 + legacy, LevelEventType.PARTICLE_COLORED_FLAME);
        this.addLevelEvent(57 + legacy, LevelEventType.PARTICLE_SPARKLER);
        this.addLevelEvent(58 + legacy, LevelEventType.PARTICLE_CONDUIT);
        this.addLevelEvent(59 + legacy, LevelEventType.PARTICLE_BUBBLE_COLUMN_UP);
        this.addLevelEvent(60 + legacy, LevelEventType.PARTICLE_BUBBLE_COLUMN_DOWN);
        this.addLevelEvent(61 + legacy, LevelEventType.PARTICLE_SNEEZE);
        this.addLevelEvent(62 + legacy, LevelEventType.PARTICLE_SHULKER_BULLET);
        this.addLevelEvent(63 + legacy, LevelEventType.PARTICLE_BLEACH);
        this.addLevelEvent(64 + legacy, LevelEventType.PARTICLE_DRAGON_DESTROY_BLOCK);
        this.addLevelEvent(65 + legacy, LevelEventType.PARTICLE_MYCELIUM_DUST);
        this.addLevelEvent(66 + legacy, LevelEventType.PARTICLE_FALLING_RED_DUST);
        this.addLevelEvent(67 + legacy, LevelEventType.PARTICLE_CAMPFIRE_SMOKE);
        this.addLevelEvent(68 + legacy, LevelEventType.PARTICLE_TALL_CAMPFIRE_SMOKE);
        this.addLevelEvent(69 + legacy, LevelEventType.PARTICLE_RISING_DRAGONS_BREATH);
        this.addLevelEvent(70 + legacy, LevelEventType.PARTICLE_DRAGONS_BREATH);
        this.addLevelEvent(71 + legacy, LevelEventType.PARTICLE_BLUE_FLAME);
        this.addLevelEvent(72 + legacy, LevelEventType.PARTICLE_SOUL);
        this.addLevelEvent(73 + legacy, LevelEventType.PARTICLE_OBSIDIAN_TEAR);
        this.addLevelEvent(74 + legacy, LevelEventType.PARTICLE_PORTAL_REVERSE);
        this.addLevelEvent(75 + legacy, LevelEventType.PARTICLE_SNOWFLAKE);
        this.addLevelEvent(76 + legacy, LevelEventType.PARTICLE_VIBRATION_SIGNAL);
        this.addLevelEvent(77 + legacy, LevelEventType.PARTICLE_SCULK_SENSOR_REDSTONE);
        this.addLevelEvent(78 + legacy, LevelEventType.PARTICLE_SPORE_BLOSSOM_SHOWER);
        this.addLevelEvent(79 + legacy, LevelEventType.PARTICLE_SPORE_BLOSSOM_AMBIENT);
        this.addLevelEvent(80 + legacy, LevelEventType.PARTICLE_WAX);
        this.addLevelEvent(81 + legacy, LevelEventType.PARTICLE_ELECTRIC_SPARK);
    }

    @Override
    protected StackRequestActionData readRequestActionData(ByteBuf byteBuf, StackRequestActionType type, BedrockSession session) {
        if (type == StackRequestActionType.CRAFT_RECIPE_AUTO) {
           return new AutoCraftRecipeStackRequestActionData(
                   VarInts.readUnsignedInt(byteBuf), byteBuf.readByte()
           );
        } else {
            return super.readRequestActionData(byteBuf, type, session);
        }
    }

    @Override
    protected void writeRequestActionData(ByteBuf byteBuf, StackRequestActionData action, BedrockSession session) {
        super.writeRequestActionData(byteBuf, action, session);
        if (action.getType() == StackRequestActionType.CRAFT_RECIPE_AUTO) {
            byteBuf.writeByte(((AutoCraftRecipeStackRequestActionData) action).getTimesCrafted());
        }
    }
}
