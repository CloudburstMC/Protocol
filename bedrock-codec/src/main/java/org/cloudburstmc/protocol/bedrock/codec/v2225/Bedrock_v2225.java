package org.cloudburstmc.protocol.bedrock.codec.v2225;

import org.cloudburstmc.protocol.bedrock.codec.BedrockCodec;
import org.cloudburstmc.protocol.bedrock.codec.v2193.Bedrock_v2193;
import org.cloudburstmc.protocol.bedrock.codec.v2225.serializer.*;
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

    public static final BedrockCodec CODEC = Bedrock_v2193.CODEC.toBuilder()
            .protocolVersion(2225)
            .minecraftVersion("1.26.60")
            .helper(() -> new BedrockCodecHelper_v2225(ENTITY_DATA, GAME_RULE_TYPES, ITEM_STACK_REQUEST_TYPES, CONTAINER_SLOT_TYPES, PLAYER_ABILITIES, TEXT_PROCESSING_ORIGINS))
            .updateSerializer(AddEntityPacket.class, AddEntitySerializer_v2225.INSTANCE)
            .updateSerializer(AddPlayerPacket.class, AddPlayerSerializer_v2225.INSTANCE)
            .updateSerializer(AnimatePacket.class, AnimateSerializer_v2225.INSTANCE)
            .updateSerializer(DimensionDataPacket.class, DimensionDataSerializer_v2225.INSTANCE)
            .updateSerializer(InventoryTransactionPacket.class, InventoryTransactionSerializer_v2225.INSTANCE)
            .updateSerializer(LevelChunkPacket.class, LevelChunkSerializer_v2225.INSTANCE)
            .updateSerializer(StartGamePacket.class, StartGameSerializer_v2225.INSTANCE)
            .updateSerializer(PlayerListPacket.class, PlayerListSerializer_v2225.INSTANCE)
            //.registerPacket(SetPlayerFurnaceOptionsPacket::new, SetPlayerFurnaceOptionsSerializer_v2193.INSTANCE, 351, PacketRecipient.BOTH)
            .build();
}
