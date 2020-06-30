package com.nukkitx.protocol.bedrock.v291;

import com.nukkitx.math.vector.Vector3f;
import com.nukkitx.math.vector.Vector3i;
import com.nukkitx.nbt.NbtUtils;
import com.nukkitx.nbt.stream.NBTInputStream;
import com.nukkitx.nbt.stream.NBTOutputStream;
import com.nukkitx.nbt.tag.CompoundTag;
import com.nukkitx.nbt.tag.Tag;
import com.nukkitx.network.VarInts;
import com.nukkitx.network.util.Preconditions;
import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.data.GameRuleData;
import com.nukkitx.protocol.bedrock.data.LevelEventType;
import com.nukkitx.protocol.bedrock.data.SoundEvent;
import com.nukkitx.protocol.bedrock.data.command.CommandEnumData;
import com.nukkitx.protocol.bedrock.data.command.CommandOriginData;
import com.nukkitx.protocol.bedrock.data.command.CommandOriginType;
import com.nukkitx.protocol.bedrock.data.command.CommandParamType;
import com.nukkitx.protocol.bedrock.data.entity.*;
import com.nukkitx.protocol.bedrock.data.inventory.ItemData;
import com.nukkitx.protocol.bedrock.data.inventory.ItemInstance;
import com.nukkitx.protocol.bedrock.data.skin.ImageData;
import com.nukkitx.protocol.bedrock.data.skin.SerializedSkin;
import com.nukkitx.protocol.bedrock.data.structure.StructureSettings;
import com.nukkitx.protocol.bedrock.util.LittleEndianByteBufOutputStream;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import static com.nukkitx.protocol.bedrock.data.entity.EntityData.Type;

