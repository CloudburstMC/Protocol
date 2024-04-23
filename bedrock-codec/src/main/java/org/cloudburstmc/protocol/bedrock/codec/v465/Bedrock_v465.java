package org.cloudburstmc.protocol.bedrock.codec.v465;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodec;
import org.cloudburstmc.protocol.bedrock.codec.EntityDataTypeMap;
import org.cloudburstmc.protocol.bedrock.codec.v291.serializer.EntityEventSerializer_v291;
import org.cloudburstmc.protocol.bedrock.codec.v291.serializer.LevelEventSerializer_v291;
import org.cloudburstmc.protocol.bedrock.codec.v291.serializer.LevelSoundEvent1Serializer_v291;
import org.cloudburstmc.protocol.bedrock.codec.v313.serializer.LevelSoundEvent2Serializer_v313;
import org.cloudburstmc.protocol.bedrock.codec.v332.serializer.LevelSoundEventSerializer_v332;
import org.cloudburstmc.protocol.bedrock.codec.v361.serializer.LevelEventGenericSerializer_v361;
import org.cloudburstmc.protocol.bedrock.codec.v448.Bedrock_v448;
import org.cloudburstmc.protocol.bedrock.codec.v465.serializer.*;
import org.cloudburstmc.protocol.bedrock.data.*;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityDataTypes;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityEventType;
import org.cloudburstmc.protocol.bedrock.packet.*;
import org.cloudburstmc.protocol.bedrock.transformer.TypeMapTransformer;
import org.cloudburstmc.protocol.common.util.TypeMap;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Bedrock_v465 extends Bedrock_v448 {

    protected static final TypeMap<ParticleType> PARTICLE_TYPES = Bedrock_v448.PARTICLE_TYPES.toBuilder()
            .insert(82, ParticleType.SHRIEK)
            .build();

    protected static final EntityDataTypeMap ENTITY_DATA = Bedrock_v448.ENTITY_DATA.toBuilder()
            .update(EntityDataTypes.AREA_EFFECT_CLOUD_PARTICLE, new TypeMapTransformer<>(PARTICLE_TYPES))
            .build();

    protected static final TypeMap<EntityEventType> ENTITY_EVENTS = Bedrock_v448.ENTITY_EVENTS.toBuilder()
            .insert(76, EntityEventType.ENTITY_GROW_UP)
            .build();

    protected static final TypeMap<LevelEventType> LEVEL_EVENTS = Bedrock_v448.LEVEL_EVENTS.toBuilder()
            .insert(LEVEL_EVENT_PARTICLE + 34, LevelEvent.PARTICLE_TURTLE_EGG)
            .insert(LEVEL_EVENT_PARTICLE + 35, LevelEvent.PARTICLE_SCULK_SHRIEK)
            .insert(LEVEL_EVENT_PARTICLE_TYPE, PARTICLE_TYPES)
            .build();

    protected static final TypeMap<SoundEvent> SOUND_EVENTS = Bedrock_v448.SOUND_EVENTS.toBuilder()
            .replace(363, SoundEvent.BLOCK_CLICK)
            .insert(364, SoundEvent.BLOCK_CLICK_FAIL)
            .insert(366, SoundEvent.SCULK_SHRIEKER_SHRIEK)
            .insert(367, SoundEvent.WARDEN_NEARBY_CLOSE)
            .insert(368, SoundEvent.WARDEN_NEARBY_CLOSER)
            .insert(369, SoundEvent.WARDEN_NEARBY_CLOSEST)
            .insert(370, SoundEvent.WARDEN_SLIGHTLY_ANGRY)
            .insert(371, SoundEvent.UNDEFINED)
            .build();

    public static final BedrockCodec CODEC = Bedrock_v448.CODEC.toBuilder()
            .protocolVersion(465)
            .minecraftVersion("1.17.30")
            .helper(() -> new BedrockCodecHelper_v465(ENTITY_DATA, GAME_RULE_TYPES, ITEM_STACK_REQUEST_TYPES, CONTAINER_SLOT_TYPES))
            .updateSerializer(StartGamePacket.class, StartGameSerializer_v465.INSTANCE)
            .updateSerializer(EntityEventPacket.class, new EntityEventSerializer_v291(ENTITY_EVENTS))
            .updateSerializer(LevelEventPacket.class, new LevelEventSerializer_v291(LEVEL_EVENTS))
            .updateSerializer(LevelEventGenericPacket.class, new LevelEventGenericSerializer_v361(LEVEL_EVENTS))
            .updateSerializer(LevelSoundEvent1Packet.class, new LevelSoundEvent1Serializer_v291(SOUND_EVENTS))
            .updateSerializer(LevelSoundEvent2Packet.class, new LevelSoundEvent2Serializer_v313(SOUND_EVENTS))
            .updateSerializer(LevelSoundEventPacket.class, new LevelSoundEventSerializer_v332(SOUND_EVENTS))
            .updateSerializer(EntityPickRequestPacket.class, EntityPickRequestSerializer_v465.INSTANCE)
            .updateSerializer(AddVolumeEntityPacket.class, AddVolumeEntitySerializer_v465.INSTANCE)
            .updateSerializer(AnimateEntityPacket.class, AnimateEntitySerializer_v465.INSTANCE)
            .updateSerializer(PhotoTransferPacket.class, PhotoTransferSerializer_v465.INSTANCE)
            .updateSerializer(EducationSettingsPacket.class, EducationSettingsSerializer_v465.INSTANCE)
            .updateSerializer(HurtArmorPacket.class, HurtArmorSerializer_v465.INSTANCE)
            .updateSerializer(CraftingDataPacket.class, CraftingDataSerializer_v465.INSTANCE)
            .registerPacket(EduUriResourcePacket::new, EduUriResourceSerializer_v465.INSTANCE, 170, PacketRecipient.CLIENT)
            .registerPacket(CreatePhotoPacket::new, CreatePhotoSerializer_v465.INSTANCE, 171, PacketRecipient.SERVER)
            .registerPacket(UpdateSubChunkBlocksPacket::new, UpdateSubChunkBlocksSerializer_v465.INSTANCE, 172, PacketRecipient.CLIENT)
            .build();
}
