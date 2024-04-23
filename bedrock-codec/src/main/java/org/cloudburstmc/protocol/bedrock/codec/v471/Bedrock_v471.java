package org.cloudburstmc.protocol.bedrock.codec.v471;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodec;
import org.cloudburstmc.protocol.bedrock.codec.EntityDataTypeMap;
import org.cloudburstmc.protocol.bedrock.codec.v291.serializer.LevelEventSerializer_v291;
import org.cloudburstmc.protocol.bedrock.codec.v291.serializer.LevelSoundEvent1Serializer_v291;
import org.cloudburstmc.protocol.bedrock.codec.v313.serializer.LevelSoundEvent2Serializer_v313;
import org.cloudburstmc.protocol.bedrock.codec.v332.serializer.LevelSoundEventSerializer_v332;
import org.cloudburstmc.protocol.bedrock.codec.v361.serializer.LevelEventGenericSerializer_v361;
import org.cloudburstmc.protocol.bedrock.codec.v465.Bedrock_v465;
import org.cloudburstmc.protocol.bedrock.codec.v471.serializer.EventSerializer_v471;
import org.cloudburstmc.protocol.bedrock.codec.v471.serializer.PhotoInfoRequestSerializer_v471;
import org.cloudburstmc.protocol.bedrock.codec.v471.serializer.SubChunkRequestSerializer_v471;
import org.cloudburstmc.protocol.bedrock.codec.v471.serializer.SubChunkSerializer_v471;
import org.cloudburstmc.protocol.bedrock.data.*;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityDataTypes;
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.action.ItemStackRequestActionType;
import org.cloudburstmc.protocol.bedrock.packet.*;
import org.cloudburstmc.protocol.bedrock.transformer.TypeMapTransformer;
import org.cloudburstmc.protocol.common.util.TypeMap;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Bedrock_v471 extends Bedrock_v465 {

    protected static final TypeMap<ParticleType> PARTICLE_TYPES = Bedrock_v465.PARTICLE_TYPES.toBuilder()
            .insert(83, ParticleType.SCULK_SOUL)
            .build();

    protected static final EntityDataTypeMap ENTITY_DATA = Bedrock_v465.ENTITY_DATA.toBuilder()
            .update(EntityDataTypes.AREA_EFFECT_CLOUD_PARTICLE, new TypeMapTransformer<>(PARTICLE_TYPES))
            .build();

    protected static final TypeMap<ItemStackRequestActionType> ITEM_STACK_REQUEST_TYPES = Bedrock_v465.ITEM_STACK_REQUEST_TYPES.toBuilder()
            .shift(14, 2)
            .insert(14, ItemStackRequestActionType.CRAFT_REPAIR_AND_DISENCHANT)
            .insert(15, ItemStackRequestActionType.CRAFT_LOOM)
            .build();

    protected static final TypeMap<LevelEventType> LEVEL_EVENTS = Bedrock_v465.LEVEL_EVENTS.toBuilder()
            .insert(LEVEL_EVENT_PARTICLE + 36, LevelEvent.SCULK_CATALYST_BLOOM)
            .insert(LEVEL_EVENT_PARTICLE_TYPE, PARTICLE_TYPES)
            .build();

    protected static final TypeMap<SoundEvent> SOUND_EVENTS = Bedrock_v465.SOUND_EVENTS.toBuilder()
            .insert(365, SoundEvent.SCULK_CATALYST_BLOOM)
            .build();

    public static final BedrockCodec CODEC = Bedrock_v465.CODEC.toBuilder()
            .protocolVersion(471)
            .minecraftVersion("1.17.40")
            .helper(() -> new BedrockCodecHelper_v471(ENTITY_DATA, GAME_RULE_TYPES, ITEM_STACK_REQUEST_TYPES, CONTAINER_SLOT_TYPES))
            .updateSerializer(EventPacket.class, EventSerializer_v471.INSTANCE)
            .updateSerializer(LevelEventPacket.class, new LevelEventSerializer_v291(LEVEL_EVENTS))
            .updateSerializer(LevelEventGenericPacket.class, new LevelEventGenericSerializer_v361(LEVEL_EVENTS))
            .updateSerializer(LevelSoundEvent1Packet.class, new LevelSoundEvent1Serializer_v291(SOUND_EVENTS))
            .updateSerializer(LevelSoundEvent2Packet.class, new LevelSoundEvent2Serializer_v313(SOUND_EVENTS))
            .updateSerializer(LevelSoundEventPacket.class, new LevelSoundEventSerializer_v332(SOUND_EVENTS))
            .registerPacket(PhotoInfoRequestPacket::new, PhotoInfoRequestSerializer_v471.INSTANCE, 173, PacketRecipient.SERVER)
            .registerPacket(SubChunkPacket::new, SubChunkSerializer_v471.INSTANCE, 174, PacketRecipient.CLIENT)
            .registerPacket(SubChunkRequestPacket::new, SubChunkRequestSerializer_v471.INSTANCE, 175, PacketRecipient.SERVER)
            .build();
}
