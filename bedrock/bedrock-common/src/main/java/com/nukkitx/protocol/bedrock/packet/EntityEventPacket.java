package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class EntityEventPacket extends BedrockPacket {
    private long runtimeEntityId;
    private Event event;
    private int data;

    @Override
    public final boolean handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public enum Event {
        HURT_ANIMATION,
        DEATH_ANIMATION,
        ARM_SWING,
        TAME_FAIL,
        TAME_SUCCESS,
        SHAKE_WET,
        USE_ITEM,
        EAT_BLOCK_ANIMATION,
        FISH_HOOK_BUBBLE,
        FISH_HOOK_POSITION,
        FISH_HOOK_HOOK,
        FISH_HOOK_LURED,
        SQUID_INK_CLOUD,
        ZOMBIE_VILLAGER_CURE,
        RESPAWN,
        IRON_GOLEM_OFFER_FLOWER,
        IRON_GOLEM_WITHDRAW_FLOWER,
        VILLAGER_HURT,
        LOVE_PARTICLES,
        VILLAGER_STOP_TRADING,
        WITCH_SPELL_PARTICLES,
        FIREWORK_PARTICLES,
        SILVERFISH_MERGE_WITH_STONE,
        GUARDIAN_ATTACK_ANIMATION,
        WITCH_DRINK_POTION,
        WITCH_THROW_POTION,
        MINECART_TNT_PRIME_FUSE,
        PLAYER_ADD_XP_LEVELS,
        ELDER_GUARDIAN_CURSE,
        AGENT_ARM_SWING,
        ENDER_DRAGON_DEATH,
        DUST_PARTICLES,
        ARROW_SHAKE,
        EATING_ITEM,
        BABY_ANIMAL_FEED,
        DEATH_SMOKE_CLOUD,
        COMPLETE_TRADE,
        REMOVE_LEASH,
        /*
         * Join or leave caravan
         * Data: Caravan size
         */
        CARAVAN,
        CONSUME_TOTEM,
        /*
         * Microjang hack to check if achievement is successful sent every 4 seconds.
         */
        CHECK_TREASURE_HUNTER_ACHIEVEMENT,
        /*
         * Entity spawn
         * Data: MobSpawnMethod | (entityId << 16)
         */
        ENTITY_SPAWN,
        DRAGON_FLAMING,
        MERGE_ITEMS,
        BALLOON_POP,
        FIND_TREASURE_BRIBE,
    }
}
