package org.cloudburstmc.protocol.bedrock.codec.v361;

import org.cloudburstmc.protocol.bedrock.codec.BedrockCodec;
import org.cloudburstmc.protocol.bedrock.codec.v354.Bedrock_v354;
import org.cloudburstmc.protocol.bedrock.codec.v361.serializer.AddPaintingSerializer_v361;
import org.cloudburstmc.protocol.bedrock.codec.v361.serializer.ClientCacheBlobStatusSerializer_v361;
import org.cloudburstmc.protocol.bedrock.codec.v361.serializer.ClientCacheMissResponseSerializer_v361;
import org.cloudburstmc.protocol.bedrock.codec.v361.serializer.ClientCacheStatusSerializer_v361;
import org.cloudburstmc.protocol.bedrock.codec.v361.serializer.CommandBlockUpdateSerializer_v361;
import org.cloudburstmc.protocol.bedrock.codec.v361.serializer.CraftingDataSerializer_v361;
import org.cloudburstmc.protocol.bedrock.codec.v361.serializer.LevelChunkSerializer_v361;
import org.cloudburstmc.protocol.bedrock.codec.v361.serializer.LevelEventGenericSerializer_v361;
import org.cloudburstmc.protocol.bedrock.codec.v361.serializer.ResourcePackDataInfoSerializer_v361;
import org.cloudburstmc.protocol.bedrock.codec.v361.serializer.StartGameSerializer_v361;
import org.cloudburstmc.protocol.bedrock.codec.v361.serializer.StructureBlockUpdateSerializer_v361;
import org.cloudburstmc.protocol.bedrock.codec.v361.serializer.StructureTemplateDataRequestSerializer_v361;
import org.cloudburstmc.protocol.bedrock.codec.v361.serializer.StructureTemplateDataResponseSerializer_v361;
import org.cloudburstmc.protocol.bedrock.codec.v361.serializer.UpdateBlockPropertiesSerializer_v361;
import org.cloudburstmc.protocol.bedrock.codec.v361.serializer.VideoStreamConnectSerializer_v361;
import org.cloudburstmc.protocol.bedrock.data.LevelEvent;
import org.cloudburstmc.protocol.bedrock.data.LevelEventType;
import org.cloudburstmc.protocol.bedrock.data.ParticleType;
import org.cloudburstmc.protocol.bedrock.data.ResourcePackType;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityData;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityFlag;
import org.cloudburstmc.protocol.bedrock.packet.AddHangingEntityPacket;
import org.cloudburstmc.protocol.bedrock.packet.AddPaintingPacket;
import org.cloudburstmc.protocol.bedrock.packet.ClientCacheBlobStatusPacket;
import org.cloudburstmc.protocol.bedrock.packet.ClientCacheMissResponsePacket;
import org.cloudburstmc.protocol.bedrock.packet.ClientCacheStatusPacket;
import org.cloudburstmc.protocol.bedrock.packet.CommandBlockUpdatePacket;
import org.cloudburstmc.protocol.bedrock.packet.CraftingDataPacket;
import org.cloudburstmc.protocol.bedrock.packet.LevelChunkPacket;
import org.cloudburstmc.protocol.bedrock.packet.LevelEventGenericPacket;
import org.cloudburstmc.protocol.bedrock.packet.ResourcePackDataInfoPacket;
import org.cloudburstmc.protocol.bedrock.packet.StartGamePacket;
import org.cloudburstmc.protocol.bedrock.packet.StructureBlockUpdatePacket;
import org.cloudburstmc.protocol.bedrock.packet.StructureTemplateDataRequestPacket;
import org.cloudburstmc.protocol.bedrock.packet.StructureTemplateDataResponsePacket;
import org.cloudburstmc.protocol.bedrock.packet.UpdateBlockPropertiesPacket;
import org.cloudburstmc.protocol.bedrock.packet.VideoStreamConnectPacket;
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
            .build();

    protected static final TypeMap<ResourcePackType> RESOURCE_PACK_TYPES = TypeMap.builder(ResourcePackType.class)
            .insert(0, ResourcePackType.INVALID)
            .insert(1, ResourcePackType.RESOURCES)
            .insert(2, ResourcePackType.DATA_ADD_ON)
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
            .registerPacket(LevelEventGenericPacket.class, LevelEventGenericSerializer_v361.INSTANCE, 124)
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
