package org.cloudburstmc.protocol.bedrock.data;

public enum AdventureSetting {
    WORLD_IMMUTABLE,
    NO_PVM,
    NO_MVP,
    SHOW_NAME_TAGS,
    AUTO_JUMP,
    MAY_FLY,
    NO_CLIP,
    WORLD_BUILDER,
    FLYING,
    MUTED,

    // Permission flags
    MINE,
    DOORS_AND_SWITCHES,
    OPEN_CONTAINERS,
    ATTACK_PLAYERS,
    ATTACK_MOBS,
    OPERATOR,
    TELEPORT,
    BUILD,
    DEFAULT_LEVEL_PERMISSIONS
}
