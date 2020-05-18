package com.nukkitx.protocol.bedrock.data;

public enum EntityEventType {
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
    /**
     * Join or leave caravan
     * Data: Caravan size
     */
    CARAVAN,
    CONSUME_TOTEM,
    /*
     * Microjang hack to check if achievement is successful sent every 4 seconds on a vanilla server.
     */
    CHECK_TREASURE_HUNTER_ACHIEVEMENT,
    /**
     * Entity spawn
     * Data: {@code MobSpawnMethod | (entityId << 16)}
     */
    ENTITY_SPAWN,
    DRAGON_FLAMING,
    MERGE_ITEMS,
    BALLOON_POP,
    FIND_TREASURE_BRIBE,
    CROSSBOW_DRAW,
}
