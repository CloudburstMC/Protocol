package org.cloudburstmc.protocol.bedrock.codec.v361;

import org.cloudburstmc.protocol.bedrock.codec.BedrockCodec;
import org.cloudburstmc.protocol.bedrock.codec.v354.Bedrock_v354;
import org.cloudburstmc.protocol.bedrock.codec.v361.serializer.*;
import org.cloudburstmc.protocol.bedrock.data.LevelEvent;
import org.cloudburstmc.protocol.bedrock.data.LevelEventType;
import org.cloudburstmc.protocol.bedrock.data.ParticleType;
import org.cloudburstmc.protocol.bedrock.data.ResourcePackType;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityData;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityFlag;
import org.cloudburstmc.protocol.bedrock.packet.*;
import org.cloudburstmc.protocol.common.util.TypeMap;

public class Bedrock_v361 extends Bedrock_v354 {

    protected static final TypeMap<EntityData> ENTITY_DATA = Bedrock_v354.ENTITY_DATA.toBuilder()
            .replace(40, EntityData.NPC_DATA)
            .insert(103, EntityData.SKIN_ID)
            .insert(104, EntityData.SPAWNING_FRAMES)
            .insert(105, EntityData.COMMAND_BLOCK_TICK_DELAY)
            .insert(106, EntityData.COMMAND_BLOCK_EXECUTE_ON_FIRST_TICK)
            .build();

    protected static final TypeMap<EntityFlag> ENTITY_FLAGS = Bedrock_v354.ENTITY_FLAGS.toBuilder()
            .insert(87, EntityFlag.HIDDEN_WHEN_INVISIBLE)
            .build();

    protected static final TypeMap<LevelEventType> LEVEL_EVENTS = Bedrock_v354.LEVEL_EVENTS.toBuilder()
            .insert(LEVEL_EVENT_PARTICLE + 23, LevelEvent.PARTICLE_TELEPORT_TRAIL)
            .shift(LEVEL_EVENT_PARTICLE_TYPE + 1, 1)
            .insert(LEVEL_EVENT_PARTICLE_TYPE + 1, ParticleType.BUBBLE_MANUAL)
            .shift(LEVEL_EVENT_PARTICLE_TYPE + 22, 1)
            .insert(LEVEL_EVENT_PARTICLE_TYPE + 22, ParticleType.MOB_PORTAL)
            .shift(LEVEL_EVENT_PARTICLE_TYPE + 24, 1)
            .insert(LEVEL_EVENT_PARTICLE_TYPE + 24, ParticleType.WATER_SPLASH_MANUAL)
            .insert(LEVEL_EVENT_PARTICLE_TYPE + 48, ParticleType.FIREWORKS_STARTER)
            .insert(LEVEL_EVENT_PARTICLE_TYPE + 49, ParticleType.FIREWORKS)
            .insert(LEVEL_EVENT_PARTICLE_TYPE + 50, ParticleType.FIREWORKS_OVERLAY)
            .insert(LEVEL_EVENT_PARTICLE_TYPE + 51, ParticleType.BALLOON_GAS)
            .insert(LEVEL_EVENT_PARTICLE_TYPE + 52, ParticleType.COLORED_FLAME)
            .insert(LEVEL_EVENT_PARTICLE_TYPE + 53, ParticleType.SPARKLER)
            .insert(LEVEL_EVENT_PARTICLE_TYPE + 54, ParticleType.CONDUIT)
            .insert(LEVEL_EVENT_PARTICLE_TYPE + 55, ParticleType.BUBBLE_COLUMN_UP)
            .insert(LEVEL_EVENT_PARTICLE_TYPE + 56, ParticleType.BUBBLE_COLUMN_DOWN)
            .insert(LEVEL_EVENT_PARTICLE_TYPE + 57, ParticleType.SNEEZE)
            .build();

    protected static final TypeMap<ResourcePackType> RESOURCE_PACK_TYPES = TypeMap.builder(ResourcePackType.class)
            .insert(0, ResourcePackType.INVALID)
            .insert(1, ResourcePackType.RESOURCE)
            .insert(2, ResourcePackType.BEHAVIOR)
            .insert(3, ResourcePackType.WORLD_TEMPLATE)
            .insert(4, ResourcePackType.ADDON)
            .insert(5, ResourcePackType.SKINS)
            .insert(6, ResourcePackType.CACHED)
            .insert(7, ResourcePackType.COPY_PROTECTED)
            .build();

    public static BedrockCodec CODEC = Bedrock_v354.CODEC.toBuilder()
            .protocolVersion(361)
            .minecraftVersion("1.12.0")
            .helper(() -> new BedrockCodecHelper_v361(ENTITY_DATA, ENTITY_DATA_TYPES, ENTITY_FLAGS, GAME_RULE_TYPES))
            .deregisterPacket(AddHangingEntityPacket.class)
            .updateSerializer(StartGamePacket.class, StartGameSerializer_v361.INSTANCE)
            .updateSerializer(AddPaintingPacket.class, AddPaintingSerializer_v361.INSTANCE)
            .updateSerializer(CraftingDataPacket.class, CraftingDataSerializer_v361.INSTANCE)
            .updateSerializer(LevelChunkPacket.class, LevelChunkSerializer_v361.INSTANCE)
            .updateSerializer(CommandBlockUpdatePacket.class, CommandBlockUpdateSerializer_v361.INSTANCE)
            .updateSerializer(ResourcePackDataInfoPacket.class, new ResourcePackDataInfoSerializer_v361(RESOURCE_PACK_TYPES))
            .updateSerializer(StructureBlockUpdatePacket.class, StructureBlockUpdateSerializer_v361.INSTANCE)
            .updateSerializer(LevelEventGenericPacket.class, LevelEventGenericSerializer_v361.INSTANCE)
            .updateSerializer(VideoStreamConnectPacket.class, VideoStreamConnectSerializer_v361.INSTANCE)
            // AddEntityPacket 127
            // RemoveEntityPacket 128
            .registerPacket(ClientCacheStatusPacket.class, ClientCacheStatusSerializer_v361.INSTANCE, 129)
            .registerPacket(StructureTemplateDataRequestPacket.class, StructureTemplateDataRequestSerializer_v361.INSTANCE, 132)
            .registerPacket(StructureTemplateDataResponsePacket.class, StructureTemplateDataResponseSerializer_v361.INSTANCE, 133)
            .registerPacket(UpdateBlockPropertiesPacket.class, UpdateBlockPropertiesSerializer_v361.INSTANCE, 134)
            .registerPacket(ClientCacheBlobStatusPacket.class, ClientCacheBlobStatusSerializer_v361.INSTANCE, 135)
            .registerPacket(ClientCacheMissResponsePacket.class, ClientCacheMissResponseSerializer_v361.INSTANCE, 136)
            .build();
}
