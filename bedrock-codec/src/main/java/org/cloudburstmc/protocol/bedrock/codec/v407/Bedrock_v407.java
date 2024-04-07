package org.cloudburstmc.protocol.bedrock.codec.v407;

import org.cloudburstmc.protocol.bedrock.codec.BedrockCodec;
import org.cloudburstmc.protocol.bedrock.codec.EntityDataTypeMap;
import org.cloudburstmc.protocol.bedrock.codec.v291.serializer.EntityEventSerializer_v291;
import org.cloudburstmc.protocol.bedrock.codec.v291.serializer.LevelEventSerializer_v291;
import org.cloudburstmc.protocol.bedrock.codec.v291.serializer.LevelSoundEvent1Serializer_v291;
import org.cloudburstmc.protocol.bedrock.codec.v313.serializer.LevelSoundEvent2Serializer_v313;
import org.cloudburstmc.protocol.bedrock.codec.v332.serializer.LevelSoundEventSerializer_v332;
import org.cloudburstmc.protocol.bedrock.codec.v361.serializer.LevelEventGenericSerializer_v361;
import org.cloudburstmc.protocol.bedrock.codec.v390.Bedrock_v390;
import org.cloudburstmc.protocol.bedrock.codec.v407.serializer.*;
import org.cloudburstmc.protocol.bedrock.data.*;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityDataFormat;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityDataTypes;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityEventType;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityFlag;
import org.cloudburstmc.protocol.bedrock.data.inventory.ContainerSlotType;
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.action.ItemStackRequestActionType;
import org.cloudburstmc.protocol.bedrock.packet.*;
import org.cloudburstmc.protocol.bedrock.transformer.BooleanTransformer;
import org.cloudburstmc.protocol.bedrock.transformer.FlagTransformer;
import org.cloudburstmc.protocol.bedrock.transformer.TypeMapTransformer;
import org.cloudburstmc.protocol.common.util.TypeMap;

public class Bedrock_v407 extends Bedrock_v390 {

    protected static final TypeMap<EntityFlag> ENTITY_FLAGS = Bedrock_v390.ENTITY_FLAGS.toBuilder()
            .shift(86, 1)
            .insert(86, EntityFlag.IS_AVOIDING_BLOCK)
            .shift(93, 2)
            .insert(93, EntityFlag.ADMIRING)
            .insert(94, EntityFlag.CELEBRATING_SPECIAL)
            .build();

    protected static final TypeMap<ParticleType> PARTICLE_TYPES = Bedrock_v390.PARTICLE_TYPES.toBuilder()
            .insert(68, ParticleType.BLUE_FLAME)
            .insert(69, ParticleType.SOUL)
            .insert(70, ParticleType.OBSIDIAN_TEAR)
            .build();

    protected static final EntityDataTypeMap ENTITY_DATA = Bedrock_v390.ENTITY_DATA.toBuilder()
            .update(EntityDataTypes.FLAGS, new FlagTransformer(ENTITY_FLAGS, 0))
            .update(EntityDataTypes.FLAGS_2, new FlagTransformer(ENTITY_FLAGS, 1))
            .update(EntityDataTypes.AREA_EFFECT_CLOUD_PARTICLE, new TypeMapTransformer<>(PARTICLE_TYPES))
            .insert(EntityDataTypes.LOW_TIER_CURED_TRADE_DISCOUNT, 113, EntityDataFormat.INT)
            .insert(EntityDataTypes.HIGH_TIER_CURED_TRADE_DISCOUNT, 114, EntityDataFormat.INT)
            .insert(EntityDataTypes.NEARBY_CURED_TRADE_DISCOUNT, 115, EntityDataFormat.INT)
            .insert(EntityDataTypes.NEARBY_CURED_DISCOUNT_TIME_STAMP, 116, EntityDataFormat.INT)
            .insert(EntityDataTypes.HITBOX, 117, EntityDataFormat.NBT)
            .insert(EntityDataTypes.IS_BUOYANT, 118, EntityDataFormat.BYTE, BooleanTransformer.INSTANCE)
            .insert(EntityDataTypes.BUOYANCY_DATA, 119, EntityDataFormat.STRING)
            .build();

    protected static final TypeMap<EntityEventType> ENTITY_EVENTS = Bedrock_v390.ENTITY_EVENTS.toBuilder()
            .insert(75, EntityEventType.LANDED_ON_GROUND)
            .build();

