package org.cloudburstmc.protocol.bedrock.codec.v388;

import org.cloudburstmc.protocol.bedrock.codec.BedrockCodec;
import org.cloudburstmc.protocol.bedrock.codec.v291.serializer.EntityEventSerializer_v291;
import org.cloudburstmc.protocol.bedrock.codec.v291.serializer.LevelSoundEvent1Serializer_v291;
import org.cloudburstmc.protocol.bedrock.codec.v313.serializer.LevelSoundEvent2Serializer_v313;
import org.cloudburstmc.protocol.bedrock.codec.v332.serializer.LevelSoundEventSerializer_v332;
import org.cloudburstmc.protocol.bedrock.codec.v361.Bedrock_v361;
import org.cloudburstmc.protocol.bedrock.codec.v361.serializer.ResourcePackDataInfoSerializer_v361;
import org.cloudburstmc.protocol.bedrock.codec.v388.serializer.*;
import org.cloudburstmc.protocol.bedrock.data.LevelEvent;
import org.cloudburstmc.protocol.bedrock.data.LevelEventType;
import org.cloudburstmc.protocol.bedrock.data.ResourcePackType;
import org.cloudburstmc.protocol.bedrock.data.SoundEvent;
import org.cloudburstmc.protocol.bedrock.data.command.CommandParam;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityData;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityEventType;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityFlag;
import org.cloudburstmc.protocol.bedrock.packet.*;
import org.cloudburstmc.protocol.common.util.TypeMap;

public class Bedrock_v388 extends Bedrock_v361 {

    protected static final TypeMap<EntityData> ENTITY_DATA = Bedrock_v361.ENTITY_DATA.toBuilder()
            .insert(107, EntityData.AMBIENT_SOUND_INTERVAL)
            .insert(108, EntityData.AMBIENT_SOUND_INTERVAL_RANGE)
            .insert(109, EntityData.AMBIENT_SOUND_EVENT_NAME)
            .insert(110, EntityData.FALL_DAMAGE_MULTIPLIER)
            .insert(111, EntityData.NAME_RAW_TEXT)
            .insert(112, EntityData.CAN_RIDE_TARGET)
            .build();

    protected static final TypeMap<EntityFlag> ENTITY_FLAGS = Bedrock_v361.ENTITY_FLAGS.toBuilder()
            .insert(88, EntityFlag.IS_IN_UI)
            .insert(89, EntityFlag.STALKING)
            .insert(90, EntityFlag.EMOTING)
            .insert(91, EntityFlag.CELEBRATING)
            .build();

    protected static final TypeMap<CommandParam> COMMAND_PARAMS = Bedrock_v361.COMMAND_PARAMS.toBuilder()
            .remove(18) // Removed INT_RANGE
            .shift(27, 2) // Move STRING up to 29
            .shift(31, 7) // Move POSITION up to 38
            .insert(37, CommandParam.BLOCK_POSITION)
            .shift(46, 1) // Move JSON up to 47
            .build();

    protected static final TypeMap<EntityEventType> ENTITY_EVENTS = Bedrock_v361.ENTITY_EVENTS.toBuilder()
            .insert(74, EntityEventType.FINISHED_CHARGING_CROSSBOW)
            .build();

    protected static final TypeMap<LevelEventType> LEVEL_EVENTS = Bedrock_v361.LEVEL_EVENTS.toBuilder()
            .insert(LEVEL_EVENT_PARTICLE + 24, LevelEvent.PARTICLE_POINT_CLOUD)
            .insert(LEVEL_EVENT_PARTICLE + 25, LevelEvent.PARTICLE_EXPLOSION)
            .insert(LEVEL_EVENT_PARTICLE + 26, LevelEvent.PARTICLE_BLOCK_EXPLOSION)
            .build();

    protected static final TypeMap<SoundEvent> SOUND_EVENTS = Bedrock_v361.SOUND_EVENTS.toBuilder()
            .replace(274, SoundEvent.AMBIENT_IN_RAID)
            .insert(275, SoundEvent.UI_CARTOGRAPHY_TABLE_USE)
            .insert(276, SoundEvent.UI_STONECUTTER_USE)
            .insert(277, SoundEvent.UI_LOOM_USE)
            .insert(278, SoundEvent.SMOKER_USE)
            .insert(279, SoundEvent.BLAST_FURNACE_USE)
            .insert(280, SoundEvent.SMITHING_TABLE_USE)
            .insert(281, SoundEvent.SCREECH)
            .insert(282, SoundEvent.SLEEP)
            .insert(283, SoundEvent.FURNACE_USE)
            .insert(284, SoundEvent.MOOSHROOM_CONVERT)
            .insert(285, SoundEvent.MILK_SUSPICIOUSLY)
            .insert(286, SoundEvent.CELEBRATE)
            .insert(287, SoundEvent.UNDEFINED)
            .build();

