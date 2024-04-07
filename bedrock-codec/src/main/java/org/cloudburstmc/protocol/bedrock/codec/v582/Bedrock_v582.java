package org.cloudburstmc.protocol.bedrock.codec.v582;

import org.cloudburstmc.protocol.bedrock.codec.BedrockCodec;
import org.cloudburstmc.protocol.bedrock.codec.v291.serializer.LevelEventSerializer_v291;
import org.cloudburstmc.protocol.bedrock.codec.v361.serializer.LevelEventGenericSerializer_v361;
import org.cloudburstmc.protocol.bedrock.codec.v448.serializer.AvailableCommandsSerializer_v448;
import org.cloudburstmc.protocol.bedrock.codec.v575.BedrockCodecHelper_v575;
import org.cloudburstmc.protocol.bedrock.codec.v575.Bedrock_v575;
import org.cloudburstmc.protocol.bedrock.codec.v582.serializer.*;
import org.cloudburstmc.protocol.bedrock.data.LevelEvent;
import org.cloudburstmc.protocol.bedrock.data.LevelEventType;
import org.cloudburstmc.protocol.bedrock.data.PacketRecipient;
import org.cloudburstmc.protocol.bedrock.data.command.CommandParam;
import org.cloudburstmc.protocol.bedrock.data.inventory.ContainerSlotType;
import org.cloudburstmc.protocol.bedrock.packet.*;
import org.cloudburstmc.protocol.common.util.TypeMap;

public class Bedrock_v582 extends Bedrock_v575 {

    protected static final TypeMap<ContainerSlotType> CONTAINER_SLOT_TYPES = Bedrock_v575.CONTAINER_SLOT_TYPES.toBuilder()
            .insert(61, ContainerSlotType.SMITHING_TABLE_TEMPLATE)
            .build();

    protected static final TypeMap<LevelEventType> LEVEL_EVENTS = Bedrock_v575.LEVEL_EVENTS.toBuilder()
            .insert(LEVEL_EVENT_SOUND + 67, LevelEvent.SOUND_AMETHYST_RESONATE)
            .insert(LEVEL_EVENT_BLOCK + 103, LevelEvent.PARTICLE_BREAK_BLOCK_DOWN)
            .insert(LEVEL_EVENT_BLOCK + 104, LevelEvent.PARTICLE_BREAK_BLOCK_UP)
            .insert(LEVEL_EVENT_BLOCK + 105, LevelEvent.PARTICLE_BREAK_BLOCK_NORTH)
            .insert(LEVEL_EVENT_BLOCK + 106, LevelEvent.PARTICLE_BREAK_BLOCK_SOUTH)
            .insert(LEVEL_EVENT_BLOCK + 107, LevelEvent.PARTICLE_BREAK_BLOCK_WEST)
            .insert(LEVEL_EVENT_BLOCK + 108, LevelEvent.PARTICLE_BREAK_BLOCK_EAST)
            .insert(LEVEL_EVENT_BLOCK + 109, LevelEvent.ALL_PLAYERS_SLEEPING)
            .insert(LEVEL_EVENT_PARTICLE_TYPE, PARTICLE_TYPES)
            .remove(9800)
            .insert(9810, LevelEvent.JUMP_PREVENTED)
            .build();

    protected static final TypeMap<CommandParam> COMMAND_PARAMS = Bedrock_v575.COMMAND_PARAMS.toBuilder()
            .shift(32, 5)
            .insert(32, CommandParam.PERMISSION)
            .insert(33, CommandParam.PERMISSIONS)
            .insert(34, CommandParam.PERMISSION_SELECTOR)
            .insert(35, CommandParam.PERMISSION_ELEMENT)
            .insert(36, CommandParam.PERMISSION_ELEMENTS)
            .build();

    public static final BedrockCodec CODEC = Bedrock_v575.CODEC.toBuilder()
            .raknetProtocolVersion(11)
            .protocolVersion(582)
            .minecraftVersion("1.19.80")
            .helper(() -> new BedrockCodecHelper_v575(ENTITY_DATA, GAME_RULE_TYPES, ITEM_STACK_REQUEST_TYPES, CONTAINER_SLOT_TYPES, PLAYER_ABILITIES, TEXT_PROCESSING_ORIGINS))
            .updateSerializer(StartGamePacket.class, new StartGameSerializer_v582())
            .updateSerializer(RequestChunkRadiusPacket.class, RequestChunkRadiusSerializer_v582.INSTANCE)
            .updateSerializer(CraftingDataPacket.class, new CraftingDataSerializer_v582())
            .updateSerializer(LevelEventPacket.class, new LevelEventSerializer_v291(LEVEL_EVENTS))
            .updateSerializer(LevelEventGenericPacket.class, new LevelEventGenericSerializer_v361(LEVEL_EVENTS))
            .updateSerializer(AvailableCommandsPacket.class, new AvailableCommandsSerializer_v448(COMMAND_PARAMS))
            .registerPacket(CompressedBiomeDefinitionListPacket::new, CompressedBiomeDefinitionListSerializer_v582.INSTANCE, 301, PacketRecipient.CLIENT)
            .registerPacket(TrimDataPacket::new, TrimDataSerializer_v582.INSTANCE, 302, PacketRecipient.CLIENT)
            .registerPacket(OpenSignPacket::new, OpenSignSerializer_v582.INSTANCE, 303, PacketRecipient.SERVER)
            .build();
}
