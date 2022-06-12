package org.cloudburstmc.protocol.bedrock.data.entity;

import com.nukkitx.nbt.NbtMap;
import lombok.experimental.UtilityClass;
import org.cloudburstmc.math.vector.Vector3f;
import org.cloudburstmc.math.vector.Vector3i;
import org.cloudburstmc.protocol.bedrock.data.ParticleType;
import org.cloudburstmc.protocol.bedrock.data.defintions.BlockDefinition;

import java.lang.reflect.Field;
import java.util.EnumSet;
import java.util.Set;

@UtilityClass
public class EntityDataTypes {

    public static final EntityDataType<EnumSet<EntityFlag>> FLAGS = new EntityDataType<EnumSet<EntityFlag>>() {
        @Override
        public boolean isInstance(Object value) {
            return value instanceof EnumSet &&
                    (((EnumSet<?>) value).isEmpty() || ((Set<?>) value).iterator().next() instanceof EntityFlag);
        }
    };
    public static final EntityDataType<Integer> STRUCTURAL_INTEGRITY = new EntityDataType<Integer>() {
    };
    public static final EntityDataType<Integer> VARIANT = new EntityDataType<Integer>() {
    };
    public static final EntityDataType<BlockDefinition> BLOCK = new EntityDataType<BlockDefinition>() {
    };
    public static final EntityDataType<Byte> COLOR = new EntityDataType<Byte>() {
    };
    public static final EntityDataType<String> NAME = new EntityDataType<String>() {
    };
    /**
     * Unique ID of the entity that owns or created this entity.
     */
    public static final EntityDataType<Long> OWNER_EID = new EntityDataType<Long>() {
    };
    public static final EntityDataType<Long> TARGET_EID = new EntityDataType<Long>() {
    };
    public static final EntityDataType<Short> AIR_SUPPLY = new EntityDataType<Short>() {
    };
    public static final EntityDataType<Integer> EFFECT_COLOR = new EntityDataType<Integer>() {
    };
    public static final EntityDataType<Byte> EFFECT_AMBIENCE = new EntityDataType<Byte>() {
    };
    public static final EntityDataType<Byte> JUMP_DURATION = new EntityDataType<Byte>() {
    };
    public static final EntityDataType<Integer> HURT_TICKS = new EntityDataType<Integer>() {
    };
    public static final EntityDataType<Integer> HURT_DIRECTION = new EntityDataType<Integer>() {
    };
    public static final EntityDataType<Float> ROW_TIME_LEFT = new EntityDataType<Float>() {
    };
    public static final EntityDataType<Float> ROW_TIME_RIGHT = new EntityDataType<Float>() {
    };
    public static final EntityDataType<Integer> VALUE = new EntityDataType<Integer>() {
    };
    public static final EntityDataType<Byte> WITHER_SKULL_DANGEROUS = new EntityDataType<Byte>() {
    }; // Same ID shares three different types -facepalm-
    public static final EntityDataType<Integer> HORSE_FLAGS = new EntityDataType<Integer>() {
    };
    public static final EntityDataType<NbtMap> DISPLAY_FIREWORK = new EntityDataType<NbtMap>() {
    };
    public static final EntityDataType<BlockDefinition> DISPLAY_BLOCK_STATE = new EntityDataType<BlockDefinition>() {
    };
    public static final EntityDataType<Vector3i> DISPLAY_OFFSET = new EntityDataType<Vector3i>() {
    };
    public static final EntityDataType<Byte> CUSTOM_DISPLAY = new EntityDataType<Byte>() {
    };
    public static final EntityDataType<Byte> HORSE_TYPE = new EntityDataType<Byte>() {
    };
    public static final EntityDataType<Integer> OLD_SWELL = new EntityDataType<Integer>() {
    };
    public static final EntityDataType<Integer> SWELL_DIRECTION = new EntityDataType<Integer>() {
    };
    public static final EntityDataType<Byte> CHARGE_AMOUNT = new EntityDataType<Byte>() {
    };
    public static final EntityDataType<BlockDefinition> CARRY_BLOCK_STATE = new EntityDataType<BlockDefinition>() {
    };
    public static final EntityDataType<Byte> CLIENT_EVENT = new EntityDataType<Byte>() {
    };
    public static final EntityDataType<Boolean> USING_ITEM = new EntityDataType<Boolean>() {
    };
    public static final EntityDataType<Byte> PLAYER_FLAGS = new EntityDataType<Byte>() {
    };
    public static final EntityDataType<Integer> PLAYER_INDEX = new EntityDataType<Integer>() {
    };
    public static final EntityDataType<Vector3i> BED_POSITION = new EntityDataType<Vector3i>() {
    };
    /**
     * Power of fireball (x-axis)
     */
    public static final EntityDataType<Float> FIREBALL_POWER_X = new EntityDataType<Float>() {
    };
    /**
     * Power of fireball (y-axis)
     */
    public static final EntityDataType<Float> FIREBALL_POWER_Y = new EntityDataType<Float>() {
    };
    /**
     * Power of fireball (z-axis)
     */
    public static final EntityDataType<Float> FIREBALL_POWER_Z = new EntityDataType<Float>() {
    };
    /**
     * Potion aux value used for an Arrow's trail. (Equal to the potion ID - 1)
     */
    public static final EntityDataType<Byte> AUX_POWER = new EntityDataType<Byte>() {
    };
    public static final EntityDataType<Float> FISH_X = new EntityDataType<Float>() {
    };
    public static final EntityDataType<Float> FISH_Z = new EntityDataType<Float>() {
    };
    public static final EntityDataType<Float> FISH_ANGLE = new EntityDataType<Float>() {
    };
    public static final EntityDataType<Short> AUX_VALUE_DATA = new EntityDataType<Short>() {
    };
    /**
     * Unique ID for the entity who holds a leash to the current entity.
     */
    public static final EntityDataType<Long> LEASH_HOLDER = new EntityDataType<Long>() {
    };
    /**
     * Set the scale of this entity.
     * 1 is the default size defined by {@code EntityDataType#WIDTH} and {@code EntityDataType#HEIGHT}.
     */
    public static final EntityDataType<Float> SCALE = new EntityDataType<Float>() {
    };
    public static final EntityDataType<Boolean> HAS_NPC = new EntityDataType<Boolean>() {
    };
    public static final EntityDataType<String> NPC_DATA = new EntityDataType<String>() {
    };
    public static final EntityDataType<String> ACTIONS = new EntityDataType<String>() {
    };
    public static final EntityDataType<Short> AIR_SUPPLY_MAX = new EntityDataType<Short>() {
    };
    public static final EntityDataType<Integer> MARK_VARIANT = new EntityDataType<Integer>() {
    };
    public static final EntityDataType<Byte> CONTAINER_TYPE = new EntityDataType<Byte>() {
    };
    public static final EntityDataType<Integer> CONTAINER_SIZE = new EntityDataType<Integer>() {
    };
    public static final EntityDataType<Integer> CONTAINER_STRENGTH_MODIFIER = new EntityDataType<Integer>() {
    };
    /**
     * Target position of Ender Crystal beam.
     */
    public static final EntityDataType<Vector3i> BLOCK_TARGET_POS = new EntityDataType<Vector3i>() {
    };
    public static final EntityDataType<Integer> WITHER_INVULNERABLE_TICKS = new EntityDataType<Integer>() {
    };
    /**
     * Unique entity ID to target for the left head of a Wither.
     */
    public static final EntityDataType<Long> WITHER_TARGET_A = new EntityDataType<Long>() {
    };
    /**
     * Unique entity ID to target for the middle head of a Wither.
     */
    public static final EntityDataType<Long> WITHER_TARGET_B = new EntityDataType<Long>() {
    };
    /**
     * Unique entity ID to target for the right head of a Wither.
     */
    public static final EntityDataType<Long> WITHER_TARGET_C = new EntityDataType<Long>() {
    };
    public static final EntityDataType<Short> WITHER_AERIAL_ATTACK = new EntityDataType<Short>() {
    };
    public static final EntityDataType<Float> WIDTH = new EntityDataType<Float>() {
    };
    public static final EntityDataType<Float> HEIGHT = new EntityDataType<Float>() {
    };
    public static final EntityDataType<Integer> FUSE_TIME = new EntityDataType<Integer>() {
    };
    public static final EntityDataType<Vector3f> SEAT_OFFSET = new EntityDataType<Vector3f>() {
    };
    public static final EntityDataType<Boolean> SEAT_LOCK_RIDER_ROTATION = new EntityDataType<Boolean>() {
    };
    public static final EntityDataType<Float> SEAT_LOCK_RIDER_ROTATION_DEGREES = new EntityDataType<Float>() {
    };
    public static final EntityDataType<Float> SEAT_ROTATION_OFFSET = new EntityDataType<Float>() {
    };
    public static final EntityDataType<Float> SEAT_ROTATION_OFFSET_DEGREES = new EntityDataType<Float>() {
    };
    /**
     * Radius of Area Effect Cloud
     */
    public static final EntityDataType<Float> AREA_EFFECT_CLOUD_RADIUS = new EntityDataType<Float>() {
    };
    public static final EntityDataType<Integer> AREA_EFFECT_CLOUD_WAITING = new EntityDataType<Integer>() {
    };
    public static final EntityDataType<ParticleType> AREA_EFFECT_CLOUD_PARTICLE = new EntityDataType<ParticleType>() {
    };
    public static final EntityDataType<Integer> SHULKER_PEEK_AMOUNT = new EntityDataType<Integer>() {
    };
    public static final EntityDataType<Integer> SHULKER_ATTACH_FACE = new EntityDataType<Integer>() {
    };
    public static final EntityDataType<Boolean> SHULKER_ATTACHED = new EntityDataType<Boolean>() {
    };
    /**
     * Position a Shulker entity is attached from.
     */
    public static final EntityDataType<Vector3i> SHULKER_ATTACH_POS = new EntityDataType<Vector3i>() {
    };
    /**
     * Sets the unique ID of the player that is trading with this entity.
     */
    public static final EntityDataType<Long> TRADE_TARGET_EID = new EntityDataType<Long>() {
    };
    /**
     * Previously used for the villager V1 entity.
     *
     * @deprecated unused AFAIK
     */
    public static final EntityDataType<Integer> CAREER = new EntityDataType<Integer>() {
    };
    public static final EntityDataType<Boolean> COMMAND_BLOCK_ENABLED = new EntityDataType<Boolean>() {
    };
    public static final EntityDataType<String> COMMAND_BLOCK_NAME = new EntityDataType<String>() {
    };
    public static final EntityDataType<String> COMMAND_BLOCK_LAST_OUTPUT = new EntityDataType<String>() {
    };
    public static final EntityDataType<Boolean> COMMAND_BLOCK_TRACK_OUTPUT = new EntityDataType<Boolean>() {
    };
    public static final EntityDataType<Byte> CONTROLLING_RIDER_SEAT_INDEX = new EntityDataType<Byte>() {
    };
    public static final EntityDataType<Integer> STRENGTH = new EntityDataType<Integer>() {
    };
    public static final EntityDataType<Integer> STRENGTH_MAX = new EntityDataType<Integer>() {
    };
    public static final EntityDataType<Integer> EVOKER_SPELL_CASTING_COLOR = new EntityDataType<Integer>() {
    };
    public static final EntityDataType<Integer> DATA_LIFETIME_TICKS = new EntityDataType<Integer>() {
    };
    public static final EntityDataType<Integer> ARMOR_STAND_POSE_INDEX = new EntityDataType<Integer>() {
    };
    public static final EntityDataType<Integer> END_CRYSTAL_TICK_OFFSET = new EntityDataType<Integer>() {
    };
    public static final EntityDataType<Byte> NAMETAG_ALWAYS_SHOW = new EntityDataType<Byte>() {
    };
    public static final EntityDataType<Byte> COLOR_2 = new EntityDataType<Byte>() {
    };
    public static final EntityDataType<String> NAME_AUTHOR = new EntityDataType<String>() {
    };
    public static final EntityDataType<String> SCORE = new EntityDataType<String>() {
    };
    /**
     * Unique entity ID that the balloon string is attached to.
     * Disable by setting value to -1.
     */
    public static final EntityDataType<Long> BALLOON_ANCHOR_EID = new EntityDataType<Long>() {
    };
    public static final EntityDataType<Byte> PUFFED_STATE = new EntityDataType<Byte>() {
    };
    public static final EntityDataType<Integer> BOAT_BUBBLE_TIME = new EntityDataType<Integer>() {
    };
    /**
     * The unique entity ID of the player's Agent. (Education Edition only)
     */
    public static final EntityDataType<Long> AGENT_EID = new EntityDataType<Long>() {
    };
    public static final EntityDataType<Float> SITTING_AMOUNT = new EntityDataType<Float>() {
    };
    public static final EntityDataType<Float> SITTING_AMOUNT_PREVIOUS = new EntityDataType<Float>() {
    };
    public static final EntityDataType<Integer> EATING_COUNTER = new EntityDataType<Integer>() {
    };
    public static final EntityDataType<EnumSet<EntityFlag>> FLAGS_2 = new EntityDataType<EnumSet<EntityFlag>>() {
    };
    public static final EntityDataType<Float> LAYING_AMOUNT = new EntityDataType<Float>() {
    };
    public static final EntityDataType<Float> LAYING_AMOUNT_PREVIOUS = new EntityDataType<Float>() {
    };
    public static final EntityDataType<Integer> AREA_EFFECT_CLOUD_DURATION = new EntityDataType<Integer>() {
    };
    public static final EntityDataType<Integer> AREA_EFFECT_CLOUD_SPAWN_TIME = new EntityDataType<Integer>() {
    };
    public static final EntityDataType<Float> AREA_EFFECT_CLOUD_CHANGE_RATE = new EntityDataType<Float>() {
    };
    public static final EntityDataType<Float> AREA_EFFECT_CLOUD_CHANGE_ON_PICKUP = new EntityDataType<Float>() {
    };
    public static final EntityDataType<Integer> AREA_EFFECT_CLOUD_PICKUP_COUNT = new EntityDataType<Integer>() {
    };
    public static final EntityDataType<String> INTERACT_TEXT = new EntityDataType<String>() {
    };
    public static final EntityDataType<Integer> TRADE_TIER = new EntityDataType<Integer>() {
    };
    public static final EntityDataType<Integer> MAX_TRADE_TIER = new EntityDataType<Integer>() {
    };
    public static final EntityDataType<Integer> TRADE_EXPERIENCE = new EntityDataType<Integer>() {
    };
    public static final EntityDataType<Integer> SKIN_ID = new EntityDataType<Integer>() {
    };
    public static final EntityDataType<Integer> SPAWNING_FRAMES = new EntityDataType<Integer>() {
    };
    public static final EntityDataType<Integer> COMMAND_BLOCK_TICK_DELAY = new EntityDataType<Integer>() {
    };
    public static final EntityDataType<Boolean> COMMAND_BLOCK_EXECUTE_ON_FIRST_TICK = new EntityDataType<Boolean>() {
    };
    public static final EntityDataType<Float> AMBIENT_SOUND_INTERVAL = new EntityDataType<Float>() {
    };
    public static final EntityDataType<Float> AMBIENT_SOUND_INTERVAL_RANGE = new EntityDataType<Float>() {
    };
    public static final EntityDataType<String> AMBIENT_SOUND_EVENT_NAME = new EntityDataType<String>() {
    };
    public static final EntityDataType<Float> FALL_DAMAGE_MULTIPLIER = new EntityDataType<Float>() {
    };
    public static final EntityDataType<String> NAME_RAW_TEXT = new EntityDataType<String>() {
    };
    public static final EntityDataType<Boolean> CAN_RIDE_TARGET = new EntityDataType<Boolean>() {
    };
    public static final EntityDataType<Integer> LOW_TIER_CURED_TRADE_DISCOUNT = new EntityDataType<Integer>() {
    };
    public static final EntityDataType<Integer> HIGH_TIER_CURED_TRADE_DISCOUNT = new EntityDataType<Integer>() {
    };
    public static final EntityDataType<Integer> NEARBY_CURED_TRADE_DISCOUNT = new EntityDataType<Integer>() {
    };
    public static final EntityDataType<Integer> NEARBY_CURED_DISCOUNT_TIME_STAMP = new EntityDataType<Integer>() {
    };