@SuppressWarnings("PointlessBitwiseExpression")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BedrockPacketHelper_v291 extends BedrockPacketHelper {

    public static final BedrockPacketHelper INSTANCE = new BedrockPacketHelper_v291();

    @Override
    protected void registerEntityData() {
        this.addEntityData(0, EntityData.FLAGS);
        this.addEntityData(1, EntityData.HEALTH);
        this.addEntityData(2, EntityData.VARIANT);
        this.addEntityData(3, EntityData.COLOR);
        this.addEntityData(4, EntityData.NAMETAG);
        this.addEntityData(5, EntityData.OWNER_EID);
        this.addEntityData(6, EntityData.TARGET_EID);
        this.addEntityData(7, EntityData.AIR_SUPPLY);
        this.addEntityData(8, EntityData.EFFECT_COLOR);
        this.addEntityData(9, EntityData.EFFECT_AMBIENT);
        this.addEntityData(10, EntityData.JUMP_DURATION);
        this.addEntityData(11, EntityData.HURT_TIME);
        this.addEntityData(12, EntityData.HURT_DIRECTION);
        this.addEntityData(13, EntityData.ROW_TIME_LEFT);
        this.addEntityData(14, EntityData.ROW_TIME_RIGHT);
        this.addEntityData(15, EntityData.EXPERIENCE_VALUE);
        this.addEntityData(16, EntityData.DISPLAY_ITEM);
        this.addEntityData(17, EntityData.DISPLAY_OFFSET);
        this.addEntityData(18, EntityData.CUSTOM_DISPLAY);
        this.addEntityData(19, EntityData.SWELL);
        this.addEntityData(20, EntityData.OLD_SWELL);
        this.addEntityData(21, EntityData.SWELL_DIRECTION);
        this.addEntityData(22, EntityData.CHARGE_AMOUNT);
        this.addEntityData(23, EntityData.CARRIED_BLOCK);
        this.addEntityData(24, EntityData.CLIENT_EVENT);
        this.addEntityData(25, EntityData.USING_ITEM);
        this.addEntityData(26, EntityData.PLAYER_FLAGS);
        this.addEntityData(27, EntityData.PLAYER_INDEX);
        this.addEntityData(28, EntityData.BED_POSITION);
        this.addEntityData(29, EntityData.X_POWER);
        this.addEntityData(30, EntityData.Y_POWER);
        this.addEntityData(31, EntityData.Z_POWER);
        this.addEntityData(32, EntityData.AUX_POWER);
        this.addEntityData(33, EntityData.FISH_X);
        this.addEntityData(34, EntityData.FISH_Z);
        this.addEntityData(35, EntityData.FISH_ANGLE);
        this.addEntityData(36, EntityData.POTION_AUX_VALUE);
        this.addEntityData(37, EntityData.LEASH_HOLDER_EID);
        this.addEntityData(38, EntityData.SCALE);
        this.addEntityData(39, EntityData.INTERACTIVE_TAG);
        this.addEntityData(40, EntityData.NPC_SKIN_ID);
        this.addEntityData(41, EntityData.URL_TAG);
        this.addEntityData(42, EntityData.MAX_AIR_SUPPLY);
        this.addEntityData(43, EntityData.MARK_VARIANT);
        this.addEntityData(44, EntityData.CONTAINER_TYPE);
        this.addEntityData(45, EntityData.CONTAINER_BASE_SIZE);
        this.addEntityData(46, EntityData.CONTAINER_STRENGTH_MODIFIER);
        this.addEntityData(47, EntityData.BLOCK_TARGET);
        this.addEntityData(48, EntityData.WITHER_INVULNERABLE_TICKS);
        this.addEntityData(49, EntityData.WITHER_TARGET_1);
        this.addEntityData(50, EntityData.WITHER_TARGET_2);
        this.addEntityData(51, EntityData.WITHER_TARGET_3);
        this.addEntityData(52, EntityData.WITHER_AERIAL_ATTACK);
        this.addEntityData(53, EntityData.BOUNDING_BOX_WIDTH);
        this.addEntityData(54, EntityData.BOUNDING_BOX_HEIGHT);
        this.addEntityData(55, EntityData.FUSE_LENGTH);
        this.addEntityData(56, EntityData.RIDER_SEAT_POSITION);
        this.addEntityData(57, EntityData.RIDER_ROTATION_LOCKED);
        this.addEntityData(58, EntityData.RIDER_MAX_ROTATION);
        this.addEntityData(59, EntityData.RIDER_MIN_ROTATION);
        this.addEntityData(60, EntityData.AREA_EFFECT_CLOUD_RADIUS);
        this.addEntityData(61, EntityData.AREA_EFFECT_CLOUD_WAITING);
        this.addEntityData(62, EntityData.AREA_EFFECT_CLOUD_PARTICLE_ID);
        this.addEntityData(63, EntityData.SHULKER_PEEK_ID);
        this.addEntityData(64, EntityData.SHULKER_ATTACH_FACE);
        this.addEntityData(66, EntityData.SHULKER_ATTACH_POS);
        this.addEntityData(67, EntityData.TRADE_TARGET_EID);
        this.addEntityData(69, EntityData.COMMAND_BLOCK_ENABLED); // Unsure
        this.addEntityData(70, EntityData.COMMAND_BLOCK_COMMAND);
        this.addEntityData(71, EntityData.COMMAND_BLOCK_LAST_OUTPUT);
        this.addEntityData(72, EntityData.COMMAND_BLOCK_TRACK_OUTPUT);
        this.addEntityData(73, EntityData.CONTROLLING_RIDER_SEAT_INDEX);
        this.addEntityData(74, EntityData.STRENGTH);
        this.addEntityData(75, EntityData.MAX_STRENGTH);
        this.addEntityData(76, EntityData.EVOKER_SPELL_COLOR);
        this.addEntityData(77, EntityData.LIMITED_LIFE);
        this.addEntityData(78, EntityData.ARMOR_STAND_POSE_INDEX);
        this.addEntityData(79, EntityData.ENDER_CRYSTAL_TIME_OFFSET);
        this.addEntityData(80, EntityData.NAMETAG_ALWAYS_SHOW);
        this.addEntityData(81, EntityData.COLOR_2);
        this.addEntityData(83, EntityData.SCORE_TAG);
        this.addEntityData(84, EntityData.BALLOON_ATTACHED_ENTITY);
        this.addEntityData(85, EntityData.PUFFERFISH_SIZE);
        this.addEntityData(86, EntityData.BOAT_BUBBLE_TIME);
        this.addEntityData(87, EntityData.AGENT_ID);
    }

    @Override
    protected void registerEntityFlags() {
        this.addEntityFlag(0, EntityFlag.ON_FIRE);
        this.addEntityFlag(1, EntityFlag.SNEAKING);
        this.addEntityFlag(2, EntityFlag.RIDING);
        this.addEntityFlag(3, EntityFlag.SPRINTING);
        this.addEntityFlag(4, EntityFlag.USING_ITEM);
        this.addEntityFlag(5, EntityFlag.INVISIBLE);
        this.addEntityFlag(6, EntityFlag.TEMPTED);
        this.addEntityFlag(7, EntityFlag.IN_LOVE);
        this.addEntityFlag(8, EntityFlag.SADDLED);
        this.addEntityFlag(9, EntityFlag.POWERED);
        this.addEntityFlag(10, EntityFlag.IGNITED);
        this.addEntityFlag(11, EntityFlag.BABY);
        this.addEntityFlag(12, EntityFlag.CONVERTING);
        this.addEntityFlag(13, EntityFlag.CRITICAL);
        this.addEntityFlag(14, EntityFlag.CAN_SHOW_NAME);
        this.addEntityFlag(15, EntityFlag.ALWAYS_SHOW_NAME);
        this.addEntityFlag(16, EntityFlag.NO_AI);
        this.addEntityFlag(17, EntityFlag.SILENT);
        this.addEntityFlag(18, EntityFlag.WALL_CLIMBING);
        this.addEntityFlag(19, EntityFlag.CAN_CLIMB);
        this.addEntityFlag(20, EntityFlag.CAN_SWIM);
        this.addEntityFlag(21, EntityFlag.CAN_FLY);
        this.addEntityFlag(22, EntityFlag.CAN_WALK);
        this.addEntityFlag(23, EntityFlag.RESTING);
        this.addEntityFlag(24, EntityFlag.SITTING);
        this.addEntityFlag(25, EntityFlag.ANGRY);
        this.addEntityFlag(26, EntityFlag.INTERESTED);
        this.addEntityFlag(27, EntityFlag.CHARGED);
        this.addEntityFlag(28, EntityFlag.TAMED);
        this.addEntityFlag(29, EntityFlag.ORPHANED);
        this.addEntityFlag(30, EntityFlag.LEASHED);
        this.addEntityFlag(31, EntityFlag.SHEARED);
        this.addEntityFlag(32, EntityFlag.GLIDING);
        this.addEntityFlag(33, EntityFlag.ELDER);
        this.addEntityFlag(34, EntityFlag.MOVING);
        this.addEntityFlag(35, EntityFlag.BREATHING);
        this.addEntityFlag(36, EntityFlag.CHESTED);
        this.addEntityFlag(37, EntityFlag.STACKABLE);
        this.addEntityFlag(38, EntityFlag.SHOW_BOTTOM);
        this.addEntityFlag(39, EntityFlag.STANDING);
        this.addEntityFlag(40, EntityFlag.SHAKING);
        this.addEntityFlag(41, EntityFlag.IDLING);
        this.addEntityFlag(42, EntityFlag.CASTING);
        this.addEntityFlag(43, EntityFlag.CHARGING);
        this.addEntityFlag(44, EntityFlag.WASD_CONTROLLED);
        this.addEntityFlag(45, EntityFlag.CAN_POWER_JUMP);
        this.addEntityFlag(46, EntityFlag.LINGERING);
        this.addEntityFlag(47, EntityFlag.HAS_COLLISION);
        this.addEntityFlag(48, EntityFlag.HAS_GRAVITY);
        this.addEntityFlag(49, EntityFlag.FIRE_IMMUNE);
        this.addEntityFlag(50, EntityFlag.DANCING);
        this.addEntityFlag(51, EntityFlag.ENCHANTED);
        this.addEntityFlag(52, EntityFlag.RETURN_TRIDENT);
        this.addEntityFlag(53, EntityFlag.CONTAINER_IS_PRIVATE);
        this.addEntityFlag(54, EntityFlag.IS_TRANSFORMING);
        this.addEntityFlag(55, EntityFlag.DAMAGE_NEARBY_MOBS);
        this.addEntityFlag(56, EntityFlag.SWIMMING);
        this.addEntityFlag(57, EntityFlag.BRIBED);
        this.addEntityFlag(58, EntityFlag.IS_PREGNANT);
        this.addEntityFlag(59, EntityFlag.LAYING_EGG);
        this.addEntityFlag(60, EntityFlag.RIDER_CAN_PICK);
    }

    @Override
    protected void registerEntityDataTypes() {
        this.addEntityDataType(7, Type.FLAGS);
        this.addEntityDataType(0, Type.BYTE);
        this.addEntityDataType(1, Type.SHORT);
        this.addEntityDataType(2, Type.INT);
        this.addEntityDataType(3, Type.FLOAT);
        this.addEntityDataType(4, Type.STRING);
        this.addEntityDataType(5, Type.NBT);
        this.addEntityDataType(6, Type.VECTOR3I);
        this.addEntityDataType(7, Type.LONG);
        this.addEntityDataType(8, Type.VECTOR3F);
    }

    @Override
    protected void registerEntityEvents() {
        this.addEntityEvent(0, EntityEventType.NONE);
        this.addEntityEvent(1, EntityEventType.JUMP);
        this.addEntityEvent(2, EntityEventType.HURT);
        this.addEntityEvent(3, EntityEventType.DEATH);
        this.addEntityEvent(4, EntityEventType.ATTACK_START);
        this.addEntityEvent(4, EntityEventType.ATTACK_STOP);
        this.addEntityEvent(6, EntityEventType.TAME_FAILED);
        this.addEntityEvent(7, EntityEventType.TAME_SUCCEEDED);
        this.addEntityEvent(8, EntityEventType.SHAKE_WETNESS);
        this.addEntityEvent(9, EntityEventType.USE_ITEM);
        this.addEntityEvent(10, EntityEventType.EAT_GRASS);
        this.addEntityEvent(11, EntityEventType.FISH_HOOK_BUBBLE);
        this.addEntityEvent(12, EntityEventType.FISH_HOOK_POSITION);
        this.addEntityEvent(13, EntityEventType.FISH_HOOK_TIME);
        this.addEntityEvent(14, EntityEventType.FISH_HOOK_TEASE);
        this.addEntityEvent(15, EntityEventType.SQUID_FLEEING);
        this.addEntityEvent(16, EntityEventType.ZOMBIE_VILLAGER_CURE);
        this.addEntityEvent(17, EntityEventType.PLAY_AMBIENT);
        this.addEntityEvent(18, EntityEventType.RESPAWN);
        this.addEntityEvent(19, EntityEventType.GOLEM_FLOWER_OFFER);
        this.addEntityEvent(20, EntityEventType.GOLEM_FLOWER_WITHDRAW);
        this.addEntityEvent(21, EntityEventType.LOVE_PARTICLES);
        this.addEntityEvent(22, EntityEventType.VILLAGER_ANGRY);
        this.addEntityEvent(23, EntityEventType.VILLAGER_HAPPY);
        this.addEntityEvent(24, EntityEventType.WITCH_HAT_MAGIC);
        this.addEntityEvent(25, EntityEventType.FIREWORK_EXPLODE);
        this.addEntityEvent(26, EntityEventType.IN_LOVE_HEARTS);
        this.addEntityEvent(27, EntityEventType.SILVERFISH_MERGE_WITH_STONE);
        this.addEntityEvent(28, EntityEventType.GUARDIAN_ATTACK_ANIMATION);
        this.addEntityEvent(29, EntityEventType.WITCH_DRINK_POTION);
        this.addEntityEvent(30, EntityEventType.WITCH_THROW_POTION);
        this.addEntityEvent(31, EntityEventType.PRIME_TNT_MINECART);
        this.addEntityEvent(32, EntityEventType.PRIME_CREEPER);
        this.addEntityEvent(33, EntityEventType.AIR_SUPPLY);
        this.addEntityEvent(34, EntityEventType.PLAYER_ADD_XP_LEVELS);
        this.addEntityEvent(35, EntityEventType.ELDER_GUARDIAN_CURSE);
        this.addEntityEvent(36, EntityEventType.AGENT_ARM_SWING);
        this.addEntityEvent(37, EntityEventType.ENDER_DRAGON_DEATH);
        this.addEntityEvent(38, EntityEventType.DUST_PARTICLES);
        this.addEntityEvent(39, EntityEventType.ARROW_SHAKE);

        this.addEntityEvent(57, EntityEventType.EATING_ITEM);
        this.addEntityEvent(60, EntityEventType.BABY_ANIMAL_FEED);
        this.addEntityEvent(61, EntityEventType.DEATH_SMOKE_CLOUD);
        this.addEntityEvent(62, EntityEventType.COMPLETE_TRADE);
        this.addEntityEvent(63, EntityEventType.REMOVE_LEASH);
        this.addEntityEvent(64, EntityEventType.CARAVAN);
        this.addEntityEvent(65, EntityEventType.CONSUME_TOTEM);
        this.addEntityEvent(66, EntityEventType.CHECK_TREASURE_HUNTER_ACHIEVEMENT);
        this.addEntityEvent(67, EntityEventType.ENTITY_SPAWN);
        this.addEntityEvent(68, EntityEventType.DRAGON_FLAMING);
        this.addEntityEvent(69, EntityEventType.UPDATE_ITEM_STACK_SIZE);
        this.addEntityEvent(70, EntityEventType.START_SWIMMING);
        this.addEntityEvent(71, EntityEventType.BALLOON_POP);
        this.addEntityEvent(72, EntityEventType.TREASURE_HUNT);
    }

    @Override
    protected void registerGameRuleTypes() {
        this.addGameRuleType(1, Boolean.class);
        this.addGameRuleType(2, Integer.class);
        this.addGameRuleType(3, Float.class);
    }

    @Override
    protected void registerSoundEvents() {
        this.addSoundEvent(0, SoundEvent.ITEM_USE_ON);
        this.addSoundEvent(1, SoundEvent.HIT);
        this.addSoundEvent(2, SoundEvent.STEP);
        this.addSoundEvent(3, SoundEvent.FLY);
        this.addSoundEvent(4, SoundEvent.JUMP);
        this.addSoundEvent(5, SoundEvent.BREAK);
        this.addSoundEvent(6, SoundEvent.PLACE);
        this.addSoundEvent(7, SoundEvent.HEAVY_STEP);
        this.addSoundEvent(8, SoundEvent.GALLOP);
        this.addSoundEvent(9, SoundEvent.FALL);
        this.addSoundEvent(10, SoundEvent.AMBIENT);
        this.addSoundEvent(11, SoundEvent.AMBIENT_BABY);
        this.addSoundEvent(12, SoundEvent.AMBIENT_IN_WATER);
        this.addSoundEvent(13, SoundEvent.BREATHE);
        this.addSoundEvent(14, SoundEvent.DEATH);
        this.addSoundEvent(15, SoundEvent.DEATH_IN_WATER);
        this.addSoundEvent(16, SoundEvent.DEATH_TO_ZOMBIE);
        this.addSoundEvent(17, SoundEvent.HURT);
        this.addSoundEvent(18, SoundEvent.HURT_IN_WATER);
        this.addSoundEvent(19, SoundEvent.MAD);
        this.addSoundEvent(20, SoundEvent.BOOST);
        this.addSoundEvent(21, SoundEvent.BOW);
        this.addSoundEvent(22, SoundEvent.SQUISH_BIG);
        this.addSoundEvent(23, SoundEvent.SQUISH_SMALL);
        this.addSoundEvent(24, SoundEvent.FALL_BIG);
        this.addSoundEvent(25, SoundEvent.FALL_SMALL);
        this.addSoundEvent(26, SoundEvent.SPLASH);
        this.addSoundEvent(27, SoundEvent.FIZZ);
        this.addSoundEvent(28, SoundEvent.FLAP);
        this.addSoundEvent(29, SoundEvent.SWIM);
        this.addSoundEvent(30, SoundEvent.DRINK);
        this.addSoundEvent(31, SoundEvent.EAT);
        this.addSoundEvent(32, SoundEvent.TAKEOFF);
        this.addSoundEvent(33, SoundEvent.SHAKE);
        this.addSoundEvent(34, SoundEvent.PLOP);
        this.addSoundEvent(35, SoundEvent.LAND);
        this.addSoundEvent(36, SoundEvent.SADDLE);
        this.addSoundEvent(37, SoundEvent.ARMOR);
        this.addSoundEvent(38, SoundEvent.MOB_ARMOR_STAND_PLACE);
        this.addSoundEvent(39, SoundEvent.ADD_CHEST);
        this.addSoundEvent(40, SoundEvent.THROW);
        this.addSoundEvent(41, SoundEvent.ATTACK);
        this.addSoundEvent(42, SoundEvent.ATTACK_NODAMAGE);
        this.addSoundEvent(43, SoundEvent.ATTACK_STRONG);
        this.addSoundEvent(44, SoundEvent.WARN);
        this.addSoundEvent(45, SoundEvent.SHEAR);
        this.addSoundEvent(46, SoundEvent.MILK);
        this.addSoundEvent(47, SoundEvent.THUNDER);
        this.addSoundEvent(48, SoundEvent.EXPLODE);
        this.addSoundEvent(49, SoundEvent.FIRE);
        this.addSoundEvent(50, SoundEvent.IGNITE);
        this.addSoundEvent(51, SoundEvent.FUSE);
        this.addSoundEvent(52, SoundEvent.STARE);
        this.addSoundEvent(53, SoundEvent.SPAWN);
        this.addSoundEvent(54, SoundEvent.SHOOT);
        this.addSoundEvent(55, SoundEvent.BREAK_BLOCK);
        this.addSoundEvent(56, SoundEvent.LAUNCH);
        this.addSoundEvent(57, SoundEvent.BLAST);
        this.addSoundEvent(58, SoundEvent.LARGE_BLAST);
        this.addSoundEvent(59, SoundEvent.TWINKLE);
        this.addSoundEvent(60, SoundEvent.REMEDY);
        this.addSoundEvent(61, SoundEvent.UNFECT);
        this.addSoundEvent(62, SoundEvent.LEVELUP);
        this.addSoundEvent(63, SoundEvent.BOW_HIT);
        this.addSoundEvent(64, SoundEvent.BULLET_HIT);
        this.addSoundEvent(65, SoundEvent.EXTINGUISH_FIRE);
        this.addSoundEvent(66, SoundEvent.ITEM_FIZZ);
        this.addSoundEvent(67, SoundEvent.CHEST_OPEN);
        this.addSoundEvent(68, SoundEvent.CHEST_CLOSED);
        this.addSoundEvent(69, SoundEvent.SHULKERBOX_OPEN);
        this.addSoundEvent(70, SoundEvent.SHULKERBOX_CLOSED);
        this.addSoundEvent(71, SoundEvent.ENDERCHEST_OPEN);
        this.addSoundEvent(72, SoundEvent.ENDERCHEST_CLOSED);
        this.addSoundEvent(73, SoundEvent.POWER_ON);
        this.addSoundEvent(74, SoundEvent.POWER_OFF);
        this.addSoundEvent(75, SoundEvent.ATTACH);
        this.addSoundEvent(76, SoundEvent.DETACH);
        this.addSoundEvent(77, SoundEvent.DENY);
        this.addSoundEvent(78, SoundEvent.TRIPOD);
        this.addSoundEvent(79, SoundEvent.POP);
        this.addSoundEvent(80, SoundEvent.DROP_SLOT);
        this.addSoundEvent(81, SoundEvent.NOTE);
        this.addSoundEvent(82, SoundEvent.THORNS);
        this.addSoundEvent(83, SoundEvent.PISTON_IN);
        this.addSoundEvent(84, SoundEvent.PISTON_OUT);
        this.addSoundEvent(85, SoundEvent.PORTAL);
        this.addSoundEvent(86, SoundEvent.WATER);
        this.addSoundEvent(87, SoundEvent.LAVA_POP);
        this.addSoundEvent(88, SoundEvent.LAVA);
        this.addSoundEvent(89, SoundEvent.BURP);
        this.addSoundEvent(90, SoundEvent.BUCKET_FILL_WATER);
        this.addSoundEvent(91, SoundEvent.BUCKET_FILL_LAVA);
        this.addSoundEvent(92, SoundEvent.BUCKET_EMPTY_WATER);
        this.addSoundEvent(93, SoundEvent.BUCKET_EMPTY_LAVA);
        this.addSoundEvent(94, SoundEvent.ARMOR_EQUIP_CHAIN);
        this.addSoundEvent(95, SoundEvent.ARMOR_EQUIP_DIAMOND);
        this.addSoundEvent(96, SoundEvent.ARMOR_EQUIP_GENERIC);
        this.addSoundEvent(97, SoundEvent.ARMOR_EQUIP_GOLD);
        this.addSoundEvent(98, SoundEvent.ARMOR_EQUIP_IRON);
        this.addSoundEvent(99, SoundEvent.ARMOR_EQUIP_LEATHER);
        this.addSoundEvent(100, SoundEvent.ARMOR_EQUIP_ELYTRA);
        this.addSoundEvent(101, SoundEvent.RECORD_13);
        this.addSoundEvent(102, SoundEvent.RECORD_CAT);
        this.addSoundEvent(103, SoundEvent.RECORD_BLOCKS);
        this.addSoundEvent(104, SoundEvent.RECORD_CHIRP);
        this.addSoundEvent(105, SoundEvent.RECORD_FAR);
        this.addSoundEvent(106, SoundEvent.RECORD_MALL);
        this.addSoundEvent(107, SoundEvent.RECORD_MELLOHI);
        this.addSoundEvent(108, SoundEvent.RECORD_STAL);
        this.addSoundEvent(109, SoundEvent.RECORD_STRAD);
        this.addSoundEvent(110, SoundEvent.RECORD_WARD);
        this.addSoundEvent(111, SoundEvent.RECORD_11);
        this.addSoundEvent(112, SoundEvent.RECORD_WAIT);
        this.addSoundEvent(113, SoundEvent.STOP_RECORD);
        this.addSoundEvent(114, SoundEvent.FLOP);
        this.addSoundEvent(115, SoundEvent.ELDERGUARDIAN_CURSE);
        this.addSoundEvent(116, SoundEvent.MOB_WARNING);
        this.addSoundEvent(117, SoundEvent.MOB_WARNING_BABY);
        this.addSoundEvent(118, SoundEvent.TELEPORT);
        this.addSoundEvent(119, SoundEvent.SHULKER_OPEN);
        this.addSoundEvent(120, SoundEvent.SHULKER_CLOSE);
        this.addSoundEvent(121, SoundEvent.HAGGLE);
        this.addSoundEvent(122, SoundEvent.HAGGLE_YES);
        this.addSoundEvent(123, SoundEvent.HAGGLE_NO);
        this.addSoundEvent(124, SoundEvent.HAGGLE_IDLE);
        this.addSoundEvent(125, SoundEvent.CHORUS_GROW);
        this.addSoundEvent(126, SoundEvent.CHORUS_DEATH);
        this.addSoundEvent(127, SoundEvent.GLASS);
        this.addSoundEvent(128, SoundEvent.POTION_BREWED);
        this.addSoundEvent(129, SoundEvent.CAST_SPELL);
        this.addSoundEvent(130, SoundEvent.PREPARE_ATTACK);
        this.addSoundEvent(131, SoundEvent.PREPARE_SUMMON);
        this.addSoundEvent(132, SoundEvent.PREPARE_WOLOLO);
        this.addSoundEvent(133, SoundEvent.FANG);
        this.addSoundEvent(134, SoundEvent.CHARGE);
        this.addSoundEvent(135, SoundEvent.CAMERA_TAKE_PICTURE);
        this.addSoundEvent(136, SoundEvent.LEASHKNOT_PLACE);
        this.addSoundEvent(137, SoundEvent.LEASHKNOT_BREAK);
        this.addSoundEvent(138, SoundEvent.GROWL);
        this.addSoundEvent(139, SoundEvent.WHINE);
        this.addSoundEvent(140, SoundEvent.PANT);
        this.addSoundEvent(141, SoundEvent.PURR);
        this.addSoundEvent(142, SoundEvent.PURREOW);
        this.addSoundEvent(143, SoundEvent.DEATH_MIN_VOLUME);
        this.addSoundEvent(144, SoundEvent.DEATH_MID_VOLUME);
        this.addSoundEvent(145, SoundEvent.IMITATE_BLAZE);
        this.addSoundEvent(146, SoundEvent.IMITATE_CAVE_SPIDER);
        this.addSoundEvent(147, SoundEvent.IMITATE_CREEPER);
        this.addSoundEvent(148, SoundEvent.IMITATE_ELDER_GUARDIAN);
        this.addSoundEvent(149, SoundEvent.IMITATE_ENDER_DRAGON);
        this.addSoundEvent(150, SoundEvent.IMITATE_ENDERMAN);
        this.addSoundEvent(152, SoundEvent.IMITATE_EVOCATION_ILLAGER);
        this.addSoundEvent(153, SoundEvent.IMITATE_GHAST);
        this.addSoundEvent(154, SoundEvent.IMITATE_HUSK);
        this.addSoundEvent(155, SoundEvent.IMITATE_ILLUSION_ILLAGER);
        this.addSoundEvent(156, SoundEvent.IMITATE_MAGMA_CUBE);
        this.addSoundEvent(157, SoundEvent.IMITATE_POLAR_BEAR);
        this.addSoundEvent(158, SoundEvent.IMITATE_SHULKER);
        this.addSoundEvent(159, SoundEvent.IMITATE_SILVERFISH);
        this.addSoundEvent(160, SoundEvent.IMITATE_SKELETON);
        this.addSoundEvent(161, SoundEvent.IMITATE_SLIME);
        this.addSoundEvent(162, SoundEvent.IMITATE_SPIDER);
        this.addSoundEvent(163, SoundEvent.IMITATE_STRAY);
        this.addSoundEvent(164, SoundEvent.IMITATE_VEX);
        this.addSoundEvent(165, SoundEvent.IMITATE_VINDICATION_ILLAGER);
        this.addSoundEvent(166, SoundEvent.IMITATE_WITCH);
        this.addSoundEvent(167, SoundEvent.IMITATE_WITHER);
        this.addSoundEvent(168, SoundEvent.IMITATE_WITHER_SKELETON);
        this.addSoundEvent(169, SoundEvent.IMITATE_WOLF);
        this.addSoundEvent(170, SoundEvent.IMITATE_ZOMBIE);
        this.addSoundEvent(171, SoundEvent.IMITATE_ZOMBIE_PIGMAN);
        this.addSoundEvent(172, SoundEvent.IMITATE_ZOMBIE_VILLAGER);
        this.addSoundEvent(173, SoundEvent.BLOCK_END_PORTAL_FRAME_FILL);
        this.addSoundEvent(174, SoundEvent.BLOCK_END_PORTAL_SPAWN);
        this.addSoundEvent(175, SoundEvent.RANDOM_ANVIL_USE);
        this.addSoundEvent(176, SoundEvent.BOTTLE_DRAGONBREATH);
        this.addSoundEvent(177, SoundEvent.PORTAL_TRAVEL);
        this.addSoundEvent(178, SoundEvent.ITEM_TRIDENT_HIT);
        this.addSoundEvent(179, SoundEvent.ITEM_TRIDENT_RETURN);
        this.addSoundEvent(180, SoundEvent.ITEM_TRIDENT_RIPTIDE_1);
        this.addSoundEvent(181, SoundEvent.ITEM_TRIDENT_RIPTIDE_2);
        this.addSoundEvent(182, SoundEvent.ITEM_TRIDENT_RIPTIDE_3);
        this.addSoundEvent(183, SoundEvent.ITEM_TRIDENT_THROW);
        this.addSoundEvent(184, SoundEvent.ITEM_TRIDENT_THUNDER);
        this.addSoundEvent(185, SoundEvent.ITEM_TRIDENT_HIT_GROUND);
        this.addSoundEvent(186, SoundEvent.DEFAULT);
        this.addSoundEvent(188, SoundEvent.ELEMENT_CONSTRUCTOR_OPEN);
        this.addSoundEvent(189, SoundEvent.ICE_BOMB_HIT);
        this.addSoundEvent(190, SoundEvent.BALLOON_POP);
        this.addSoundEvent(191, SoundEvent.LT_REACTION_ICE_BOMB);
        this.addSoundEvent(192, SoundEvent.LT_REACTION_BLEACH);
        this.addSoundEvent(193, SoundEvent.LT_REACTION_E_PASTE);
        this.addSoundEvent(194, SoundEvent.LT_REACTION_E_PASTE2);
        this.addSoundEvent(199, SoundEvent.LT_REACTION_FERTILIZER);
        this.addSoundEvent(200, SoundEvent.LT_REACTION_FIREBALL);
        this.addSoundEvent(201, SoundEvent.LT_REACTION_MG_SALT);
        this.addSoundEvent(202, SoundEvent.LT_REACTION_MISC_FIRE);
        this.addSoundEvent(203, SoundEvent.LT_REACTION_FIRE);
        this.addSoundEvent(204, SoundEvent.LT_REACTION_MISC_EXPLOSION);
        this.addSoundEvent(205, SoundEvent.LT_REACTION_MISC_MYSTICAL);
        this.addSoundEvent(206, SoundEvent.LT_REACTION_MISC_MYSTICAL2);
        this.addSoundEvent(207, SoundEvent.LT_REACTION_PRODUCT);
        this.addSoundEvent(208, SoundEvent.SPARKLER_USE);
        this.addSoundEvent(209, SoundEvent.GLOWSTICK_USE);
        this.addSoundEvent(210, SoundEvent.SPARKLER_ACTIVE);
        this.addSoundEvent(211, SoundEvent.CONVERT_TO_DROWNED);
        this.addSoundEvent(212, SoundEvent.BUCKET_FILL_FISH);
        this.addSoundEvent(213, SoundEvent.BUCKET_EMPTY_FISH);
        this.addSoundEvent(214, SoundEvent.BUBBLE_UP);
        this.addSoundEvent(215, SoundEvent.BUBBLE_DOWN);
        this.addSoundEvent(216, SoundEvent.BUBBLE_POP);
        this.addSoundEvent(217, SoundEvent.BUBBLE_UP_INSIDE);
        this.addSoundEvent(218, SoundEvent.BUBBLE_DOWN_INSIDE);
        this.addSoundEvent(219, SoundEvent.BABY_HURT);
        this.addSoundEvent(220, SoundEvent.BABY_DEATH);
        this.addSoundEvent(221, SoundEvent.BABY_STEP);
        this.addSoundEvent(222, SoundEvent.BABY_SPAWN);
        this.addSoundEvent(223, SoundEvent.BORN);
        this.addSoundEvent(224, SoundEvent.BLOCK_TURTLE_EGG_BREAK);
        this.addSoundEvent(225, SoundEvent.BLOCK_TURTLE_EGG_CRACK);
        this.addSoundEvent(226, SoundEvent.BLOCK_TURTLE_EGG_HATCH);
        this.addSoundEvent(227, SoundEvent.TURTLE_LAY_EGG);
        this.addSoundEvent(228, SoundEvent.BLOCK_TURTLE_EGG_ATTACK);
        this.addSoundEvent(229, SoundEvent.BEACON_ACTIVATE);
        this.addSoundEvent(230, SoundEvent.BEACON_AMBIENT);
        this.addSoundEvent(231, SoundEvent.BEACON_DEACTIVATE);
        this.addSoundEvent(232, SoundEvent.BEACON_POWER);
        this.addSoundEvent(233, SoundEvent.CONDUIT_ACTIVATE);
        this.addSoundEvent(234, SoundEvent.CONDUIT_AMBIENT);
        this.addSoundEvent(235, SoundEvent.CONDUIT_ATTACK);
        this.addSoundEvent(236, SoundEvent.CONDUIT_DEACTIVATE);
        this.addSoundEvent(237, SoundEvent.CONDUIT_SHORT);
        this.addSoundEvent(238, SoundEvent.SWOOP);
        this.addSoundEvent(239, SoundEvent.UNDEFINED);
    }

    @Override
    protected void registerCommandParams() {
        this.addCommandParam(1, CommandParamType.INT);
        this.addCommandParam(2, CommandParamType.FLOAT);
        this.addCommandParam(3, CommandParamType.VALUE);
        this.addCommandParam(4, CommandParamType.WILDCARD_INT);
        this.addCommandParam(5, CommandParamType.OPERATOR);
        this.addCommandParam(6, CommandParamType.TARGET);
        this.addCommandParam(7, CommandParamType.WILDCARD_TARGET);
        this.addCommandParam(14, CommandParamType.FILE_PATH);
        this.addCommandParam(18, CommandParamType.INT_RANGE);
        this.addCommandParam(26, CommandParamType.STRING);
        this.addCommandParam(28, CommandParamType.POSITION);
        this.addCommandParam(31, CommandParamType.MESSAGE);
        this.addCommandParam(33, CommandParamType.TEXT);
        this.addCommandParam(36, CommandParamType.JSON);
        this.addCommandParam(43, CommandParamType.COMMAND);
    }

    @Override
    protected void registerLevelEvents() {
        this.addLevelEvent(0, LevelEventType.UNDEFINED);

        // Sounds
        int sound = 1000;
        this.addLevelEvent(0 + sound, LevelEventType.SOUND_CLICK);
        this.addLevelEvent(1 + sound, LevelEventType.SOUND_CLICK_FAIL);
        this.addLevelEvent(2 + sound, LevelEventType.SOUND_LAUNCH);
        this.addLevelEvent(3 + sound, LevelEventType.SOUND_DOOR_OPEN);
        this.addLevelEvent(4 + sound, LevelEventType.SOUND_FIZZ);
        this.addLevelEvent(5 + sound, LevelEventType.SOUND_FUSE);
        this.addLevelEvent(6 + sound, LevelEventType.SOUND_PLAY_RECORDING);
        this.addLevelEvent(7 + sound, LevelEventType.SOUND_GHAST_WARNING);
        this.addLevelEvent(8 + sound, LevelEventType.SOUND_GHAST_FIREBALL);
        this.addLevelEvent(9 + sound, LevelEventType.SOUND_BLAZE_FIREBALL);
        this.addLevelEvent(10 + sound, LevelEventType.SOUND_ZOMBIE_DOOR_BUMP);

        this.addLevelEvent(12 + sound, LevelEventType.SOUND_ZOMBIE_DOOR_CRASH);

        this.addLevelEvent(16 + sound, LevelEventType.SOUND_ZOMBIE_INFECTED);
        this.addLevelEvent(17 + sound, LevelEventType.SOUND_ZOMBIE_CONVERTED);
        this.addLevelEvent(18 + sound, LevelEventType.SOUND_ENDERMAN_TELEPORT);

        this.addLevelEvent(20 + sound, LevelEventType.SOUND_ANVIL_BROKEN);
        this.addLevelEvent(21 + sound, LevelEventType.SOUND_ANVIL_USED);
        this.addLevelEvent(22 + sound, LevelEventType.SOUND_ANVIL_LAND);

        this.addLevelEvent(30 + sound, LevelEventType.SOUND_INFINITY_ARROW_PICKUP);

        this.addLevelEvent(32 + sound, LevelEventType.SOUND_TELEPORT_ENDERPEARL);

        this.addLevelEvent(40 + sound, LevelEventType.SOUND_ITEMFRAME_ITEM_ADD);
        this.addLevelEvent(41 + sound, LevelEventType.SOUND_ITEMFRAME_BREAK);
        this.addLevelEvent(42 + sound, LevelEventType.SOUND_ITEMFRAME_PLACE);
        this.addLevelEvent(43 + sound, LevelEventType.SOUND_ITEMFRAME_ITEM_REMOVE);
        this.addLevelEvent(44 + sound, LevelEventType.SOUND_ITEMFRAME_ITEM_ROTATE);

        this.addLevelEvent(51 + sound, LevelEventType.SOUND_EXPERIENCE_ORB_PICKUP);
        this.addLevelEvent(52 + sound, LevelEventType.SOUND_TOTEM_USED);

        this.addLevelEvent(60 + sound, LevelEventType.SOUND_ARMOR_STAND_BREAK);
        this.addLevelEvent(61 + sound, LevelEventType.SOUND_ARMOR_STAND_HIT);
        this.addLevelEvent(62 + sound, LevelEventType.SOUND_ARMOR_STAND_LAND);
        this.addLevelEvent(63 + sound, LevelEventType.SOUND_ARMOR_STAND_PLACE);

        // Particles
        int particle = 2000;
        this.addLevelEvent(0 + particle, LevelEventType.PARTICLE_SHOOT);
        this.addLevelEvent(1 + particle, LevelEventType.PARTICLE_DESTROY_BLOCK);
        this.addLevelEvent(2 + particle, LevelEventType.PARTICLE_POTION_SPLASH);
        this.addLevelEvent(3 + particle, LevelEventType.PARTICLE_EYE_OF_ENDER_DEATH);
        this.addLevelEvent(4 + particle, LevelEventType.PARTICLE_MOB_BLOCK_SPAWN);
        this.addLevelEvent(5 + particle, LevelEventType.PARTICLE_CROP_GROWTH);
        this.addLevelEvent(6 + particle, LevelEventType.PARTICLE_SOUND_GUARDIAN_GHOST);
        this.addLevelEvent(7 + particle, LevelEventType.PARTICLE_DEATH_SMOKE);
        this.addLevelEvent(8 + particle, LevelEventType.PARTICLE_DENY_BLOCK);
        this.addLevelEvent(9 + particle, LevelEventType.PARTICLE_GENERIC_SPAWN);
        this.addLevelEvent(10 + particle, LevelEventType.PARTICLE_DRAGON_EGG);
        this.addLevelEvent(11 + particle, LevelEventType.PARTICLE_CROP_EATEN);
        this.addLevelEvent(12 + particle, LevelEventType.PARTICLE_CRIT);
        this.addLevelEvent(13 + particle, LevelEventType.PARTICLE_TELEPORT);
        this.addLevelEvent(14 + particle, LevelEventType.PARTICLE_CRACK_BLOCK);
        this.addLevelEvent(15 + particle, LevelEventType.PARTICLE_BUBBLES);
        this.addLevelEvent(16 + particle, LevelEventType.PARTICLE_EVAPORATE);
        this.addLevelEvent(17 + particle, LevelEventType.PARTICLE_DESTROY_ARMOR_STAND);
        this.addLevelEvent(18 + particle, LevelEventType.PARTICLE_BREAKING_EGG);
        this.addLevelEvent(19 + particle, LevelEventType.PARTICLE_DESTROY_EGG);
        this.addLevelEvent(20 + particle, LevelEventType.PARTICLE_EVAPORATE_WATER);
        this.addLevelEvent(21 + particle, LevelEventType.PARTICLE_DESTROY_BLOCK_NO_SOUND);

        // World
        int world = 3000;
        this.addLevelEvent(1 + world, LevelEventType.START_RAINING);
        this.addLevelEvent(2 + world, LevelEventType.START_THUNDERSTORM);
        this.addLevelEvent(3 + world, LevelEventType.STOP_RAINING);
        this.addLevelEvent(4 + world, LevelEventType.STOP_THUNDERSTORM);
        this.addLevelEvent(5 + world, LevelEventType.GLOBAL_PAUSE);
        this.addLevelEvent(6 + world, LevelEventType.SIM_TIME_STEP);
        this.addLevelEvent(7 + world, LevelEventType.SIM_TIME_SCALE);

        // Blocks
        int block = 3500;
        this.addLevelEvent(0 + block, LevelEventType.ACTIVATE_BLOCK);
        this.addLevelEvent(1 + block, LevelEventType.CAULDRON_EXPLODE);
        this.addLevelEvent(2 + block, LevelEventType.CAULDRON_DYE_ARMOR);
        this.addLevelEvent(3 + block, LevelEventType.CAULDRON_CLEAN_ARMOR);
        this.addLevelEvent(4 + block, LevelEventType.CAULDRON_FILL_POTION);
        this.addLevelEvent(5 + block, LevelEventType.CAULDRON_TAKE_POTION);
        this.addLevelEvent(6 + block, LevelEventType.CAULDRON_FILL_WATER);
        this.addLevelEvent(7 + block, LevelEventType.CAULDRON_TAKE_WATER);
        this.addLevelEvent(8 + block, LevelEventType.CAULDRON_ADD_DYE);
        this.addLevelEvent(9 + block, LevelEventType.CAULDRON_CLEAN_BANNER);
        this.addLevelEvent(10 + block, LevelEventType.CAULDRON_FLUSH);

        // Legacy particles
        int legacy = 0x4000;
        this.addLevelEvent(1 + legacy, LevelEventType.PARTICLE_BUBBLE);
        this.addLevelEvent(2 + legacy, LevelEventType.PARTICLE_CRITICAL);
        this.addLevelEvent(3 + legacy, LevelEventType.PARTICLE_BLOCK_FORCE_FIELD);
        this.addLevelEvent(4 + legacy, LevelEventType.PARTICLE_SMOKE);
        this.addLevelEvent(5 + legacy, LevelEventType.PARTICLE_EXPLODE);
        this.addLevelEvent(6 + legacy, LevelEventType.PARTICLE_EVAPORATION);
        this.addLevelEvent(7 + legacy, LevelEventType.PARTICLE_FLAME);
        this.addLevelEvent(8 + legacy, LevelEventType.PARTICLE_LAVA);
        this.addLevelEvent(9 + legacy, LevelEventType.PARTICLE_LARGE_SMOKE);
        this.addLevelEvent(10 + legacy, LevelEventType.PARTICLE_REDSTONE);
        this.addLevelEvent(11 + legacy, LevelEventType.PARTICLE_RISING_RED_DUST);
        this.addLevelEvent(12 + legacy, LevelEventType.PARTICLE_ITEM_BREAK);
        this.addLevelEvent(13 + legacy, LevelEventType.PARTICLE_SNOWBALL_POOF);
        this.addLevelEvent(14 + legacy, LevelEventType.PARTICLE_HUGE_EXPLODE);
        this.addLevelEvent(15 + legacy, LevelEventType.PARTICLE_HUGE_EXPLODE_SEED);
        this.addLevelEvent(16 + legacy, LevelEventType.PARTICLE_MOB_FLAME);
        this.addLevelEvent(17 + legacy, LevelEventType.PARTICLE_HEART);
        this.addLevelEvent(18 + legacy, LevelEventType.PARTICLE_TERRAIN);
        this.addLevelEvent(19 + legacy, LevelEventType.PARTICLE_TOWN_AURA);
        this.addLevelEvent(20 + legacy, LevelEventType.PARTICLE_PORTAL);
        this.addLevelEvent(21 + legacy, LevelEventType.PARTICLE_SPLASH);
        this.addLevelEvent(22 + legacy, LevelEventType.PARTICLE_WATER_WAKE);
        this.addLevelEvent(23 + legacy, LevelEventType.PARTICLE_DRIP_WATER);
        this.addLevelEvent(24 + legacy, LevelEventType.PARTICLE_DRIP_LAVA);
        this.addLevelEvent(25 + legacy, LevelEventType.PARTICLE_FALLING_DUST);
        this.addLevelEvent(26 + legacy, LevelEventType.PARTICLE_MOB_SPELL);
        this.addLevelEvent(27 + legacy, LevelEventType.PARTICLE_MOB_SPELL_AMBIENT);
        this.addLevelEvent(28 + legacy, LevelEventType.PARTICLE_MOB_SPELL_INSTANTANEOUS);
        this.addLevelEvent(29 + legacy, LevelEventType.PARTICLE_INK);
        this.addLevelEvent(30 + legacy, LevelEventType.PARTICLE_SLIME);
        this.addLevelEvent(31 + legacy, LevelEventType.PARTICLE_RAIN_SPLASH);
        this.addLevelEvent(32 + legacy, LevelEventType.PARTICLE_VILLAGER_ANGRY);
        this.addLevelEvent(33 + legacy, LevelEventType.PARTICLE_VILLAGER_HAPPY);
        this.addLevelEvent(34 + legacy, LevelEventType.PARTICLE_ENCHANTMENT_TABLE);
        this.addLevelEvent(35 + legacy, LevelEventType.PARTICLE_TRACKING_EMITTER);
        this.addLevelEvent(36 + legacy, LevelEventType.PARTICLE_NOTE);
        this.addLevelEvent(37 + legacy, LevelEventType.PARTICLE_WITCH_SPELL);
        this.addLevelEvent(38 + legacy, LevelEventType.PARTICLE_CARROT);
        //39 unknown
        this.addLevelEvent(40 + legacy, LevelEventType.PARTICLE_END_ROD);
        this.addLevelEvent(41 + legacy, LevelEventType.PARTICLE_DRAGONS_BREATH);
    }

    @Override
    public EntityLinkData readEntityLink(ByteBuf buffer) {
        Preconditions.checkNotNull(buffer, "buffer");

        long from = VarInts.readLong(buffer);
        long to = VarInts.readLong(buffer);
        int type = buffer.readUnsignedByte();
        boolean immediate = buffer.readBoolean();

        return new EntityLinkData(from, to, EntityLinkData.Type.values()[type], immediate);
    }

    @Override
    public void writeEntityLink(ByteBuf buffer, EntityLinkData entityLink) {
        Preconditions.checkNotNull(buffer, "buffer");
        Preconditions.checkNotNull(entityLink, "entityLink");

        VarInts.writeLong(buffer, entityLink.getFrom());
        VarInts.writeLong(buffer, entityLink.getTo());
        buffer.writeByte(entityLink.getType().ordinal());
        buffer.writeBoolean(entityLink.isImmediate());
    }

    @Override
    public ItemInstance readItemInstance(ByteBuf buffer) {
        return ItemInstance.of(readItem(buffer));
    }

    @Override
    public void writeItemInstance(ByteBuf buffer, ItemInstance itemInstance) {
        writeItem(buffer, itemInstance.getItem());
    }

    @Override
    public ItemData readItem(ByteBuf buffer) {
        Preconditions.checkNotNull(buffer, "buffer");

        int id = VarInts.readInt(buffer);
        if (id == 0) {
            // We don't need to read anything extra.
            return ItemData.AIR;
        }
        int aux = VarInts.readInt(buffer);
        short damage = (short) (aux >> 8);
        if (damage == Short.MAX_VALUE) damage = -1;
        int count = aux & 0xff;
        short nbtSize = buffer.readShortLE();

        CompoundTag compoundTag = null;
        if (nbtSize > 0) {
            try (NBTInputStream reader = NbtUtils.createReaderLE(new ByteBufInputStream(buffer.readSlice(nbtSize)))) {
                Tag<?> tag = reader.readTag();
                if (tag instanceof CompoundTag) {
                    compoundTag = (CompoundTag) tag;
                }
            } catch (IOException e) {
                throw new IllegalStateException("Unable to load NBT data", e);
            }
        }

        String[] canPlace = readArray(buffer, new String[0], this::readString);
        String[] canBreak = readArray(buffer, new String[0], this::readString);

        return ItemData.of(id, damage, count, compoundTag, canPlace, canBreak);
    }

    @Override
    public void writeItem(ByteBuf buffer, ItemData item) {
        Preconditions.checkNotNull(buffer, "buffer");
        Preconditions.checkNotNull(item, "item");

        // Write id
        int id = item.getId();
        if (id == 0) {
            // We don't need to write anything extra.
            buffer.writeByte(0);
            return;
        }
        VarInts.writeInt(buffer, id);

        // Write damage and count
        short damage = item.getDamage();
        if (damage == -1) damage = Short.MAX_VALUE;
        VarInts.writeInt(buffer, (damage << 8) | (item.getCount() & 0xff));

        // Remember this position, since we'll be writing the true NBT size here later:
        int sizeIndex = buffer.writerIndex();
        buffer.writeShortLE(0);

        if (item.getTag() != null) {
            int afterSizeIndex = buffer.writerIndex();
            try (NBTOutputStream stream = new NBTOutputStream(new LittleEndianByteBufOutputStream(buffer))) {
                stream.write(item.getTag());
            } catch (IOException e) {
                // This shouldn't happen (as this is backed by a Netty ByteBuf), but okay...
                throw new IllegalStateException("Unable to save NBT data", e);
            }
            // Set to the written NBT size
            buffer.setShortLE(sizeIndex, buffer.writerIndex() - afterSizeIndex);
        }

        writeArray(buffer, item.getCanPlace(), this::writeString);
        writeArray(buffer, item.getCanBreak(), this::writeString);
    }

    @Override
    public CommandOriginData readCommandOrigin(ByteBuf buffer) {
        Preconditions.checkNotNull(buffer, "buffer");
        CommandOriginType origin = CommandOriginType.values()[VarInts.readUnsignedInt(buffer)];
        UUID uuid = readUuid(buffer);
        String requestId = readString(buffer);
        long varLong = -1;
        if (origin == CommandOriginType.DEV_CONSOLE || origin == CommandOriginType.TEST) {
            varLong = VarInts.readLong(buffer);
        }
        return new CommandOriginData(origin, uuid, requestId, varLong);
    }

    @Override
    public void writeCommandOrigin(ByteBuf buffer, CommandOriginData originData) {
        Preconditions.checkNotNull(buffer, "buffer");
        Preconditions.checkNotNull(originData, "commandOriginData");
        VarInts.writeUnsignedInt(buffer, originData.getOrigin().ordinal());
        writeUuid(buffer, originData.getUuid());
        writeString(buffer, originData.getRequestId());
        if (originData.getOrigin() == CommandOriginType.DEV_CONSOLE || originData.getOrigin() == CommandOriginType.TEST) {
            VarInts.writeLong(buffer, originData.getEvent());
        }
    }

    @Override
    public GameRuleData<?> readGameRule(ByteBuf buffer) {
        Preconditions.checkNotNull(buffer, "buffer");

        String name = readString(buffer);
        int type = VarInts.readUnsignedInt(buffer);

        switch (type) {
            case 1:
                return new GameRuleData<>(name, buffer.readBoolean());
            case 2:
                return new GameRuleData<>(name, VarInts.readUnsignedInt(buffer));
            case 3:
                return new GameRuleData<>(name, buffer.readFloatLE());
        }
        throw new IllegalStateException("Invalid gamerule type received");
    }

    @Override
    public void writeGameRule(ByteBuf buffer, GameRuleData<?> gameRule) {
        Preconditions.checkNotNull(buffer, "buffer");
        Preconditions.checkNotNull(gameRule, "gameRule");

        Object value = gameRule.getValue();
        int type = this.gameRuleTypes.getInt(value.getClass());

        writeString(buffer, gameRule.getName());
        VarInts.writeUnsignedInt(buffer, type);
        switch (type) {
            case 1:
                buffer.writeBoolean((boolean) value);
                break;
            case 2:
                VarInts.writeUnsignedInt(buffer, (int) value);
                break;
            case 3:
                buffer.writeFloatLE((float) value);
                break;
        }
    }

    @Override
    public void readEntityData(ByteBuf buffer, EntityDataMap entityDataMap) {
        Preconditions.checkNotNull(buffer, "buffer");
        Preconditions.checkNotNull(entityDataMap, "entityDataDictionary");

        int length = VarInts.readUnsignedInt(buffer);

        for (int i = 0; i < length; i++) {
            int metadataInt = VarInts.readUnsignedInt(buffer);
            EntityData entityData = this.entityData.get(metadataInt);
            EntityData.Type type = this.entityDataTypes.get(VarInts.readUnsignedInt(buffer));
            if (entityData != null && entityData.isFlags()) {
                if (type != Type.LONG) {
                    throw new IllegalArgumentException("Expected long value for flags, got " + type.name());
                }
                type = Type.FLAGS;
            }

            Object object;
            switch (type) {
                case BYTE:
                    object = buffer.readByte();
                    break;
                case SHORT:
                    object = buffer.readShortLE();
                    break;
                case INT:
                    object = VarInts.readInt(buffer);
                    break;
                case FLOAT:
                    object = buffer.readFloatLE();
                    break;
                case STRING:
                    object = readString(buffer);
                    break;
                case NBT:
                    object = this.readItem(buffer);
                    break;
                case VECTOR3I:
                    object = readVector3i(buffer);
                    break;
                case FLAGS:
                    int index = entityData == EntityData.FLAGS_2 ? 1 : 0;
                    entityDataMap.getOrCreateFlags().set(VarInts.readLong(buffer), index, this.entityFlags);
                    continue;
                case LONG:
                    object = VarInts.readLong(buffer);
                    break;
                case VECTOR3F:
                    object = readVector3f(buffer);
                    break;
                default:
                    throw new IllegalArgumentException("Unknown entity data type received");
            }
            if (entityData != null) {
                entityDataMap.put(entityData, object);
            } else {
                log.debug("Unknown entity data: {} type {} value {}", metadataInt, type, object);
            }
        }
    }

    @Override
    public void writeEntityData(ByteBuf buffer, EntityDataMap entityDataMap) {
        Preconditions.checkNotNull(buffer, "buffer");
        Preconditions.checkNotNull(entityDataMap, "entityDataDictionary");

        VarInts.writeUnsignedInt(buffer, entityDataMap.size());

        for (Map.Entry<EntityData, Object> entry : entityDataMap.entrySet()) {
            int index = buffer.writerIndex();
            VarInts.writeUnsignedInt(buffer, this.entityData.get(entry.getKey()));
            Object object = entry.getValue();
            EntityData.Type type = EntityData.Type.from(object);
            VarInts.writeUnsignedInt(buffer, this.entityDataTypes.get(type));

            switch (type) {
                case BYTE:
                    buffer.writeByte((byte) object);
                    break;
                case SHORT:
                    buffer.writeShortLE((short) object);
                    break;
                case INT:
                    VarInts.writeInt(buffer, (int) object);
                    break;
                case FLOAT:
                    buffer.writeFloatLE((float) object);
                    break;
                case STRING:
                    writeString(buffer, (String) object);
                    break;
                case NBT:
                    ItemData item;
                    if (object instanceof CompoundTag) {
                        item = ItemData.of(1, (short) 0, 1, (CompoundTag) object);
                    } else {
                        item = (ItemData) object;
                    }
                    this.writeItem(buffer, item);
                    break;
                case VECTOR3I:
                    writeVector3i(buffer, (Vector3i) object);
                    break;
                case FLAGS:
                    int flagsIndex = entry.getKey() == EntityData.FLAGS_2 ? 1 : 0;
                    object = ((EntityFlags) object).get(flagsIndex, this.entityFlags);
                case LONG:
                    VarInts.writeLong(buffer, (long) object);
                    break;
                case VECTOR3F:
                    writeVector3f(buffer, (Vector3f) object);
                    break;
                default:
                    buffer.writerIndex(index);
                    break;
            }
        }
    }

    @Override
    public CommandEnumData readCommandEnum(ByteBuf buffer, boolean soft) {
        Preconditions.checkNotNull(buffer, "buffer");

        String name = readString(buffer);

        String[] values = new String[VarInts.readUnsignedInt(buffer)];
        for (int i = 0; i < values.length; i++) {
            values[i] = readString(buffer);
        }
        return new CommandEnumData(name, values, soft);
    }

    @Override
    public void writeCommandEnum(ByteBuf buffer, CommandEnumData enumData) {
        Preconditions.checkNotNull(buffer, "buffer");
        Preconditions.checkNotNull(enumData, "enumData");

        writeString(buffer, enumData.getName());

        String[] values = enumData.getValues();
        VarInts.writeUnsignedInt(buffer, values.length);
        for (String value : values) {
            writeString(buffer, value);
        }
    }

    @Override
    public StructureSettings readStructureSettings(ByteBuf buffer) {
        return null;
    }

    @Override
    public void writeStructureSettings(ByteBuf buffer, StructureSettings settings) {

    }

    @Override
    public SerializedSkin readSkin(ByteBuf buffer) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void writeSkin(ByteBuf buffer, SerializedSkin skin) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ImageData readImage(ByteBuf buffer) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void writeImage(ByteBuf buffer, ImageData image) {
        throw new UnsupportedOperationException();
    }
}
