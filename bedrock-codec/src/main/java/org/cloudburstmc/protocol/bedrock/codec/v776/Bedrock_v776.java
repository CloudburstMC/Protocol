package org.cloudburstmc.protocol.bedrock.codec.v776;

import org.cloudburstmc.protocol.bedrock.codec.BedrockCodec;
import org.cloudburstmc.protocol.bedrock.codec.EntityDataTypeMap;
import org.cloudburstmc.protocol.bedrock.codec.v766.Bedrock_v766;
import org.cloudburstmc.protocol.bedrock.codec.v776.serializer.*;
import org.cloudburstmc.protocol.bedrock.data.*;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityDataFormat;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityDataTypes;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityFlag;
import org.cloudburstmc.protocol.bedrock.packet.*;
import org.cloudburstmc.protocol.bedrock.transformer.FlagTransformer;
import org.cloudburstmc.protocol.common.util.TypeMap;

public class Bedrock_v776 extends Bedrock_v766 {

    protected static final TypeMap<EntityFlag> ENTITY_FLAGS = Bedrock_v766.ENTITY_FLAGS
            .toBuilder()
            .insert(119, EntityFlag.RENDER_WHEN_INVISIBLE)
            .build();

    protected static final EntityDataTypeMap ENTITY_DATA = Bedrock_v766.ENTITY_DATA
            .toBuilder()
            .insert(EntityDataTypes.FILTERED_NAME, 132, EntityDataFormat.STRING)
            .insert(EntityDataTypes.BED_ENTER_POSITION, 133, EntityDataFormat.VECTOR3F)
            .update(EntityDataTypes.FLAGS, new FlagTransformer(ENTITY_FLAGS, 0))
            .update(EntityDataTypes.FLAGS_2, new FlagTransformer(ENTITY_FLAGS, 1))
            .build();

    protected static final TypeMap<Ability> PLAYER_ABILITIES = Bedrock_v766.PLAYER_ABILITIES
            .toBuilder()
            .insert(19, Ability.VERTICAL_FLY_SPEED)
            .build();

    public static final BedrockCodec CODEC = Bedrock_v766.CODEC.toBuilder()
            .raknetProtocolVersion(11)
            .protocolVersion(776)
            .minecraftVersion("1.21.60")
            .helper(() -> new BedrockCodecHelper_v776(ENTITY_DATA, GAME_RULE_TYPES, ITEM_STACK_REQUEST_TYPES, CONTAINER_SLOT_TYPES, PLAYER_ABILITIES, TEXT_PROCESSING_ORIGINS))
            .updateSerializer(BossEventPacket.class, BossEventSerializer_v776.INSTANCE)
            .updateSerializer(CameraAimAssistPresetsPacket.class, CameraAimAssistPresetsSerializer_v776.INSTANCE)
            .updateSerializer(CommandBlockUpdatePacket.class, CommandBlockUpdateSerializer_v776.INSTANCE)
            .updateSerializer(CreativeContentPacket.class, CreativeContentSerializer_v776.INSTANCE)
            .updateSerializer(ItemComponentPacket.class, ItemComponentSerializer_v776.INSTANCE)
            .updateSerializer(StartGamePacket.class, StartGameSerializer_v776.INSTANCE)
            .updateSerializer(CameraPresetsPacket.class, CameraPresetsSerializer_v776.INSTANCE)
            .updateSerializer(StructureBlockUpdatePacket.class, StructureBlockUpdateSerializer_v776.INSTANCE)
            .registerPacket(CameraAimAssistInstructionPacket::new, CameraAimAssistInstructionSerializer_v776.INSTANCE, 321, PacketRecipient.SERVER)
            .registerPacket(MovementPredictionSyncPacket::new, MovementPredictionSyncSerializer_v776.INSTANCE, 322, PacketRecipient.SERVER)
            .build();
}