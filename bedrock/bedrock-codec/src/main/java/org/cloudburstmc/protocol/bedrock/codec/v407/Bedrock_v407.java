package org.cloudburstmc.protocol.bedrock.codec.v407;

import org.cloudburstmc.protocol.bedrock.codec.BedrockCodec;
import org.cloudburstmc.protocol.bedrock.codec.v291.serializer.EntityEventSerializer_v291;
import org.cloudburstmc.protocol.bedrock.codec.v291.serializer.LevelEventSerializer_v291;
import org.cloudburstmc.protocol.bedrock.codec.v291.serializer.LevelSoundEvent1Serializer_v291;
import org.cloudburstmc.protocol.bedrock.codec.v313.serializer.LevelSoundEvent2Serializer_v313;
import org.cloudburstmc.protocol.bedrock.codec.v332.serializer.LevelSoundEventSerializer_v332;
import org.cloudburstmc.protocol.bedrock.codec.v390.Bedrock_v390;
import org.cloudburstmc.protocol.bedrock.codec.v407.serializer.*;
import org.cloudburstmc.protocol.bedrock.data.LevelEvent;
import org.cloudburstmc.protocol.bedrock.data.LevelEventType;
import org.cloudburstmc.protocol.bedrock.data.ParticleType;
import org.cloudburstmc.protocol.bedrock.data.SoundEvent;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityData;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityEventType;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityFlag;
import org.cloudburstmc.protocol.bedrock.data.inventory.stackrequestactions.StackRequestActionType;
import org.cloudburstmc.protocol.bedrock.packet.*;
import org.cloudburstmc.protocol.common.util.TypeMap;

public class Bedrock_v407 extends Bedrock_v390 {

    protected static final TypeMap<EntityData> ENTITY_DATA = Bedrock_v390.ENTITY_DATA.toBuilder()
            .insert(113, EntityData.LOW_TIER_CURED_TRADE_DISCOUNT)
            .insert(114, EntityData.HIGH_TIER_CURED_TRADE_DISCOUNT)
            .insert(115, EntityData.NEARBY_CURED_TRADE_DISCOUNT)
            .insert(116, EntityData.NEARBY_CURED_DISCOUNT_TIME_STAMP)
            .insert(117, EntityData.HITBOX)
            .insert(118, EntityData.IS_BUOYANT)
            .insert(119, EntityData.BUOYANCY_DATA)
            .build();

    protected static final TypeMap<EntityFlag> ENTITY_FLAGS = Bedrock_v390.ENTITY_FLAGS.toBuilder()
            .shift(86, 1)
            .insert(86, EntityFlag.IS_AVOIDING_BLOCK)
            .shift(93, 2)
            .insert(93, EntityFlag.ADMIRING)
            .insert(94, EntityFlag.CELEBRATING_SPECIAL)
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
            .insert(LEVEL_EVENT_PARTICLE_TYPE + 68, ParticleType.BLUE_FLAME)
            .insert(LEVEL_EVENT_PARTICLE_TYPE + 69, ParticleType.SOUL)
            .insert(LEVEL_EVENT_PARTICLE_TYPE + 70, ParticleType.OBSIDIAN_TEAR)
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

    protected static final TypeMap<StackRequestActionType> ITEM_STACK_REQUEST_TYPES = TypeMap.builder(StackRequestActionType.class)
            .insert(0, StackRequestActionType.TAKE)
            .insert(1, StackRequestActionType.PLACE)
            .insert(2, StackRequestActionType.SWAP)
            .insert(3, StackRequestActionType.DROP)
            .insert(4, StackRequestActionType.DESTROY)
            .insert(5, StackRequestActionType.CONSUME)
            .insert(6, StackRequestActionType.CREATE)
            .insert(7, StackRequestActionType.LAB_TABLE_COMBINE)
            .insert(8, StackRequestActionType.BEACON_PAYMENT)
            .insert(9, StackRequestActionType.CRAFT_RECIPE)
            .insert(10, StackRequestActionType.CRAFT_RECIPE_AUTO)
            .insert(11, StackRequestActionType.CRAFT_CREATIVE)
            .insert(12, StackRequestActionType.CRAFT_NON_IMPLEMENTED_DEPRECATED)
            .insert(13, StackRequestActionType.CRAFT_RESULTS_DEPRECATED)
            .build();

    public static BedrockCodec CODEC = Bedrock_v390.CODEC.toBuilder()
            .protocolVersion(407)
            .minecraftVersion("1.16.0")
            .helper(() -> new BedrockCodecHelper_v407(ENTITY_DATA, ENTITY_DATA_TYPES, ENTITY_FLAGS, GAME_RULE_TYPES, ITEM_STACK_REQUEST_TYPES))
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
            .updateSerializer(EducationSettingsPacket.class, EducationSettingsSerializer_v407.INSTANCE)
            .registerPacket(CreativeContentPacket.class, CreativeContentSerializer_v407.INSTANCE, 145)
            .registerPacket(PlayerEnchantOptionsPacket.class, PlayerEnchantOptionsSerializer_v407.INSTANCE, 146)
            .registerPacket(ItemStackRequestPacket.class, ItemStackRequestSerializer_v407.INSTANCE, 147)
            .registerPacket(ItemStackResponsePacket.class, ItemStackResponseSerializer_v407.INSTANCE, 148)
            .registerPacket(PlayerArmorDamagePacket.class, PlayerArmorDamageSerializer_v407.INSTANCE, 149)
            .registerPacket(CodeBuilderPacket.class, CodeBuilderSerializer_v407.INSTANCE, 150)
            .registerPacket(UpdatePlayerGameTypePacket.class, UpdatePlayerGameTypeSerializer_v407.INSTANCE, 151)
            .registerPacket(EmoteListPacket.class, EmoteListSerializer_v407.INSTANCE, 152)
            .registerPacket(PositionTrackingDBServerBroadcastPacket.class, PositionTrackingDBServerBroadcastSerializer_v407.INSTANCE, 153)
            .registerPacket(PositionTrackingDBClientRequestPacket.class, PositionTrackingDBClientRequestSerializer_v407.INSTANCE, 154)
            .registerPacket(DebugInfoPacket.class, DebugInfoSerializer_v407.INSTANCE, 155)
            .registerPacket(PacketViolationWarningPacket.class, PacketViolationWarningSerializer_v407.INSTANCE, 156)
            .build();
}
