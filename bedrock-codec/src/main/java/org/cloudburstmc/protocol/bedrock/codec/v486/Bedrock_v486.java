package org.cloudburstmc.protocol.bedrock.codec.v486;

import org.cloudburstmc.protocol.bedrock.codec.BedrockCodec;
import org.cloudburstmc.protocol.bedrock.codec.EntityDataTypeMap;
import org.cloudburstmc.protocol.bedrock.codec.v291.serializer.LevelSoundEvent1Serializer_v291;
import org.cloudburstmc.protocol.bedrock.codec.v313.serializer.LevelSoundEvent2Serializer_v313;
import org.cloudburstmc.protocol.bedrock.codec.v332.serializer.LevelSoundEventSerializer_v332;
import org.cloudburstmc.protocol.bedrock.codec.v465.BedrockCodecHelper_v465;
import org.cloudburstmc.protocol.bedrock.codec.v475.Bedrock_v475;
import org.cloudburstmc.protocol.bedrock.codec.v486.serializer.*;
import org.cloudburstmc.protocol.bedrock.data.PacketRecipient;
import org.cloudburstmc.protocol.bedrock.data.SoundEvent;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityDataTypes;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityFlag;
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.action.ItemStackRequestActionType;
import org.cloudburstmc.protocol.bedrock.packet.*;
import org.cloudburstmc.protocol.bedrock.transformer.FlagTransformer;
import org.cloudburstmc.protocol.common.util.TypeMap;

public class Bedrock_v486 extends Bedrock_v475 {

    protected static final TypeMap<EntityFlag> ENTITY_FLAGS = Bedrock_v475.ENTITY_FLAGS.toBuilder()
            .insert(100, EntityFlag.CROAKING)
            .insert(101, EntityFlag.EAT_MOB)
            .build();

    protected static final EntityDataTypeMap ENTITY_DATA = Bedrock_v475.ENTITY_DATA.toBuilder()
            .update(EntityDataTypes.FLAGS, new FlagTransformer(ENTITY_FLAGS, 0))
            .update(EntityDataTypes.FLAGS_2, new FlagTransformer(ENTITY_FLAGS, 1))
            .build();

    protected static final TypeMap<ItemStackRequestActionType> ITEM_STACK_REQUEST_TYPES = Bedrock_v475.ITEM_STACK_REQUEST_TYPES.toBuilder()
            .shift(7, 2)
            .insert(7, ItemStackRequestActionType.PLACE_IN_ITEM_CONTAINER)
            .insert(8, ItemStackRequestActionType.TAKE_FROM_ITEM_CONTAINER)
            .build();

    protected static final TypeMap<SoundEvent> SOUND_EVENTS = Bedrock_v475.SOUND_EVENTS.toBuilder()
            .replace(372, SoundEvent.TONGUE)
            .insert(373, SoundEvent.CRACK_IRON_GOLEM)
            .insert(374, SoundEvent.REPAIR_IRON_GOLEM)
            .insert(375, SoundEvent.UNDEFINED)
            .build();

    public static final BedrockCodec CODEC = Bedrock_v475.CODEC.toBuilder()
            .protocolVersion(486)
            .minecraftVersion("1.18.10")
            .helper(() -> new BedrockCodecHelper_v465(ENTITY_DATA, GAME_RULE_TYPES, ITEM_STACK_REQUEST_TYPES, CONTAINER_SLOT_TYPES))
            .updateSerializer(AddVolumeEntityPacket.class, AddVolumeEntitySerializer_v486.INSTANCE)
            .updateSerializer(BossEventPacket.class, BossEventSerializer_v486.INSTANCE)
            .updateSerializer(LevelSoundEvent1Packet.class, new LevelSoundEvent1Serializer_v291(SOUND_EVENTS))
            .updateSerializer(LevelSoundEvent2Packet.class, new LevelSoundEvent2Serializer_v313(SOUND_EVENTS))
            .updateSerializer(LevelSoundEventPacket.class, new LevelSoundEventSerializer_v332(SOUND_EVENTS))
            .updateSerializer(LevelChunkPacket.class, LevelChunkSerializer_v486.INSTANCE)
            .updateSerializer(SubChunkPacket.class, SubChunkSerializer_v486.INSTANCE)
            .updateSerializer(SubChunkRequestPacket.class, SubChunkRequestSerializer_v486.INSTANCE)
            .registerPacket(PlayerStartItemCooldownPacket::new, PlayerStartItemCooldownSerializer_v486.INSTANCE, 176, PacketRecipient.CLIENT)
            .registerPacket(ScriptMessagePacket::new, ScriptMessageSerializer_v486.INSTANCE, 177, PacketRecipient.BOTH)
            .registerPacket(CodeBuilderSourcePacket::new, CodeBuilderSourceSerializer_v486.INSTANCE, 178, PacketRecipient.CLIENT)
            .build();
}