    /**
     * Set custom hitboxes for an entity. This will override the hitbox defined with {@link EntityDataTypes#SCALE},
     * {@link EntityDataTypes#WIDTH} and {@link EntityDataTypes#HEIGHT}, but will not affect the collisions.
     * Setting the hitbox to an empty list will revert to default behaviour.
     * <p>
     * NBT format
     * <pre>
     * {
     *     "Hitboxes": [
     *          {
     *              "MinX": 0f,
     *              "MinY": 0f,
     *              "MinZ": 0f,
     *              "MaxX": 1f,
     *              "MaxY": 1f,
     *              "MaxZ": 1f,
     *              "PivotX": 0f,
     *              "PivotY": 0f,
     *              "PivotZ": 0f,
     *          }
     *     ]
     * }
     * </pre>
     */
    public static final EntityDataType<NbtMap> HITBOX = new EntityDataType<NbtMap>() {
    };
    public static final EntityDataType<Boolean> IS_BUOYANT = new EntityDataType<Boolean>() {
    };
    public static final EntityDataType<String> BASE_RUNTIME_ID = new EntityDataType<String>() {
    };
    /**
     * Custom properties from the <pre>PropertyComponent</pre>.
     */
    public static final EntityDataType<NbtMap> UPDATE_PROPERTIES = new EntityDataType<NbtMap>() {
    };
    public static final EntityDataType<Float> FREEZING_EFFECT_STRENGTH = new EntityDataType<Float>() {
    };
    public static final EntityDataType<String> BUOYANCY_DATA = new EntityDataType<String>() {
    };
    public static final EntityDataType<Integer> GOAT_HORN_COUNT = new EntityDataType<Integer>() {
    };
    /**
     * @since v503
     */
    public static final EntityDataType<?> MOVEMENT_SOUND_DISTANCE_OFFSET = new EntityDataType<Object>() {
    };
    /**
     * @since v503
     */
    public static final EntityDataType<Integer> HEARTBEAT_INTERVAL_TICKS = new EntityDataType<Integer>() {
    };
    /**
     * @since v503
     */
    public static final EntityDataType<Integer> HEARTBEAT_SOUND_EVENT = new EntityDataType<Integer>() {
    };
    /**
     * @since v527
     */
    public static final EntityDataType<Vector3i> PLAYER_LAST_DEATH_POS = new EntityDataType<Vector3i>() {
    };
    /**
     * @since v527
     */
    public static final EntityDataType<Integer> PLAYER_LAST_DEATH_DIMENSION = new EntityDataType<Integer>() {
    };
    /**
     * @since v527
     */
    public static final EntityDataType<Boolean> PLAYER_HAS_DIED = new EntityDataType<Boolean>() {
    };

    public static String getNameIfPossible(EntityDataType<?> dataType) {
        try {
            for (Field field : EntityDataTypes.class.getFields()) {
                if (dataType.equals(field.get(EntityDataTypes.class))) {
                    return field.getName();
                }
            }
        } catch (IllegalAccessException ignored) {
        }
        return "unknown";
    }
}
