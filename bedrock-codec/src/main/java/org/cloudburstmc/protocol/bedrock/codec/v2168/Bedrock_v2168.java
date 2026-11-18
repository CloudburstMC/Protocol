package org.cloudburstmc.protocol.bedrock.codec.v2168;

import org.cloudburstmc.protocol.bedrock.codec.BedrockCodec;
import org.cloudburstmc.protocol.bedrock.codec.EntityDataTypeMap;
import org.cloudburstmc.protocol.bedrock.codec.v1001.Bedrock_v1001;
import org.cloudburstmc.protocol.bedrock.codec.v1001.serializer.LevelSoundEventSerializer_v1001;
import org.cloudburstmc.protocol.bedrock.codec.v2168.serializer.*;
import org.cloudburstmc.protocol.bedrock.codec.v291.serializer.LevelEventSerializer_v291;
import org.cloudburstmc.protocol.bedrock.codec.v361.serializer.LevelEventGenericSerializer_v361;
import org.cloudburstmc.protocol.bedrock.data.LevelEventType;
import org.cloudburstmc.protocol.bedrock.data.ParticleType;
import org.cloudburstmc.protocol.bedrock.data.SoundEvent;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityDataFormat;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityDataTypes;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityFlag;
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.action.ItemStackRequestActionType;
import org.cloudburstmc.protocol.bedrock.packet.*;
import org.cloudburstmc.protocol.bedrock.transformer.*;
import org.cloudburstmc.protocol.common.util.TypeMap;

public class Bedrock_v2168 extends Bedrock_v1001 {

    protected static final TypeMap<EntityFlag> ENTITY_FLAGS = Bedrock_v1001.ENTITY_FLAGS
            .toBuilder()
            .insert(130, EntityFlag.NOT_PICKABLE_FROM_INSIDE)
            .build();

    protected static final TypeMap<ParticleType> PARTICLE_TYPES = Bedrock_v1001.PARTICLE_TYPES.toBuilder()
            .insert(102, ParticleType.ORANGE_POPLAR_LEAVES)
            .insert(103, ParticleType.RED_POPLAR_LEAVES)
            .insert(104, ParticleType.YELLOW_POPLAR_LEAVES)
            .build();

    protected static final EntityDataTypeMap ENTITY_DATA = Bedrock_v1001.ENTITY_DATA
            .toBuilder()
            .update(EntityDataTypes.AREA_EFFECT_CLOUD_PARTICLE, new TypeMapTransformer<>(PARTICLE_TYPES))
            .update(EntityDataTypes.FLAGS, new FlagTransformer(ENTITY_FLAGS, 0))
            .update(EntityDataTypes.FLAGS_2, new FlagTransformer(ENTITY_FLAGS, 1))
            .remove(16)
            .insert(EntityDataTypes.DISPLAY_BLOCK_STATE, 16, EntityDataFormat.INT, new BlockDefinitionTransformer())
            .insert(EntityDataTypes.DISPLAY_FIREWORK, 16, EntityDataFormat.NBT)
            .insert(EntityDataTypes.HORSE_FLAGS, 16, EntityDataFormat.LONG, Long2IntTransformer.INSTANCE)
            .insert(EntityDataTypes.WITHER_SKULL_DANGEROUS, 16, EntityDataFormat.BYTE)
            .insert(EntityDataTypes.UNKNOWN_HORSE_INT_25, 25, EntityDataFormat.INT)
            .build();

    protected static final TypeMap<LevelEventType> LEVEL_EVENTS = Bedrock_v1001.LEVEL_EVENTS.toBuilder()
            .insert(LEVEL_EVENT_PARTICLE_TYPE, PARTICLE_TYPES)
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
            .insert(9, ItemStackRequestActionType.MINE_BLOCK)
            .insert(10, ItemStackRequestActionType.CRAFT_RECIPE)
            .insert(11, ItemStackRequestActionType.CRAFT_RECIPE_AUTO)
            .insert(12, ItemStackRequestActionType.CRAFT_CREATIVE)
            .insert(13, ItemStackRequestActionType.CRAFT_RECIPE_OPTIONAL)
            .insert(14, ItemStackRequestActionType.CRAFT_REPAIR_AND_DISENCHANT)
            .insert(15, ItemStackRequestActionType.CRAFT_LOOM)
            .insert(16, ItemStackRequestActionType.CRAFT_NON_IMPLEMENTED_DEPRECATED)
            .insert(17, ItemStackRequestActionType.CRAFT_RESULTS_DEPRECATED)
            .build();

