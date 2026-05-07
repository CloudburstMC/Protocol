package org.cloudburstmc.protocol.bedrock.codec.v975;

import org.cloudburstmc.protocol.bedrock.codec.BedrockCodec;
import org.cloudburstmc.protocol.bedrock.codec.EntityDataTypeMap;
import org.cloudburstmc.protocol.bedrock.codec.v291.serializer.LevelEventSerializer_v291;
import org.cloudburstmc.protocol.bedrock.codec.v361.serializer.LevelEventGenericSerializer_v361;
import org.cloudburstmc.protocol.bedrock.codec.v898.serializer.AvailableCommandsSerializer_v898;
import org.cloudburstmc.protocol.bedrock.codec.v944.Bedrock_v944;
import org.cloudburstmc.protocol.bedrock.codec.v975.serializer.*;
import org.cloudburstmc.protocol.bedrock.data.LevelEventType;
import org.cloudburstmc.protocol.bedrock.data.PacketRecipient;
import org.cloudburstmc.protocol.bedrock.data.ParticleType;
import org.cloudburstmc.protocol.bedrock.data.SoundEvent;
import org.cloudburstmc.protocol.bedrock.data.command.CommandParam;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityDataFormat;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityDataTypes;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityEventType;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityFlag;
import org.cloudburstmc.protocol.bedrock.packet.*;
import org.cloudburstmc.protocol.bedrock.transformer.FlagTransformer;
import org.cloudburstmc.protocol.bedrock.transformer.TypeMapTransformer;
import org.cloudburstmc.protocol.common.util.TypeMap;

public class Bedrock_v975 extends Bedrock_v944 {

