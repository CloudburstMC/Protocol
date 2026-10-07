package org.cloudburstmc.protocol.bedrock.codec.v2225;

import org.cloudburstmc.protocol.bedrock.codec.BedrockCodec;
import org.cloudburstmc.protocol.bedrock.codec.v2193.Bedrock_v2193;
import org.cloudburstmc.protocol.bedrock.codec.v2225.serializer.*;
import org.cloudburstmc.protocol.bedrock.codec.v291.serializer.LevelEventSerializer_v291;
import org.cloudburstmc.protocol.bedrock.codec.v361.serializer.LevelEventGenericSerializer_v361;
import org.cloudburstmc.protocol.bedrock.data.LevelEventType;
import org.cloudburstmc.protocol.bedrock.data.PacketRecipient;
import org.cloudburstmc.protocol.bedrock.data.ParticleType;
import org.cloudburstmc.protocol.bedrock.data.SoundEvent;
import org.cloudburstmc.protocol.bedrock.data.inventory.ContainerSlotType;
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.action.ItemStackRequestActionType;
import org.cloudburstmc.protocol.bedrock.packet.*;
import org.cloudburstmc.protocol.common.util.TypeMap;

public class Bedrock_v2225 extends Bedrock_v2193 {


    protected static final TypeMap<ContainerSlotType> CONTAINER_SLOT_TYPES = Bedrock_v2193.CONTAINER_SLOT_TYPES
            .toBuilder()
            .insert(67, ContainerSlotType.RESERVED_CONTAINER_A)
            .insert(68, ContainerSlotType.RESERVED_CONTAINER_B)
            .insert(69, ContainerSlotType.RESERVED_CONTAINER_C)
            .insert(70, ContainerSlotType.RESERVED_CONTAINER_D)
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
            .insert(16, ItemStackRequestActionType.RESERVED) //new
            .insert(17, ItemStackRequestActionType.CRAFT_NON_IMPLEMENTED_DEPRECATED)
            .insert(18, ItemStackRequestActionType.CRAFT_RESULTS_DEPRECATED)
            .build();

    protected static final TypeMap<ParticleType> PARTICLE_TYPES = Bedrock_v2193.PARTICLE_TYPES.toBuilder()
            .insert(105, ParticleType.ICE_BALL_BREAK)
            .build();

    protected static final TypeMap<LevelEventType> LEVEL_EVENTS = Bedrock_v2193.LEVEL_EVENTS.toBuilder()
            .insert(LEVEL_EVENT_PARTICLE_TYPE, PARTICLE_TYPES)
            .build();

    protected static final TypeMap<SoundEvent> SOUND_EVENTS = Bedrock_v2193.SOUND_EVENTS
            .toBuilder()
            .replace(614, SoundEvent.ICE_BALL_BREAK)
            .insert(615, SoundEvent.UNDEFINED)
            .build();

    public static final BedrockCodec CODEC = Bedrock_v2193.CODEC.toBuilder()
            .protocolVersion(2225)
            .minecraftVersion("1.26.60")
            .helper(() -> new BedrockCodecHelper_v2225(ENTITY_DATA, GAME_RULE_TYPES, ITEM_STACK_REQUEST_TYPES, CONTAINER_SLOT_TYPES, PLAYER_ABILITIES, TEXT_PROCESSING_ORIGINS))
            .updateSerializer(AddEntityPacket.class, AddEntitySerializer_v2225.INSTANCE)
            .updateSerializer(AddPlayerPacket.class, AddPlayerSerializer_v2225.INSTANCE)
            .updateSerializer(AnimatePacket.class, AnimateSerializer_v2225.INSTANCE)
            .updateSerializer(ClientboundAttributeLayerSyncPacket.class, ClientboundAttributeLayerSyncSerializer_v2225.INSTANCE)
            .updateSerializer(ClientboundUpdateSoundDataPacket.class, ClientboundUpdateSoundDataSerializer_v2225.INSTANCE)
            .updateSerializer(DimensionDataPacket.class, DimensionDataSerializer_v2225.INSTANCE)
            .updateSerializer(InventoryTransactionPacket.class, InventoryTransactionSerializer_v2225.INSTANCE)
            .updateSerializer(LevelChunkPacket.class, LevelChunkSerializer_v2225.INSTANCE)
            .updateSerializer(LevelEventPacket.class, new LevelEventSerializer_v291(LEVEL_EVENTS))
            .updateSerializer(LevelEventGenericPacket.class, new LevelEventGenericSerializer_v361(LEVEL_EVENTS))
            .updateSerializer(StartGamePacket.class, StartGameSerializer_v2225.INSTANCE)
            .updateSerializer(PlayerListPacket.class, PlayerListSerializer_v2225.INSTANCE)
            .registerPacket(ClientboundMatchmakingStatePacket::new, ClientboundMatchmakingStateSerializer_v2225.INSTANCE, 353, PacketRecipient.CLIENT)
            .registerPacket(ServerboundStonecutterSetRecipePacket::new, ServerboundStonecutterSetRecipeSerializer_v2225.INSTANCE, 354, PacketRecipient.SERVER)
            .registerPacket(ClientboundStonecutterSetRecipePacket::new, ClientboundStonecutterSetRecipeSerializer_v2225.INSTANCE, 355, PacketRecipient.CLIENT)
            .registerPacket(ServerboundMatchmakingCancelPacket::new, ServerboundMatchmakingCancelSerializer_v2225.INSTANCE, 356, PacketRecipient.SERVER)
            .registerPacket(SetPassengerOfBlockPacket::new, SetPassengerOfBlockSerializer_v2225.INSTANCE, 357, PacketRecipient.BOTH)
            .registerPacket(ServerboundCursorItemDragPacket::new, ServerboundCursorItemDragSerializer_v2225.INSTANCE, 358, PacketRecipient.SERVER)
            .registerPacket(ClientboundPlayAudioContentPacket::new, ClientboundPlayAudioContentSerializer_v2225.INSTANCE, 359, PacketRecipient.CLIENT)
            .registerPacket(ServerboundRegisterAudioContentPacket::new, ServerboundRegisterAudioContentSerializer_v2225.INSTANCE, 360, PacketRecipient.SERVER)
            .build();
}
