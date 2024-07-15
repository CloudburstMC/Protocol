package org.cloudburstmc.protocol.bedrock.data.entity;

import lombok.experimental.UtilityClass;
import org.cloudburstmc.math.vector.Vector3f;
import org.cloudburstmc.math.vector.Vector3i;
import org.cloudburstmc.nbt.NbtMap;
import org.cloudburstmc.protocol.bedrock.data.ParticleType;
import org.cloudburstmc.protocol.bedrock.data.definitions.BlockDefinition;

import java.util.EnumSet;
import java.util.Set;

@UtilityClass
public class EntityDataTypes {

    public static final EntityDataType<EnumSet<EntityFlag>> FLAGS = new EntityDataType<EnumSet<EntityFlag>>(EnumSet.class, "FLAGS") {
        @Override
        public boolean isInstance(Object value) {
            return value instanceof EnumSet &&
                    (((EnumSet<?>) value).isEmpty() || ((Set<?>) value).iterator().next() instanceof EntityFlag);
        }
    };
    public static final EntityDataType<Integer> STRUCTURAL_INTEGRITY = new EntityDataType<>(Integer.class, "STRUCTURAL_INTEGRITY");
    public static final EntityDataType<Integer> VARIANT = new EntityDataType<>(Integer.class, "VARIANT");
    public static final EntityDataType<BlockDefinition> BLOCK = new EntityDataType<>(BlockDefinition.class, "BLOCK");
    public static final EntityDataType<Byte> COLOR = new EntityDataType<>(Byte.class, "COLOR");
    public static final EntityDataType<String> NAME = new EntityDataType<>(String.class, "NAME");
    /**
     * Unique ID of the entity that owns or created this entity.
     */
    public static final EntityDataType<Long> OWNER_EID = new EntityDataType<>(Long.class, "OWNER_EID");
    public static final EntityDataType<Long> TARGET_EID = new EntityDataType<>(Long.class, "TARGET_EID");
    public static final EntityDataType<Short> AIR_SUPPLY = new EntityDataType<>(Short.class, "AIR_SUPPLY");
    public static final EntityDataType<Integer> EFFECT_COLOR = new EntityDataType<>(Integer.class, "EFFECT_COLOR");
    /**
     * @deprecated since v685
     */
    @Deprecated
    public static final EntityDataType<Byte> EFFECT_AMBIENCE = new EntityDataType<>(Byte.class, "EFFECT_AMBIENCE");
    public static final EntityDataType<Byte> JUMP_DURATION = new EntityDataType<>(Byte.class, "JUMP_DURATION");
    public static final EntityDataType<Integer> HURT_TICKS = new EntityDataType<>(Integer.class, "HURT_TICKS");
    public static final EntityDataType<Integer> HURT_DIRECTION = new EntityDataType<>(Integer.class, "HURT_DIRECTION");
    public static final EntityDataType<Float> ROW_TIME_LEFT = new EntityDataType<>(Float.class, "ROW_TIME_LEFT");
    public static final EntityDataType<Float> ROW_TIME_RIGHT = new EntityDataType<>(Float.class, "ROW_TIME_RIGHT");
    public static final EntityDataType<Integer> VALUE = new EntityDataType<>(Integer.class, "VALUE");
    public static final EntityDataType<Byte> WITHER_SKULL_DANGEROUS = new EntityDataType<>(Byte.class, "WITHER_SKULL_DANGEROUS");
    // Same ID shares three different types -facepalm-
    public static final EntityDataType<Integer> HORSE_FLAGS = new EntityDataType<>(Integer.class, "HORSE_FLAGS");
    public static final EntityDataType<NbtMap> DISPLAY_FIREWORK = new EntityDataType<>(NbtMap.class, "DISPLAY_FIREWORK");
    public static final EntityDataType<BlockDefinition> DISPLAY_BLOCK_STATE = new EntityDataType<>(BlockDefinition.class, "DISPLAY_BLOCK_STATE");
    public static final EntityDataType<Integer> DISPLAY_OFFSET = new EntityDataType<>(Integer.class, "DISPLAY_OFFSET");
    public static final EntityDataType<Byte> CUSTOM_DISPLAY = new EntityDataType<>(Byte.class, "CUSTOM_DISPLAY");
    public static final EntityDataType<Byte> HORSE_TYPE = new EntityDataType<>(Byte.class, "HORSE_TYPE");
    public static final EntityDataType<Integer> OLD_SWELL = new EntityDataType<>(Integer.class, "OLD_SWELL");
    public static final EntityDataType<Integer> SWELL_DIRECTION = new EntityDataType<>(Integer.class, "SWELL_DIRECTION");
    public static final EntityDataType<Byte> CHARGE_AMOUNT = new EntityDataType<>(Byte.class, "CHARGE_AMOUNT");
    public static final EntityDataType<BlockDefinition> CARRY_BLOCK_STATE = new EntityDataType<>(BlockDefinition.class, "CARRY_BLOCK_STATE");
    public static final EntityDataType<Byte> CLIENT_EVENT = new EntityDataType<>(Byte.class, "CLIENT_EVENT");
    public static final EntityDataType<Boolean> USING_ITEM = new EntityDataType<>(Boolean.class, "USING_ITEM");
    public static final EntityDataType<Byte> PLAYER_FLAGS = new EntityDataType<>(Byte.class, "PLAYER_FLAGS");
    public static final EntityDataType<Integer> PLAYER_INDEX = new EntityDataType<>(Integer.class, "PLAYER_INDEX");
    public static final EntityDataType<Vector3i> BED_POSITION = new EntityDataType<>(Vector3i.class, "BED_POSITION");
    /**
     * Power of fireball (x-axis)
     */
    public static final EntityDataType<Float> FIREBALL_POWER_X = new EntityDataType<>(Float.class, "FIREBALL_POWER_X");
    /**
     * Power of fireball (y-axis)
     */
    public static final EntityDataType<Float> FIREBALL_POWER_Y = new EntityDataType<>(Float.class, "FIREBALL_POWER_Y");
    /**
     * Power of fireball (z-axis)
     */
    public static final EntityDataType<Float> FIREBALL_POWER_Z = new EntityDataType<>(Float.class, "FIREBALL_POWER_Z");
    /**
     * Potion aux value used for an Arrow's trail. (Equal to the potion ID - 1)
     */
    public static final EntityDataType<Byte> AUX_POWER = new EntityDataType<>(Byte.class, "AUX_POWER");
    public static final EntityDataType<Float> FISH_X = new EntityDataType<>(Float.class, "FISH_X");
    public static final EntityDataType<Float> FISH_Z = new EntityDataType<>(Float.class, "FISH_Z");
    public static final EntityDataType<Float> FISH_ANGLE = new EntityDataType<>(Float.class, "FISH_ANGLE");
    public static final EntityDataType<Short> AUX_VALUE_DATA = new EntityDataType<>(Short.class, "AUX_VALUE_DATA");
    /**
     * Unique ID for the entity who holds a leash to the current entity.
     */
    public static final EntityDataType<Long> LEASH_HOLDER = new EntityDataType<>(Long.class, "LEASH_HOLDER");
    /**
     * Set the scale of this entity.
     * 1 is the default size defined by {@code EntityDataType#WIDTH} and {@code EntityDataType#HEIGHT}.
     */
    public static final EntityDataType<Float> SCALE = new EntityDataType<>(Float.class, "SCALE");
    public static final EntityDataType<Boolean> HAS_NPC = new EntityDataType<>(Boolean.class, "HAS_NPC");
    public static final EntityDataType<String> NPC_DATA = new EntityDataType<>(String.class, "NPC_DATA");
    public static final EntityDataType<String> ACTIONS = new EntityDataType<>(String.class, "ACTIONS");
    public static final EntityDataType<Short> AIR_SUPPLY_MAX = new EntityDataType<>(Short.class, "AIR_SUPPLY_MAX");
    public static final EntityDataType<Integer> MARK_VARIANT = new EntityDataType<>(Integer.class, "MARK_VARIANT");
    public static final EntityDataType<Byte> CONTAINER_TYPE = new EntityDataType<>(Byte.class, "CONTAINER_TYPE");
    public static final EntityDataType<Integer> CONTAINER_SIZE = new EntityDataType<>(Integer.class, "CONTAINER_SIZE");
    public static final EntityDataType<Integer> CONTAINER_STRENGTH_MODIFIER = new EntityDataType<>(Integer.class, "CONTAINER_STRENGTH_MODIFIER");
    /**
     * Target position of Ender Crystal beam.
     */
    public static final EntityDataType<Vector3i> BLOCK_TARGET_POS = new EntityDataType<>(Vector3i.class, "BLOCK_TARGET_POS");
    public static final EntityDataType<Integer> WITHER_INVULNERABLE_TICKS = new EntityDataType<>(Integer.class, "WITHER_INVULNERABLE_TICKS");
    /**
     * Unique entity ID to target for the left head of a Wither.
     */
    public static final EntityDataType<Long> WITHER_TARGET_A = new EntityDataType<>(Long.class, "WITHER_TARGET_A");
    /**
     * Unique entity ID to target for the middle head of a Wither.
     */
    public static final EntityDataType<Long> WITHER_TARGET_B = new EntityDataType<>(Long.class, "WITHER_TARGET_B");
    /**
     * Unique entity ID to target for the right head of a Wither.
     */
    public static final EntityDataType<Long> WITHER_TARGET_C = new EntityDataType<>(Long.class, "WITHER_TARGET_C");
    public static final EntityDataType<Short> WITHER_AERIAL_ATTACK = new EntityDataType<>(Short.class, "WITHER_AERIAL_ATTACK");
    public static final EntityDataType<Float> WIDTH = new EntityDataType<>(Float.class, "WIDTH");
    public static final EntityDataType<Float> HEIGHT = new EntityDataType<>(Float.class, "HEIGHT");
    public static final EntityDataType<Integer> FUSE_TIME = new EntityDataType<>(Integer.class, "FUSE_TIME");
    public static final EntityDataType<Vector3f> SEAT_OFFSET = new EntityDataType<>(Vector3f.class, "SEAT_OFFSET");
    public static final EntityDataType<Boolean> SEAT_LOCK_RIDER_ROTATION = new EntityDataType<>(Boolean.class, "SEAT_LOCK_RIDER_ROTATION");
    public static final EntityDataType<Float> SEAT_LOCK_RIDER_ROTATION_DEGREES = new EntityDataType<>(Float.class, "SEAT_LOCK_RIDER_ROTATION_DEGREES");
    public static final EntityDataType<Boolean> SEAT_HAS_ROTATION = new EntityDataType<>(Boolean.class, "SEAT_HAS_ROTATION");
    public static final EntityDataType<Float> SEAT_ROTATION_OFFSET_DEGREES = new EntityDataType<>(Float.class, "SEAT_ROTATION_OFFSET_DEGREES");
    /**
     * Radius of Area Effect Cloud
     */
    public static final EntityDataType<Float> AREA_EFFECT_CLOUD_RADIUS = new EntityDataType<>(Float.class, "AREA_EFFECT_CLOUD_RADIUS");
    public static final EntityDataType<Integer> AREA_EFFECT_CLOUD_WAITING = new EntityDataType<>(Integer.class, "AREA_EFFECT_CLOUD_WAITING");
    public static final EntityDataType<ParticleType> AREA_EFFECT_CLOUD_PARTICLE = new EntityDataType<>(ParticleType.class, "AREA_EFFECT_CLOUD_PARTICLE");
    public static final EntityDataType<Integer> SHULKER_PEEK_AMOUNT = new EntityDataType<>(Integer.class, "SHULKER_PEEK_AMOUNT");
    public static final EntityDataType<Integer> SHULKER_ATTACH_FACE = new EntityDataType<>(Integer.class, "SHULKER_ATTACH_FACE");
    public static final EntityDataType<Boolean> SHULKER_ATTACHED = new EntityDataType<>(Boolean.class, "SHULKER_ATTACHED");
    /**
     * Position a Shulker entity is attached from.
     */
    public static final EntityDataType<Vector3i> SHULKER_ATTACH_POS = new EntityDataType<>(Vector3i.class, "SHULKER_ATTACH_POS");
    /**
     * Sets the unique ID of the player that is trading with this entity.
     */
    public static final EntityDataType<Long> TRADE_TARGET_EID = new EntityDataType<>(Long.class, "TRADE_TARGET_EID");
    /**
     * Previously used for the villager V1 entity.
     *
     * @deprecated unused AFAIK
     */
    @Deprecated
    public static final EntityDataType<Integer> CAREER = new EntityDataType<>(Integer.class, "CAREER");
    public static final EntityDataType<Boolean> COMMAND_BLOCK_ENABLED = new EntityDataType<>(Boolean.class, "COMMAND_BLOCK_ENABLED");
    public static final EntityDataType<String> COMMAND_BLOCK_NAME = new EntityDataType<>(String.class, "COMMAND_BLOCK_NAME");
    public static final EntityDataType<String> COMMAND_BLOCK_LAST_OUTPUT = new EntityDataType<>(String.class, "COMMAND_BLOCK_LAST_OUTPUT");
    public static final EntityDataType<Boolean> COMMAND_BLOCK_TRACK_OUTPUT = new EntityDataType<>(Boolean.class, "COMMAND_BLOCK_TRACK_OUTPUT");
    public static final EntityDataType<Byte> CONTROLLING_RIDER_SEAT_INDEX = new EntityDataType<>(Byte.class, "CONTROLLING_RIDER_SEAT_INDEX");
    public static final EntityDataType<Integer> STRENGTH = new EntityDataType<>(Integer.class, "STRENGTH");
    public static final EntityDataType<Integer> STRENGTH_MAX = new EntityDataType<>(Integer.class, "STRENGTH_MAX");
    public static final EntityDataType<Integer> EVOKER_SPELL_CASTING_COLOR = new EntityDataType<>(Integer.class, "EVOKER_SPELL_CASTING_COLOR");
    public static final EntityDataType<Integer> DATA_LIFETIME_TICKS = new EntityDataType<>(Integer.class, "DATA_LIFETIME_TICKS");
    public static final EntityDataType<Integer> ARMOR_STAND_POSE_INDEX = new EntityDataType<>(Integer.class, "ARMOR_STAND_POSE_INDEX");
    public static final EntityDataType<Integer> END_CRYSTAL_TICK_OFFSET = new EntityDataType<>(Integer.class, "END_CRYSTAL_TICK_OFFSET");
    public static final EntityDataType<Byte> NAMETAG_ALWAYS_SHOW = new EntityDataType<>(Byte.class, "NAMETAG_ALWAYS_SHOW");
    public static final EntityDataType<Byte> COLOR_2 = new EntityDataType<>(Byte.class, "COLOR_2");
    public static final EntityDataType<String> NAME_AUTHOR = new EntityDataType<>(String.class, "NAME_AUTHOR");
    public static final EntityDataType<String> SCORE = new EntityDataType<>(String.class, "SCORE");
    /**
     * Unique entity ID that the balloon string is attached to.
     * Disable by setting value to -1.
     */
    public static final EntityDataType<Long> BALLOON_ANCHOR_EID = new EntityDataType<>(Long.class, "BALLOON_ANCHOR_EID");
    public static final EntityDataType<Byte> PUFFED_STATE = new EntityDataType<>(Byte.class, "PUFFED_STATE");
    public static final EntityDataType<Integer> BOAT_BUBBLE_TIME = new EntityDataType<>(Integer.class, "BOAT_BUBBLE_TIME");
    /**
     * The unique entity ID of the player's Agent. (Education Edition only)
     */
    public static final EntityDataType<Long> AGENT_EID = new EntityDataType<>(Long.class, "AGENT_EID");
    public static final EntityDataType<Float> SITTING_AMOUNT = new EntityDataType<>(Float.class, "SITTING_AMOUNT");
    public static final EntityDataType<Float> SITTING_AMOUNT_PREVIOUS = new EntityDataType<>(Float.class, "SITTING_AMOUNT_PREVIOUS");
    public static final EntityDataType<Integer> EATING_COUNTER = new EntityDataType<>(Integer.class, "EATING_COUNTER");
    public static final EntityDataType<EnumSet<EntityFlag>> FLAGS_2 = new EntityDataType<>(EnumSet.class, "FLAGS_2");
    public static final EntityDataType<Float> LAYING_AMOUNT = new EntityDataType<>(Float.class, "LAYING_AMOUNT");
    public static final EntityDataType<Float> LAYING_AMOUNT_PREVIOUS = new EntityDataType<>(Float.class, "LAYING_AMOUNT_PREVIOUS");
    public static final EntityDataType<Integer> AREA_EFFECT_CLOUD_DURATION = new EntityDataType<>(Integer.class, "AREA_EFFECT_CLOUD_DURATION");
    public static final EntityDataType<Integer> AREA_EFFECT_CLOUD_SPAWN_TIME = new EntityDataType<>(Integer.class, "AREA_EFFECT_CLOUD_SPAWN_TIME");
    /**
     * @deprecated since v685
     */
    @Deprecated
    public static final EntityDataType<Float> AREA_EFFECT_CLOUD_CHANGE_RATE = new EntityDataType<>(Float.class, "AREA_EFFECT_CLOUD_CHANGE_RATE");
    public static final EntityDataType<Float> AREA_EFFECT_CLOUD_CHANGE_ON_PICKUP = new EntityDataType<>(Float.class, "AREA_EFFECT_CLOUD_CHANGE_ON_PICKUP");
    public static final EntityDataType<Integer> AREA_EFFECT_CLOUD_PICKUP_COUNT = new EntityDataType<>(Integer.class, "AREA_EFFECT_CLOUD_PICKUP_COUNT");
    public static final EntityDataType<String> INTERACT_TEXT = new EntityDataType<>(String.class, "INTERACT_TEXT");
    public static final EntityDataType<Integer> TRADE_TIER = new EntityDataType<>(Integer.class, "TRADE_TIER");
    public static final EntityDataType<Integer> MAX_TRADE_TIER = new EntityDataType<>(Integer.class, "MAX_TRADE_TIER");
    public static final EntityDataType<Integer> TRADE_EXPERIENCE = new EntityDataType<>(Integer.class, "TRADE_EXPERIENCE");
    public static final EntityDataType<Integer> SKIN_ID = new EntityDataType<>(Integer.class, "SKIN_ID");
    public static final EntityDataType<Integer> SPAWNING_FRAMES = new EntityDataType<>(Integer.class, "SPAWNING_FRAMES");
    public static final EntityDataType<Integer> COMMAND_BLOCK_TICK_DELAY = new EntityDataType<>(Integer.class, "COMMAND_BLOCK_TICK_DELAY");
    public static final EntityDataType<Boolean> COMMAND_BLOCK_EXECUTE_ON_FIRST_TICK = new EntityDataType<>(Boolean.class, "COMMAND_BLOCK_EXECUTE_ON_FIRST_TICK");
    public static final EntityDataType<Float> AMBIENT_SOUND_INTERVAL = new EntityDataType<>(Float.class, "AMBIENT_SOUND_INTERVAL");
    public static final EntityDataType<Float> AMBIENT_SOUND_INTERVAL_RANGE = new EntityDataType<>(Float.class, "AMBIENT_SOUND_INTERVAL_RANGE");
    public static final EntityDataType<String> AMBIENT_SOUND_EVENT_NAME = new EntityDataType<>(String.class, "AMBIENT_SOUND_EVENT_NAME");
    public static final EntityDataType<Float> FALL_DAMAGE_MULTIPLIER = new EntityDataType<>(Float.class, "FALL_DAMAGE_MULTIPLIER");
    public static final EntityDataType<String> NAME_RAW_TEXT = new EntityDataType<>(String.class, "NAME_RAW_TEXT");
    public static final EntityDataType<Boolean> CAN_RIDE_TARGET = new EntityDataType<>(Boolean.class, "CAN_RIDE_TARGET");
    public static final EntityDataType<Integer> LOW_TIER_CURED_TRADE_DISCOUNT = new EntityDataType<>(Integer.class, "LOW_TIER_CURED_TRADE_DISCOUNT");
    public static final EntityDataType<Integer> HIGH_TIER_CURED_TRADE_DISCOUNT = new EntityDataType<>(Integer.class, "HIGH_TIER_CURED_TRADE_DISCOUNT");
    public static final EntityDataType<Integer> NEARBY_CURED_TRADE_DISCOUNT = new EntityDataType<>(Integer.class, "NEARBY_CURED_TRADE_DISCOUNT");
    public static final EntityDataType<Integer> NEARBY_CURED_DISCOUNT_TIME_STAMP = new EntityDataType<>(Integer.class, "NEARBY_CURED_DISCOUNT_TIME_STAMP");

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
    public static final EntityDataType<NbtMap> HITBOX = new EntityDataType<>(NbtMap.class, "HITBOX");
    public static final EntityDataType<Boolean> IS_BUOYANT = new EntityDataType<>(Boolean.class, "IS_BUOYANT");
    public static final EntityDataType<String> BASE_RUNTIME_ID = new EntityDataType<>(String.class, "BASE_RUNTIME_ID");
    /**
     * Custom properties from the <pre>PropertyComponent</pre>.
     *
     * @deprecated v557
     */
    @Deprecated
    public static final EntityDataType<NbtMap> UPDATE_PROPERTIES = new EntityDataType<>(NbtMap.class, "UPDATE_PROPERTIES");
    public static final EntityDataType<Float> FREEZING_EFFECT_STRENGTH = new EntityDataType<>(Float.class, "FREEZING_EFFECT_STRENGTH");
    public static final EntityDataType<String> BUOYANCY_DATA = new EntityDataType<>(String.class, "BUOYANCY_DATA");
    public static final EntityDataType<Integer> GOAT_HORN_COUNT = new EntityDataType<>(Integer.class, "GOAT_HORN_COUNT");
    /**
     * @since v503
     */
    public static final EntityDataType<Float> MOVEMENT_SOUND_DISTANCE_OFFSET = new EntityDataType<>(Float.class, "MOVEMENT_SOUND_DISTANCE_OFFSET");
    /**
     * @since v503
     */
    public static final EntityDataType<Integer> HEARTBEAT_INTERVAL_TICKS = new EntityDataType<>(Integer.class, "HEARTBEAT_INTERVAL_TICKS");
    /**
     * @since v503
     */
    public static final EntityDataType<Integer> HEARTBEAT_SOUND_EVENT = new EntityDataType<>(Integer.class, "HEARTBEAT_SOUND_EVENT");
    /**
     * @since v527
     */
    public static final EntityDataType<Vector3i> PLAYER_LAST_DEATH_POS = new EntityDataType<>(Vector3i.class, "PLAYER_LAST_DEATH_POS");
    /**
     * @since v527
     */
    public static final EntityDataType<Integer> PLAYER_LAST_DEATH_DIMENSION = new EntityDataType<>(Integer.class, "PLAYER_LAST_DEATH_DIMENSION");
    /**
     * @since v527
     */
    public static final EntityDataType<Boolean> PLAYER_HAS_DIED = new EntityDataType<>(Boolean.class, "PLAYER_HAS_DIED");
    /**
     * @since v594
     */
    public static final EntityDataType<Vector3f> COLLISION_BOX = new EntityDataType<>(Vector3f.class, "COLLISION_BOX");
    /**
     * @since v685
     */
    public static final EntityDataType<Long> VISIBLE_MOB_EFFECTS = new EntityDataType<>(Long.class, "VISIBLE_MOB_EFFECTS");
}