    protected static final TypeMap<ResourcePackType> RESOURCE_PACK_TYPES = Bedrock_v361.RESOURCE_PACK_TYPES.toBuilder()
            .replace(1, ResourcePackType.ADDON)
            .replace(2, ResourcePackType.CACHED)
            .replace(3, ResourcePackType.COPY_PROTECTED)
            .replace(4, ResourcePackType.DATA_ADD_ON)
            .replace(5, ResourcePackType.PERSONA_PIECE)
            .replace(6, ResourcePackType.RESOURCES)
            .replace(7, ResourcePackType.SKINS)
            .insert(8, ResourcePackType.WORLD_TEMPLATE)
            .build();

    public static final BedrockCodec CODEC = Bedrock_v361.CODEC.toBuilder()
            .protocolVersion(388)
            .minecraftVersion("1.13.0")
            .helper(() -> new BedrockCodecHelper_v388(ENTITY_DATA, ENTITY_DATA_TYPES, ENTITY_FLAGS, GAME_RULE_TYPES))
            .deregisterPacket(ExplodePacket.class)
            .updateSerializer(ResourcePackDataInfoPacket.class, new ResourcePackDataInfoSerializer_v361(RESOURCE_PACK_TYPES))
            .updateSerializer(ResourcePackStackPacket.class, ResourcePackStackSerializer_v388.INSTANCE)
            .updateSerializer(StartGamePacket.class, StartGameSerializer_v388.INSTANCE)
            .updateSerializer(AddPlayerPacket.class, AddPlayerSerializer_v388.INSTANCE)
            .updateSerializer(InteractPacket.class, InteractSerializer_v388.INSTANCE)
            .updateSerializer(RespawnPacket.class, RespawnSerializer_v388.INSTANCE)
            .updateSerializer(CraftingDataPacket.class, CraftingDataSerializer_v388.INSTANCE)
            .updateSerializer(PlayerListPacket.class, PlayerListSerializer_v388.INSTANCE)
            .updateSerializer(EventPacket.class, EventSerializer_v388.INSTANCE)
            .updateSerializer(AvailableCommandsPacket.class, new AvailableCommandsSerializer_v388(COMMAND_PARAMS))
            .updateSerializer(ResourcePackChunkDataPacket.class, ResourcePackChunkDataSerializer_v388.INSTANCE)
            .updateSerializer(StructureBlockUpdatePacket.class, StructureBlockUpdateSerializer_v388.INSTANCE)
            .updateSerializer(PlayerSkinPacket.class, PlayerSkinSerializer_v388.INSTANCE)
            .updateSerializer(MoveEntityDeltaPacket.class, MoveEntityDeltaSerializer_v388.INSTANCE)
            .updateSerializer(StructureTemplateDataResponsePacket.class, StructureTemplateDataResponseSerializer_v388.INSTANCE)
            .updateSerializer(EntityEventPacket.class, new EntityEventSerializer_v291(ENTITY_EVENTS))
            .updateSerializer(LevelSoundEvent1Packet.class, new LevelSoundEvent1Serializer_v291(SOUND_EVENTS))
            .updateSerializer(LevelSoundEvent2Packet.class, new LevelSoundEvent2Serializer_v313(SOUND_EVENTS))
            .updateSerializer(LevelSoundEventPacket.class, new LevelSoundEventSerializer_v332(SOUND_EVENTS))
            .registerPacket(TickSyncPacket.class, TickSyncSerializer_v388.INSTANCE, 23)
            .registerPacket(EducationSettingsPacket.class, EducationSettingsSerializer_v388.INSTANCE, 137)
            .registerPacket(EmotePacket.class, EmoteSerializer_v388.INSTANCE, 138)
            .registerPacket(MultiplayerSettingsPacket.class, MultiplayerSettingsSerializer_v388.INSTANCE, 139)
            .registerPacket(SettingsCommandPacket.class, SettingsCommandSerializer_v388.INSTANCE, 140)
            .registerPacket(AnvilDamagePacket.class, AnvilDamageSerializer_v388.INSTANCE, 141)
            .registerPacket(CompletedUsingItemPacket.class, CompletedUsingItemSerializer_v388.INSTANCE, 142)
            .registerPacket(NetworkSettingsPacket.class, NetworkSettingsSerializer_v388.INSTANCE, 143)
            .registerPacket(PlayerAuthInputPacket.class, PlayerAuthInputSerializer_v388.INSTANCE, 144)
            .build();
}