    protected static final TypeMap<LevelEventType> LEVEL_EVENTS = Bedrock_v390.LEVEL_EVENTS.toBuilder()
            .insert(LEVEL_EVENT_SOUND + 50, LevelEvent.SOUND_CAMERA)
            .insert(LEVEL_EVENT_BLOCK + 100, LevelEvent.BLOCK_START_BREAK)
            .insert(LEVEL_EVENT_BLOCK + 101, LevelEvent.BLOCK_STOP_BREAK)
            .insert(LEVEL_EVENT_BLOCK + 102, LevelEvent.BLOCK_UPDATE_BREAK)
            .insert(4000, LevelEvent.SET_DATA)
            .insert(9800, LevelEvent.ALL_PLAYERS_SLEEPING)
            .insert(LEVEL_EVENT_PARTICLE_TYPE, PARTICLE_TYPES)
            .build();

    protected static final TypeMap<SoundEvent> SOUND_EVENTS = Bedrock_v390.SOUND_EVENTS.toBuilder()
            .replace(287, SoundEvent.JUMP_PREVENT)
            .insert(288, SoundEvent.AMBIENT_POLLINATE)
            .insert(289, SoundEvent.BEEHIVE_DRIP)
            .insert(290, SoundEvent.BEEHIVE_ENTER)
            .insert(291, SoundEvent.BEEHIVE_EXIT)
            .insert(292, SoundEvent.BEEHIVE_WORK)
            .insert(293, SoundEvent.BEEHIVE_SHEAR)
            .insert(294, SoundEvent.HONEYBOTTLE_DRINK)
            .insert(295, SoundEvent.AMBIENT_CAVE)
            .insert(296, SoundEvent.RETREAT)
            .insert(297, SoundEvent.CONVERT_TO_ZOMBIFIED)
            .insert(298, SoundEvent.ADMIRE)
            .insert(299, SoundEvent.STEP_LAVA)
            .insert(300, SoundEvent.TEMPT)
            .insert(301, SoundEvent.PANIC)
            .insert(302, SoundEvent.ANGRY)
            .insert(303, SoundEvent.AMBIENT_WARPED_FOREST)
            .insert(304, SoundEvent.AMBIENT_SOULSAND_VALLEY)
            .insert(305, SoundEvent.AMBIENT_NETHER_WASTES)
            .insert(306, SoundEvent.AMBIENT_BASALT_DELTAS)
            .insert(307, SoundEvent.AMBIENT_CRIMSON_FOREST)
            .insert(308, SoundEvent.RESPAWN_ANCHOR_CHARGE)
            .insert(309, SoundEvent.RESPAWN_ANCHOR_DEPLETE)
            .insert(310, SoundEvent.RESPAWN_ANCHOR_SET_SPAWN)
            .insert(311, SoundEvent.RESPAWN_ANCHOR_AMBIENT)
            .insert(312, SoundEvent.SOUL_ESCAPE_QUIET)
            .insert(313, SoundEvent.SOUL_ESCAPE_LOUD)
            .insert(314, SoundEvent.RECORD_PIGSTEP)
            .insert(315, SoundEvent.LINK_COMPASS_TO_LODESTONE)
            .insert(316, SoundEvent.USE_SMITHING_TABLE)
            .insert(317, SoundEvent.UNDEFINED)
            .build();

    protected static final TypeMap<ItemStackRequestActionType> ITEM_STACK_REQUEST_TYPES = TypeMap.builder(ItemStackRequestActionType.class)
            .insert(0, ItemStackRequestActionType.TAKE)
            .insert(1, ItemStackRequestActionType.PLACE)
            .insert(2, ItemStackRequestActionType.SWAP)
            .insert(3, ItemStackRequestActionType.DROP)
            .insert(4, ItemStackRequestActionType.DESTROY)
            .insert(5, ItemStackRequestActionType.CONSUME)
            .insert(6, ItemStackRequestActionType.CREATE)
            .insert(7, ItemStackRequestActionType.LAB_TABLE_COMBINE)
            .insert(8, ItemStackRequestActionType.BEACON_PAYMENT)
            .insert(9, ItemStackRequestActionType.CRAFT_RECIPE)
            .insert(10, ItemStackRequestActionType.CRAFT_RECIPE_AUTO)
            .insert(11, ItemStackRequestActionType.CRAFT_CREATIVE)
            .insert(12, ItemStackRequestActionType.CRAFT_NON_IMPLEMENTED_DEPRECATED)
            .insert(13, ItemStackRequestActionType.CRAFT_RESULTS_DEPRECATED)
            .build();

