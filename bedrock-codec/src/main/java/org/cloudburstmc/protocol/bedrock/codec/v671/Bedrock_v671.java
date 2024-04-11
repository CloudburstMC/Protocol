package org.cloudburstmc.protocol.bedrock.codec.v671;

import org.cloudburstmc.protocol.bedrock.codec.BedrockCodec;
import org.cloudburstmc.protocol.bedrock.codec.EntityDataTypeMap;
import org.cloudburstmc.protocol.bedrock.codec.v291.serializer.LevelEventSerializer_v291;
import org.cloudburstmc.protocol.bedrock.codec.v361.serializer.LevelEventGenericSerializer_v361;
import org.cloudburstmc.protocol.bedrock.codec.v575.BedrockCodecHelper_v575;
import org.cloudburstmc.protocol.bedrock.codec.v662.Bedrock_v662;
import org.cloudburstmc.protocol.bedrock.codec.v671.serializer.*;
import org.cloudburstmc.protocol.bedrock.data.LevelEventType;
import org.cloudburstmc.protocol.bedrock.data.ParticleType;
import org.cloudburstmc.protocol.bedrock.data.command.CommandParam;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityDataTypes;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityFlag;
import org.cloudburstmc.protocol.bedrock.packet.*;
import org.cloudburstmc.protocol.bedrock.transformer.FlagTransformer;
import org.cloudburstmc.protocol.common.util.TypeMap;

public class Bedrock_v671 extends Bedrock_v662 {

    protected static final TypeMap<ParticleType> PARTICLE_TYPES = Bedrock_v662.PARTICLE_TYPES.toBuilder()
            .insert(92, ParticleType.WOLF_ARMOR_BREAK)
            .build();

    protected static final TypeMap<LevelEventType> LEVEL_EVENTS = Bedrock_v662.LEVEL_EVENTS.toBuilder()
            .insert(LEVEL_EVENT_PARTICLE_TYPE, PARTICLE_TYPES)
            .build();

    protected static final TypeMap<EntityFlag> ENTITY_FLAGS = Bedrock_v662.ENTITY_FLAGS
            .toBuilder()
            .insert(118, EntityFlag.BODY_ROTATION_BLOCKED)
            .build();

    protected static final EntityDataTypeMap ENTITY_DATA = Bedrock_v662.ENTITY_DATA
            .toBuilder()
            .update(EntityDataTypes.FLAGS, new FlagTransformer(ENTITY_FLAGS, 0))
            .update(EntityDataTypes.FLAGS_2, new FlagTransformer(ENTITY_FLAGS, 1))
            .build();

    public static final BedrockCodec CODEC = Bedrock_v662.CODEC.toBuilder()
            .raknetProtocolVersion(11)
            .protocolVersion(671)
            .minecraftVersion("1.20.80")
            .helper(() -> new BedrockCodecHelper_v575(ENTITY_DATA, GAME_RULE_TYPES, ITEM_STACK_REQUEST_TYPES, CONTAINER_SLOT_TYPES, PLAYER_ABILITIES, TEXT_PROCESSING_ORIGINS))
            .updateSerializer(LevelEventPacket.class, new LevelEventSerializer_v291(LEVEL_EVENTS))
            .updateSerializer(LevelEventGenericPacket.class, new LevelEventGenericSerializer_v361(LEVEL_EVENTS))
            .updateSerializer(ClientboundDebugRendererPacket.class, ClientboundDebugRendererSerializer_v671.INSTANCE)
            .updateSerializer(CorrectPlayerMovePredictionPacket.class, CorrectPlayerMovePredictionSerializer_v671.INSTANCE)
            .updateSerializer(ResourcePackStackPacket.class, ResourcePackStackSerializer_v671.INSTANCE)
            .updateSerializer(UpdatePlayerGameTypePacket.class, UpdatePlayerGameTypeSerializer_v671.INSTANCE)
            .updateSerializer(StartGamePacket.class, StartGameSerializer_v671.INSTANCE)
            .updateSerializer(CraftingDataPacket.class, CraftingDataSerializer_v671.INSTANCE)
            .deregisterPacket(FilterTextPacket.class) // TODO: check
            // TODO: confirm change in AnimatePacket
            .build();
}