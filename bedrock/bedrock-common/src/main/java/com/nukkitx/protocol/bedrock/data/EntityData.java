package com.nukkitx.protocol.bedrock.data;

import com.nukkitx.math.vector.Vector3f;
import com.nukkitx.math.vector.Vector3i;
import com.nukkitx.nbt.tag.CompoundTag;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.annotation.Nonnull;

@Getter
@RequiredArgsConstructor
public enum EntityData {
    FLAGS(Type.FLAGS, true),
    HEALTH(Type.INT),
    VARIANT(Type.INT),
    COLOR(Type.BYTE),
    NAMETAG(Type.STRING),
    OWNER_EID(Type.LONG),
    TARGET_EID(Type.LONG),
    AIR(Type.SHORT),
    POTION_COLOR(Type.INT),
    POTION_AMBIENT(Type.BYTE),
    HURT_TIME(Type.INT),
    JUMP_DURATION(Type.BYTE),
    HURT_DIRECTION(Type.INT),
    PADDLE_TIME_LEFT(Type.FLOAT),
    PADDLE_TIME_RIGHT(Type.FLOAT),
    EXPERIENCE_VALUE(Type.INT),
    DISPLAY_ITEM(Type.INT),
    DISPLAY_OFFSET(null), // Can be long or int
    HAS_DISPLAY(Type.BYTE),
    CHARGED(Type.BYTE),
    ENDERMAN_HELD_ITEM_ID(Type.INT),
    ENTITY_AGE(Type.BYTE),
    //WITCH_UNKNOWN(Type.BYTE), // TODO: Initialized to 0 but never changed.
    CAN_START_SLEEP(Type.BYTE),
    PLAYER_INDEX(Type.INT),
    BED_RESPAWN_POS(Type.VECTOR3I),
    FIREBALL_POWER_X(Type.FLOAT),
    FIREBALL_POWER_Y(Type.FLOAT),
    FIREBALL_POWER_Z(Type.FLOAT),
    POTION_AUX_VALUE(Type.SHORT),
    LEAD_HOLDER_EID(Type.LONG),
    SCALE(Type.FLOAT),
    HAS_NPC_COMPONENT(Type.BYTE),
    SKIN_ID(Type.STRING),
    NPC_SKIN_ID(Type.STRING),
    URL_TAG(Type.STRING),
    MAX_AIR(Type.SHORT),
    MARK_VARIANT(Type.INT),
    CONTAINER_TYPE(Type.BYTE),
    CONTAINER_BASE_SIZE(Type.INT),
    CONTAINER_EXTRA_SLOTS_PER_STRENGTH(Type.INT),
    BLOCK_TARGET(Type.VECTOR3I),
    WITHER_INVULNERABLE_TICKS(Type.INT),
    WITHER_TARGET_1(Type.LONG),
    WITHER_TARGET_2(Type.LONG),
    WITHER_TARGET_3(Type.LONG),
    WITHER_AERIAL_ATTACK(Type.SHORT),
    BOUNDING_BOX_WIDTH(Type.FLOAT),
    BOUNDING_BOX_HEIGHT(Type.FLOAT),
    FUSE_LENGTH(Type.INT),
    RIDER_SEAT_POSITION(Type.VECTOR3F),
    RIDER_ROTATION_LOCKED(Type.BYTE),
    RIDER_MAX_ROTATION(Type.FLOAT),
    RIDER_MIN_ROTATION(Type.FLOAT),
    AREA_EFFECT_CLOUD_RADIUS(Type.FLOAT),
    AREA_EFFECT_CLOUD_WAITING(Type.INT),
    AREA_EFFECT_CLOUD_PARTICLE_ID(Type.INT),
    SHULKER_PEAK_HEIGHT(Type.INT),
    SHULKER_ATTACH_FACE(Type.BYTE),
    SHULKER_ATTACH_POS(Type.VECTOR3I),
    TRADING_PLAYER_EID(Type.LONG),
    COMMAND_BLOCK_ENABLED(Type.BYTE),
    COMMAND_BLOCK_COMMAND(Type.STRING),
    COMMAND_BLOCK_LAST_OUTPUT(Type.STRING),
    COMMAND_BLOCK_TRACK_OUTPUT(Type.BYTE),
    CONTROLLING_RIDER_SEAT_NUMBER(Type.BYTE),
    STRENGTH(Type.INT),
    MAX_STRENGTH(Type.INT),
    EVOKER_SPELL_COLOR(Type.INT),
    LIMITED_LIFE(Type.INT),
    ARMOR_STAND_POSE_INDEX(Type.INT),
    ENDER_CRYSTAL_TIME_OFFSET(Type.INT),
    ALWAYS_SHOW_NAMETAG(Type.BYTE),
    COLOR_2(Type.BYTE),
    SCORE_TAG(Type.STRING),
    BALLOON_ATTACHED_ENTITY(Type.LONG),
    PUFFERFISH_SIZE(Type.BYTE),
    BOAT_BUBBLE_TIME(Type.INT),
    AGENT_ID(Type.LONG),


    EAT_COUNTER(Type.INT),
    FLAGS_2(Type.FLAGS, true),


    AREA_EFFECT_CLOUD_DURATION(Type.INT),
    AREA_EFFECT_CLOUD_SPAWN_TIME(Type.INT),
    AREA_EFFECT_CLOUD_RADIUS_PER_TICK(Type.FLOAT),
    AREA_EFFECT_CLOUD_RADIUS_CHANGE_ON_PICKUP(Type.FLOAT),
    AREA_EFFECT_CLOUD_PICKUP_COUNT(Type.INT),
    INTERACTIVE_TAG(Type.STRING),
    TRADE_TIER(Type.INT),
    MAX_TRADE_TIER(Type.INT),
    TRADE_XP(Type.INT);

    private final Type type;
    private final boolean flags;

    EntityData(Type type) {
        this.type = type;
        this.flags = false;
    }

    @RequiredArgsConstructor
    public enum Type {
        FLAGS,
        BYTE,
        SHORT,
        INT,
        FLOAT,
        STRING,
        NBT,
        VECTOR3I,
        LONG,
        VECTOR3F;

        @Nonnull
        public static Type from(Object o) {
            if (o instanceof EntityFlags) {
                return EntityData.Type.FLAGS;
            } else if (o instanceof Byte) {
                return EntityData.Type.BYTE;
            } else if (o instanceof Short) {
                return EntityData.Type.SHORT;
            } else if (o instanceof Integer) {
                return EntityData.Type.INT;
            } else if (o instanceof Float) {
                return EntityData.Type.FLOAT;
            } else if (o instanceof String) {
                return EntityData.Type.STRING;
            } else if (o instanceof ItemData || o instanceof CompoundTag) {
                return EntityData.Type.NBT;
            } else if (o instanceof Vector3i) {
                return EntityData.Type.VECTOR3I;
            } else if (o instanceof Long) {
                return EntityData.Type.LONG;
            } else if (o instanceof Vector3f) {
                return EntityData.Type.VECTOR3F;
            }
            throw new IllegalArgumentException("Invalid type");
        }
    }
}
