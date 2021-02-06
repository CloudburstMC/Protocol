package com.nukkitx.protocol.bedrock.data.entity;

public enum EntityDamageCause {
    OVERRIDE,
    CONTACT,
    ENTITY_ATTACK,
    PROJECTILE,
    SUFFOCATION,
    FALL,
    FIRE,
    FIRE_TICK,
    LAVA,
    DROWNING,
    BLOCK_EXPLOSION,
    ENTITY_EXPLOSION,
    VOID,
    SUICIDE,
    MAGIC,
    WITHER,
    STARVE,
    ANVIL,
    THORNS,
    FALLING_BLOCK,
    PISTON,
    FLY_INTO_WALL,
    MAGMA,
    FIREWORKS,
    LIGHTNING,
    CHARGING,
    TEMPERATURE,
    FREEZING;

    private static final EntityDamageCause[] VALUES = EntityDamageCause.values();

    public static EntityDamageCause from(int id) {
        return VALUES[id];
    }
}