    protected static final TypeMap<ItemStackRequestActionType> ITEM_STACK_REQUEST_ACTION_ENUM_VALUES = TypeMap.builder(ItemStackRequestActionType.class)
            .insert(0, ItemStackRequestActionType.TAKE)
            .insert(1, ItemStackRequestActionType.PLACE)
            .insert(2, ItemStackRequestActionType.SWAP)
            .insert(3, ItemStackRequestActionType.DROP)
            .insert(4, ItemStackRequestActionType.DESTROY)
            .insert(5, ItemStackRequestActionType.CONSUME)
            .insert(6, ItemStackRequestActionType.CREATE)
            .insert(7, ItemStackRequestActionType.PLACE_IN_ITEM_CONTAINER)
            .insert(8, ItemStackRequestActionType.TAKE_FROM_ITEM_CONTAINER)
            .insert(9, ItemStackRequestActionType.LAB_TABLE_COMBINE)
            .insert(10, ItemStackRequestActionType.BEACON_PAYMENT)
            .insert(11, ItemStackRequestActionType.MINE_BLOCK)
            .insert(12, ItemStackRequestActionType.CRAFT_RECIPE)
            .insert(13, ItemStackRequestActionType.CRAFT_RECIPE_AUTO)
            .insert(14, ItemStackRequestActionType.CRAFT_CREATIVE)
            .insert(15, ItemStackRequestActionType.CRAFT_RECIPE_OPTIONAL)
            .insert(16, ItemStackRequestActionType.CRAFT_REPAIR_AND_DISENCHANT)
            .insert(17, ItemStackRequestActionType.CRAFT_LOOM)
            .insert(18, ItemStackRequestActionType.CRAFT_NON_IMPLEMENTED_DEPRECATED)
            .insert(19, ItemStackRequestActionType.CRAFT_RESULTS_DEPRECATED)
            .build();

    protected static final TypeMap<SoundEvent> SOUND_EVENTS = Bedrock_v1001.SOUND_EVENTS
            .toBuilder()
            .replace(611, SoundEvent.MOUNT)
            .insert(612, SoundEvent.DISMOUNT)
            .insert(613, SoundEvent.STRAW_BED_BREAK_LEAVE)
            .insert(614, SoundEvent.UNDEFINED)
            .build();

    public static final BedrockCodec CODEC = Bedrock_v1001.CODEC.toBuilder()
            .protocolVersion(2168)
            .minecraftVersion("1.26.40")
            .helper(() -> new BedrockCodecHelper_v2168(ENTITY_DATA, GAME_RULE_TYPES, ITEM_STACK_REQUEST_TYPES, CONTAINER_SLOT_TYPES, PLAYER_ABILITIES, TEXT_PROCESSING_ORIGINS, ITEM_STACK_REQUEST_ACTION_ENUM_VALUES))
            .updateSerializer(AnvilDamagePacket.class, AnvilDamageSerializer_v2168.INSTANCE)
            .updateSerializer(ClientboundMapItemDataPacket.class, ClientboundMapItemDataSerializer_v2168.INSTANCE)
            .updateSerializer(ClientboundUpdateSoundDataPacket.class, ClientboundUpdateSoundDataSerializer_v2168.INSTANCE)
            .updateSerializer(CraftingDataPacket.class, CraftingDataSerializer_v2168.INSTANCE)
            .updateSerializer(CreativeContentPacket.class, CreativeContentSerializer_v2168.INSTANCE)
            .updateSerializer(DimensionDataPacket.class, DimensionDataSerializer_v2168.INSTANCE)
            .updateSerializer(ItemStackResponsePacket.class, ItemStackResponseSerializer_v2168.INSTANCE)
            .updateSerializer(LevelChunkPacket.class, LevelChunkSerializer_v2168.INSTANCE)
            .updateSerializer(LevelEventPacket.class, new LevelEventSerializer_v291(LEVEL_EVENTS))
            .updateSerializer(LevelEventGenericPacket.class, new LevelEventGenericSerializer_v361(LEVEL_EVENTS))
            .updateSerializer(LevelSoundEventPacket.class, new LevelSoundEventSerializer_v1001(SOUND_EVENTS))
            .updateSerializer(MoveEntityDeltaPacket.class, MoveEntityDeltaSerializer_v2168.INSTANCE)
            .updateSerializer(MovePlayerPacket.class, MovePlayerSerializer_v2168.INSTANCE)
            .updateSerializer(PlayerAuthInputPacket.class, PlayerAuthInputSerializer_v2168.INSTANCE)
            .updateSerializer(PlayerListPacket.class, PlayerListSerializer_v2168.INSTANCE)
            .updateSerializer(PlayerLocationPacket.class, PlayerLocationSerializer_v2168.INSTANCE)
            .updateSerializer(PlayerSkinPacket.class, PlayerSkinSerializer_v2168.INSTANCE)
            .updateSerializer(PlayerUpdateEntityOverridesPacket.class, PlayerUpdateEntityOverridesSerializer_v2168.INSTANCE)
            .updateSerializer(PlaySoundPacket.class, PlaySoundSerializer_v2168.INSTANCE)
            .updateSerializer(ResourcePackClientResponsePacket.class, ResourcePackClientResponseSerializer_v2168.INSTANCE)
            .updateSerializer(ResourcePacksInfoPacket.class, ResourcePacksInfoSerializer_v2168.INSTANCE)
            .updateSerializer(SetScorePacket.class, SetScoreSerializer_v2168.INSTANCE)
            .updateSerializer(SetScoreboardIdentityPacket.class, SetScoreboardIdentitySerializer_v2168.INSTANCE)
            .updateSerializer(ServerboundDiagnosticsPacket.class, ServerboundDiagnosticsSerializer_v2168.INSTANCE)
            .updateSerializer(StartGamePacket.class, StartGameSerializer_v2168.INSTANCE)
            .updateSerializer(StructureBlockUpdatePacket.class, StructureBlockUpdateSerializer_v2168.INSTANCE)
            .updateSerializer(SubChunkPacket.class, SubChunkSerializer_v2168.INSTANCE)
            .updateSerializer(TransferPacket.class, TransferSerializer_v2168.INSTANCE)
            .build();
}