    protected static final TypeMap<ContainerSlotType> CONTAINER_SLOT_TYPES = TypeMap.builder(ContainerSlotType.class)
            .insert(0, ContainerSlotType.ANVIL_INPUT)
            .insert(1, ContainerSlotType.ANVIL_MATERIAL)
            .insert(2, ContainerSlotType.ANVIL_RESULT)
            .insert(3, ContainerSlotType.SMITHING_TABLE_INPUT)
            .insert(4, ContainerSlotType.SMITHING_TABLE_MATERIAL)
            .insert(5, ContainerSlotType.SMITHING_TABLE_RESULT)
            .insert(6, ContainerSlotType.ARMOR)
            .insert(7, ContainerSlotType.LEVEL_ENTITY)
            .insert(8, ContainerSlotType.BEACON_PAYMENT)
            .insert(9, ContainerSlotType.BREWING_INPUT)
            .insert(10, ContainerSlotType.BREWING_RESULT)
            .insert(11, ContainerSlotType.BREWING_FUEL)
            .insert(12, ContainerSlotType.HOTBAR_AND_INVENTORY)
            .insert(13, ContainerSlotType.CRAFTING_INPUT)
            .insert(14, ContainerSlotType.CRAFTING_OUTPUT)
            .insert(15, ContainerSlotType.RECIPE_CONSTRUCTION)
            .insert(16, ContainerSlotType.RECIPE_NATURE)
            .insert(17, ContainerSlotType.RECIPE_ITEMS)
            .insert(18, ContainerSlotType.RECIPE_SEARCH)
            .insert(19, ContainerSlotType.RECIPE_SEARCH_BAR)
            .insert(20, ContainerSlotType.RECIPE_EQUIPMENT)
            .insert(21, ContainerSlotType.ENCHANTING_INPUT)
            .insert(22, ContainerSlotType.ENCHANTING_MATERIAL)
            .insert(23, ContainerSlotType.FURNACE_FUEL)
            .insert(24, ContainerSlotType.FURNACE_INGREDIENT)
            .insert(25, ContainerSlotType.FURNACE_RESULT)
            .insert(26, ContainerSlotType.HORSE_EQUIP)
            .insert(27, ContainerSlotType.HOTBAR)
            .insert(28, ContainerSlotType.INVENTORY)
            .insert(29, ContainerSlotType.SHULKER_BOX)
            .insert(30, ContainerSlotType.TRADE_INGREDIENT_1)
            .insert(31, ContainerSlotType.TRADE_INGREDIENT_2)
            .insert(32, ContainerSlotType.TRADE_RESULT)
            .insert(33, ContainerSlotType.OFFHAND)
            .insert(34, ContainerSlotType.COMPOUND_CREATOR_INPUT)
            .insert(35, ContainerSlotType.COMPOUND_CREATOR_OUTPUT)
            .insert(36, ContainerSlotType.ELEMENT_CONSTRUCTOR_OUTPUT)
            .insert(37, ContainerSlotType.MATERIAL_REDUCER_INPUT)
            .insert(38, ContainerSlotType.MATERIAL_REDUCER_OUTPUT)
            .insert(39, ContainerSlotType.LAB_TABLE_INPUT)
            .insert(40, ContainerSlotType.LOOM_INPUT)
            .insert(41, ContainerSlotType.LOOM_DYE)
            .insert(42, ContainerSlotType.LOOM_MATERIAL)
            .insert(43, ContainerSlotType.LOOM_RESULT)
            .insert(44, ContainerSlotType.BLAST_FURNACE_INGREDIENT)
            .insert(45, ContainerSlotType.SMOKER_INGREDIENT)
            .insert(46, ContainerSlotType.TRADE2_INGREDIENT_1)
            .insert(47, ContainerSlotType.TRADE2_INGREDIENT_2)
            .insert(48, ContainerSlotType.TRADE2_RESULT)
            .insert(49, ContainerSlotType.GRINDSTONE_INPUT)
            .insert(50, ContainerSlotType.GRINDSTONE_ADDITIONAL)
            .insert(51, ContainerSlotType.GRINDSTONE_RESULT)
            .insert(52, ContainerSlotType.STONECUTTER_INPUT)
            .insert(53, ContainerSlotType.STONECUTTER_RESULT)
            .insert(54, ContainerSlotType.CARTOGRAPHY_INPUT)
            .insert(55, ContainerSlotType.CARTOGRAPHY_ADDITIONAL)
            .insert(56, ContainerSlotType.CARTOGRAPHY_RESULT)
            .insert(57, ContainerSlotType.BARREL)
            .insert(58, ContainerSlotType.CURSOR)
            .insert(59, ContainerSlotType.CREATED_OUTPUT)
            .build();

