package org.cloudburstmc.protocol.bedrock.codec.v618;

import org.cloudburstmc.protocol.bedrock.codec.BedrockCodec;
import org.cloudburstmc.protocol.bedrock.codec.EntityDataTypeMap;
import org.cloudburstmc.protocol.bedrock.codec.v291.serializer.LevelEventSerializer_v291;
import org.cloudburstmc.protocol.bedrock.codec.v291.serializer.LevelSoundEvent1Serializer_v291;
import org.cloudburstmc.protocol.bedrock.codec.v313.serializer.LevelSoundEvent2Serializer_v313;
import org.cloudburstmc.protocol.bedrock.codec.v332.serializer.LevelSoundEventSerializer_v332;
import org.cloudburstmc.protocol.bedrock.codec.v361.serializer.LevelEventGenericSerializer_v361;
import org.cloudburstmc.protocol.bedrock.codec.v575.BedrockCodecHelper_v575;
import org.cloudburstmc.protocol.bedrock.codec.v594.Bedrock_v594;
import org.cloudburstmc.protocol.bedrock.codec.v618.serializer.CameraInstructionSerializer_618;
import org.cloudburstmc.protocol.bedrock.codec.v618.serializer.CameraPresetsSerializer_v618;
import org.cloudburstmc.protocol.bedrock.codec.v618.serializer.RefreshEntitlementsSerializer_v618;
import org.cloudburstmc.protocol.bedrock.codec.v618.serializer.ResourcePacksInfoSerializer_v618;
import org.cloudburstmc.protocol.bedrock.data.LevelEventType;
import org.cloudburstmc.protocol.bedrock.data.ParticleType;
import org.cloudburstmc.protocol.bedrock.data.SoundEvent;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityDataTypes;
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.TextProcessingEventOrigin;
import org.cloudburstmc.protocol.bedrock.packet.*;
import org.cloudburstmc.protocol.bedrock.transformer.TypeMapTransformer;
import org.cloudburstmc.protocol.common.util.TypeMap;

public class Bedrock_v618 extends Bedrock_v594 {

    protected static final TypeMap<SoundEvent> SOUND_EVENTS = Bedrock_v594.SOUND_EVENTS
            .toBuilder()
            .replace(470, SoundEvent.BUMP)
            .insert(471, SoundEvent.PUMPKIN_CARVE)
            .insert(472, SoundEvent.CONVERT_HUSK_TO_ZOMBIE)
            .insert(473, SoundEvent.PIG_DEATH)
            .insert(474, SoundEvent.HOGLIN_CONVERT_TO_ZOMBIE)
            .insert(475, SoundEvent.AMBIENT_UNDERWATER_ENTER)
            .insert(476, SoundEvent.AMBIENT_UNDERWATER_EXIT)
            .insert(477, SoundEvent.UNDEFINED)
            .build();

    protected static final TypeMap<TextProcessingEventOrigin> TEXT_PROCESSING_ORIGINS = Bedrock_v594.TEXT_PROCESSING_ORIGINS
            .toBuilder()
            .insert(14, TextProcessingEventOrigin.PASS_THROUGH_WITHOUT_SIFT)
            .build();

    protected static final TypeMap<ParticleType> PARTICLE_TYPES = Bedrock_v594.PARTICLE_TYPES
            .toBuilder()
            .insert(86, ParticleType.CHERRY_LEAVES)
            .build();

    protected static final TypeMap<LevelEventType> LEVEL_EVENTS = Bedrock_v594.LEVEL_EVENTS.toBuilder()
            .insert(LEVEL_EVENT_PARTICLE_TYPE, PARTICLE_TYPES)
            .build();

    protected static final EntityDataTypeMap ENTITY_DATA = Bedrock_v594.ENTITY_DATA
            .toBuilder()
            .update(EntityDataTypes.AREA_EFFECT_CLOUD_PARTICLE, new TypeMapTransformer<>(PARTICLE_TYPES))
            .build();

    public static final BedrockCodec CODEC = Bedrock_v594.CODEC.toBuilder()
            .raknetProtocolVersion(11)
            .protocolVersion(618)
            .minecraftVersion("1.20.30")
            .helper(() -> new BedrockCodecHelper_v575(ENTITY_DATA, GAME_RULE_TYPES, ITEM_STACK_REQUEST_TYPES, CONTAINER_SLOT_TYPES, PLAYER_ABILITIES, TEXT_PROCESSING_ORIGINS))
            .updateSerializer(LevelSoundEvent1Packet.class, new LevelSoundEvent1Serializer_v291(SOUND_EVENTS))
            .updateSerializer(LevelSoundEvent2Packet.class, new LevelSoundEvent2Serializer_v313(SOUND_EVENTS))
            .updateSerializer(LevelSoundEventPacket.class, new LevelSoundEventSerializer_v332(SOUND_EVENTS))
            .updateSerializer(LevelEventPacket.class, new LevelEventSerializer_v291(LEVEL_EVENTS))
            .updateSerializer(LevelEventGenericPacket.class, new LevelEventGenericSerializer_v361(LEVEL_EVENTS))
            .updateSerializer(ResourcePacksInfoPacket.class, ResourcePacksInfoSerializer_v618.INSTANCE)
            .updateSerializer(CameraPresetsPacket.class, new CameraPresetsSerializer_v618())
            .updateSerializer(CameraInstructionPacket.class, new CameraInstructionSerializer_618())
            .registerPacket(RefreshEntitlementsPacket::new, new RefreshEntitlementsSerializer_v618(), 305)
            .build();
}