    protected static final TypeMap<CommandParam> COMMAND_PARAMS = TypeMap.builder(CommandParam.class)
            .insert(0, CommandParam.UNKNOWN)
            .insert(1, CommandParam.INT)
            .insert(2, CommandParam.FLOAT)
            .insert(3, CommandParam.VALUE)
            .insert(4, CommandParam.R_VALUE)
            .insert(5, CommandParam.WILDCARD_INT)
            .insert(6, CommandParam.OPERATOR)
            .insert(7, CommandParam.COMPARE_OPERATOR)
            .insert(8, CommandParam.TARGET)
            .insert(9, CommandParam.STANDALONE_TARGET)
            .insert(10, CommandParam.WILDCARD_TARGET)
            .insert(11, CommandParam.NON_ID_TARGET)
            .insert(12, CommandParam.SCORE_ARG)
            .insert(13, CommandParam.SCORE_ARGS)
            .insert(14, CommandParam.SCORE_SELECT_PARAM)
            .insert(15, CommandParam.SCORE_SELECTOR)
            .insert(16, CommandParam.TAG_SELECTOR)
            .insert(17, CommandParam.FILE_PATH)
            .insert(18, CommandParam.FILE_PATH_VAL)
            .insert(19, CommandParam.FILE_PATH_CONT)
            .insert(20, CommandParam.INT_RANGE_VAL)
            .insert(21, CommandParam.INT_RANGE_POST_VAL)
            .insert(22, CommandParam.INT_RANGE)
            .insert(23, CommandParam.INT_RANGE_FULL)
            .insert(24, CommandParam.RATIONAL_RANGE_VAL)
            .insert(25, CommandParam.RATIONAL_RANGE_POST_VAL)
            .insert(26, CommandParam.RATIONAL_RANGE)
            .insert(27, CommandParam.RATIONAL_RANGE_FULL)
            .insert(28, CommandParam.SEL_ARGS)
            .insert(29, CommandParam.ARGS)
            .insert(30, CommandParam.ARG)
            .insert(31, CommandParam.MARG)
            .insert(32, CommandParam.MVALUE)
            .insert(33, CommandParam.NAME)
            .insert(34, CommandParam.TYPE)
            .insert(35, CommandParam.FAMILY)
            .insert(36, CommandParam.PERMISSION)
            .insert(37, CommandParam.PERMISSIONS)
            .insert(38, CommandParam.PERMISSION_SELECTOR)
            .insert(39, CommandParam.PERMISSION_ELEMENT)
            .insert(40, CommandParam.PERMISSION_ELEMENTS)
            .insert(41, CommandParam.TAG)
            .insert(42, CommandParam.HAS_ITEM_ELEMENT)
            .insert(43, CommandParam.HAS_ITEM_ELEMENTS)
            .insert(44, CommandParam.HAS_ITEM)
            .insert(45, CommandParam.HAS_ITEMS)
            .insert(46, CommandParam.HAS_ITEM_SELECTOR)
            .insert(47, CommandParam.EQUIPMENT_SLOTS)
            .insert(48, CommandParam.PROPERTY_VALUE)
            .insert(49, CommandParam.HAS_PROPERTY_PARAM_VALUE)
            .insert(50, CommandParam.HAS_PROPERTY_PARAM_ENUM_VALUE)
            .insert(51, CommandParam.HAS_PROPERTY_ARG)
            .insert(52, CommandParam.HAS_PROPERTY_ARGS)
            .insert(53, CommandParam.HAS_PROPERTY_ELEMENT)
            .insert(54, CommandParam.HAS_PROPERTY_ELEMENTS)
            .insert(55, CommandParam.HAS_PROPERTY_SELECTOR)
            .insert(56, CommandParam.STRING)
            .insert(57, CommandParam.ID_CONT)
            .insert(58, CommandParam.COORD_X_INT)
            .insert(59, CommandParam.COORD_Y_INT)
            .insert(60, CommandParam.COORD_Z_INT)
            .insert(61, CommandParam.COORD_X_FLOAT)
            .insert(62, CommandParam.COORD_Y_FLOAT)
            .insert(63, CommandParam.COORD_Z_FLOAT)
            .insert(64, CommandParam.BLOCK_POSITION)
            .insert(65, CommandParam.POSITION)
            .insert(66, CommandParam.MESSAGE_XP)
            .insert(67, CommandParam.MESSAGE)
            .insert(68, CommandParam.MESSAGE_ROOT)
            .insert(69, CommandParam.POST_SELECTOR)
            .insert(70, CommandParam.TEXT)
            .insert(71, CommandParam.TEXT_CONT)
            .insert(72, CommandParam.JSON_VALUE)
            .insert(73, CommandParam.JSON_FIELD)
            .insert(74, CommandParam.JSON)
            .insert(75, CommandParam.JSON_OBJECT_FIELDS)
            .insert(76, CommandParam.JSON_OBJECT_CONT)
            .insert(77, CommandParam.JSON_ARRAY)
            .insert(78, CommandParam.JSON_ARRAY_VALUES)
            .insert(79, CommandParam.JSON_ARRAY_CONT)
            .insert(80, CommandParam.BLOCK_STATE)
            .insert(81, CommandParam.BLOCK_STATE_KEY)
            .insert(82, CommandParam.BLOCK_STATE_VALUE)
            .insert(83, CommandParam.BLOCK_STATE_VALUES)
            .insert(84, CommandParam.BLOCK_STATES)
            .insert(85, CommandParam.BLOCK_STATES_CONT)
            .insert(86, CommandParam.CLOCK_TIME_MARKER_NAME)
            .insert(87, CommandParam.COMMAND)
            .insert(88, CommandParam.SLASH_COMMAND)
            .insert(89, CommandParam.CODE_BUILDER_ARG)
            .insert(90, CommandParam.CODE_BUILDER_ARGS)
            .insert(91, CommandParam.CODE_BUILDER_SELECT_PARAM)
            .insert(92, CommandParam.CODE_BUILDER_SELECTOR)
            .insert(134217728, CommandParam.CHAINED_COMMAND)
            .build();

    protected static final TypeMap<EntityEventType> ENTITY_EVENTS = Bedrock_v944.ENTITY_EVENTS.toBuilder()
            .insert(81, EntityEventType.HURT_WITHOUT_RECEIVING_DAMAGE)
            .build();

    protected static final TypeMap<EntityFlag> ENTITY_FLAGS = Bedrock_v944.ENTITY_FLAGS
            .toBuilder()
            .insert(127, EntityFlag.USES_LEGACY_FRICTION)
            .insert(128, EntityFlag.USES_UNIFORM_AIR_DRAG)
            .insert(129, EntityFlag.NAMEPLATE_DEPTH_TESTED)
            .build();

    protected static final TypeMap<ParticleType> PARTICLE_TYPES = Bedrock_v944.PARTICLE_TYPES.toBuilder()
            .insert(101, ParticleType.SULFUR_CUBE)
            .build();

