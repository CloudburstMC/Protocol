package com.nukkitx.protocol.bedrock.data.entity;

public enum EntityEventType {
    NONE,
    JUMP,
    HURT,
    DEATH,
    ATTACK_START,
    ATTACK_STOP,
    TAME_FAILED,
    TAME_SUCCEEDED,
    SHAKE_WETNESS,
    USE_ITEM,
    EAT_GRASS,
    FISH_HOOK_BUBBLE,
    FISH_HOOK_POSITION,
    FISH_HOOK_TIME,
    FISH_HOOK_TEASE,
    SQUID_FLEEING,
    ZOMBIE_VILLAGER_CURE,
    PLAY_AMBIENT,
    RESPAWN,
    GOLEM_FLOWER_OFFER,
    GOLEM_FLOWER_WITHDRAW,
    VILLAGER_ANGRY,
    LOVE_PARTICLES,
    VILLAGER_HAPPY,
    WITCH_HAT_MAGIC,
    FIREWORK_EXPLODE,
    IN_LOVE_HEARTS,
    SILVERFISH_MERGE_WITH_STONE,
    GUARDIAN_ATTACK_ANIMATION,
    WITCH_DRINK_POTION,
    WITCH_THROW_POTION,
    PRIME_TNT_MINECART,
    PRIME_CREEPER,
    AIR_SUPPLY,
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
    UPDATE_ITEM_STACK_SIZE,
    START_SWIMMING,
    BALLOON_POP,
    TREASURE_HUNT,
    SUMMON_AGENT,
    FINISHED_CHARGING_CROSSBOW
}