    public static BedrockCodec CODEC = Bedrock_v390.CODEC.toBuilder()
            .protocolVersion(407)
            .minecraftVersion("1.16.0")
            .helper(() -> new BedrockCodecHelper_v407(ENTITY_DATA, GAME_RULE_TYPES, ITEM_STACK_REQUEST_TYPES, CONTAINER_SLOT_TYPES))
            .deregisterPacket(VideoStreamConnectPacket.class)
            .updateSerializer(StartGamePacket.class, StartGameSerializer_v407.INSTANCE)
            .updateSerializer(InventoryTransactionPacket.class, InventoryTransactionSerializer_v407.INSTANCE)
            .updateSerializer(HurtArmorPacket.class, HurtArmorSerializer_v407.INSTANCE)
            .updateSerializer(SetSpawnPositionPacket.class, SetSpawnPositionSerializer_v407.INSTANCE)
            .updateSerializer(InventoryContentPacket.class, InventoryContentSerializer_v407.INSTANCE)
            .updateSerializer(InventorySlotPacket.class, InventorySlotSerializer_v407.INSTANCE)
            .updateSerializer(CraftingDataPacket.class, CraftingDataSerializer_v407.INSTANCE)
            .updateSerializer(LevelSoundEvent1Packet.class, new LevelSoundEvent1Serializer_v291(SOUND_EVENTS))
            .updateSerializer(LevelSoundEvent2Packet.class, new LevelSoundEvent2Serializer_v313(SOUND_EVENTS))
            .updateSerializer(LevelSoundEventPacket.class, new LevelSoundEventSerializer_v332(SOUND_EVENTS))
            .updateSerializer(EntityEventPacket.class, new EntityEventSerializer_v291(ENTITY_EVENTS))
            .updateSerializer(LevelEventPacket.class, new LevelEventSerializer_v291(LEVEL_EVENTS))
            .updateSerializer(LevelEventGenericPacket.class, new LevelEventGenericSerializer_v361(LEVEL_EVENTS))
            .updateSerializer(EducationSettingsPacket.class, EducationSettingsSerializer_v407.INSTANCE)
            .registerPacket(CreativeContentPacket::new, CreativeContentSerializer_v407.INSTANCE, 145, PacketRecipient.CLIENT)
            .registerPacket(PlayerEnchantOptionsPacket::new, PlayerEnchantOptionsSerializer_v407.INSTANCE, 146, PacketRecipient.CLIENT)
            .registerPacket(ItemStackRequestPacket::new, ItemStackRequestSerializer_v407.INSTANCE, 147, PacketRecipient.SERVER)
            .registerPacket(ItemStackResponsePacket::new, ItemStackResponseSerializer_v407.INSTANCE, 148, PacketRecipient.CLIENT)
            .registerPacket(PlayerArmorDamagePacket::new, PlayerArmorDamageSerializer_v407.INSTANCE, 149, PacketRecipient.CLIENT)
            .registerPacket(CodeBuilderPacket::new, CodeBuilderSerializer_v407.INSTANCE, 150, PacketRecipient.CLIENT)
            .registerPacket(UpdatePlayerGameTypePacket::new, UpdatePlayerGameTypeSerializer_v407.INSTANCE, 151, PacketRecipient.CLIENT)
            .registerPacket(EmoteListPacket::new, EmoteListSerializer_v407.INSTANCE, 152, PacketRecipient.BOTH)
            .registerPacket(PositionTrackingDBServerBroadcastPacket::new, PositionTrackingDBServerBroadcastSerializer_v407.INSTANCE, 153, PacketRecipient.CLIENT)
            .registerPacket(PositionTrackingDBClientRequestPacket::new, PositionTrackingDBClientRequestSerializer_v407.INSTANCE, 154, PacketRecipient.SERVER)
            .registerPacket(DebugInfoPacket::new, DebugInfoSerializer_v407.INSTANCE, 155, PacketRecipient.BOTH)
            .registerPacket(PacketViolationWarningPacket::new, PacketViolationWarningSerializer_v407.INSTANCE, 156, PacketRecipient.SERVER)
            .build();
}