    protected static final EntityDataTypeMap ENTITY_DATA = Bedrock_v944.ENTITY_DATA
            .toBuilder()
            .update(EntityDataTypes.AREA_EFFECT_CLOUD_PARTICLE, new TypeMapTransformer<>(PARTICLE_TYPES))
            .update(EntityDataTypes.FLAGS, new FlagTransformer(ENTITY_FLAGS, 0))
            .update(EntityDataTypes.FLAGS_2, new FlagTransformer(ENTITY_FLAGS, 1))
            .insert(EntityDataTypes.RESERVED_139, 139, EntityDataFormat.LONG)
            .insert(EntityDataTypes.NAMEPLATE_RENDER_DISTANCE_MAX, 140, EntityDataFormat.FLOAT)
            .build();

    protected static final TypeMap<LevelEventType> LEVEL_EVENTS = Bedrock_v944.LEVEL_EVENTS.toBuilder()
            .insert(LEVEL_EVENT_PARTICLE_TYPE, PARTICLE_TYPES)
            .build();

    protected static final TypeMap<SoundEvent> SOUND_EVENTS = Bedrock_v944.SOUND_EVENTS
            .toBuilder()
            .replace(599, SoundEvent.PUSHED_BY_PLAYER)
            .insert(600, SoundEvent.BOUNCE)
            .insert(601, SoundEvent.UNDEFINED)
            .build();

    public static final BedrockCodec CODEC = Bedrock_v944.CODEC.toBuilder()
            .protocolVersion(975)
            .minecraftVersion("1.26.20")
            .helper(() -> new BedrockCodecHelper_v975(ENTITY_DATA, GAME_RULE_TYPES, ITEM_STACK_REQUEST_TYPES, CONTAINER_SLOT_TYPES, PLAYER_ABILITIES, TEXT_PROCESSING_ORIGINS))
            .updateSerializer(AvailableCommandsPacket.class, new AvailableCommandsSerializer_v898(COMMAND_PARAMS))
            .updateSerializer(BiomeDefinitionListPacket.class, BiomeDefinitionListSerializer_v975.INSTANCE)
            .updateSerializer(ClientboundAttributeLayerSyncPacket.class, ClientboundAttributeLayerSyncSerializer_v975.INSTANCE)
            .updateSerializer(DebugDrawerPacket.class, DebugDrawerSerializer_v975.INSTANCE)
            .updateSerializer(DimensionDataPacket.class, DimensionDataSerializer_v975.INSTANCE)
            .updateSerializer(DisconnectPacket.class, DisconnectSerializer_v975.INSTANCE)
            .updateSerializer(EntityEventPacket.class, new EntityEventSerializer_v975(ENTITY_EVENTS))
            .updateSerializer(InventorySlotPacket.class, InventorySlotSerializer_v975.INSTANCE)
            .updateSerializer(LevelEventPacket.class, new LevelEventSerializer_v291(LEVEL_EVENTS))
            .updateSerializer(LevelEventGenericPacket.class, new LevelEventGenericSerializer_v361(LEVEL_EVENTS))
            .updateSerializer(LevelSoundEventPacket.class, new LevelSoundEventSerializer_v975(SOUND_EVENTS))
            .updateSerializer(LocatorBarPacket.class, LocatorBarSerializer_v975.INSTANCE)
            .updateSerializer(MobEquipmentPacket.class, MobEquipmentSerializer_v975.INSTANCE)
            .updateSerializer(MoveEntityAbsolutePacket.class, MoveEntityAbsoluteSerializer_v975.INSTANCE)
            .updateSerializer(MovementPredictionSyncPacket.class, MovementPredictionSyncSerializer_v975.INSTANCE)
            .updateSerializer(PartyChangedPacket.class, PartyChangedSerializer_v975.INSTANCE)
            .updateSerializer(PlaySoundPacket.class, PlaySoundSerializer_v975.INSTANCE)
            .updateSerializer(PlayerEnchantOptionsPacket.class, PlayerEnchantOptionsSerializer_v975.INSTANCE)
            .updateSerializer(ServerboundDiagnosticsPacket.class, ServerboundDiagnosticsSerializer_v975.INSTANCE)
            .updateSerializer(UpdateClientOptionsPacket.class, UpdateClientOptionsSerializer_v975.INSTANCE)
            .registerPacket(ServerStoreInfoPacket::new, ServerStoreInfoSerializer_v975.INSTANCE, 346, PacketRecipient.CLIENT)
            .registerPacket(ServerPresenceInfoPacket::new, ServerPresenceInfoSerializer_v975.INSTANCE, 347, PacketRecipient.CLIENT)
            .build();
}
