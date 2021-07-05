package org.cloudburstmc.protocol.java.data.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.key.Key;

import java.util.Locale;

@Getter
@RequiredArgsConstructor
public enum AttributeType {
    GENERIC_MAX_HEALTH(20D, 0D, 1024D),
    GENERIC_FOLLOW_RANGE(32D, 0D, 2048D),
    GENERIC_KNOCKBACK_RESISTANCE(0, 0, 1D),
    GENERIC_MOVEMENT_SPEED(0.7D, 0D, 1024D),
    GENERIC_ATTACK_DAMAGE(2D, 0D, 2048D),
    GENERIC_ATTACK_SPEED(4D, 0D, 1024D),
    GENERIC_FLYING_SPEED(0.4D, 0D, 1024D),
    GENERIC_ARMOR(0D, 0D, 30D),
    GENERIC_ARMOR_TOUGHNESS(0D, 0D, 20D),
    GENERIC_ATTACK_KNOCKBACK(0D, 0D, 5D),
    GENERIC_LUCK(0D, -1024D, 1024D),
    HORSE_JUMP_STRENGTH(0.7D, 0D, 2D),
    ZOMBIE_SPAWN_REINFORCEMENTS(0D, 0D, 1D),
    
    // Forge exclusive
    ENTITY_GRAVITY(Key.key("fml:entity_gravity"), 0.08D, -8.0D, 8.0D);

    private final Key key;
    private final double defaultValue;
    private final double min;
    private final double max;

    AttributeType(double defaultValue, double min, double max) {
        this.key = Key.key(this.name().toLowerCase(Locale.ROOT));
        this.defaultValue = defaultValue;
        this.min = min;
        this.max = max;
    }
}
