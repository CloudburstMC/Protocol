package org.cloudburstmc.protocol.bedrock.codec.v465;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodec;
import org.cloudburstmc.protocol.bedrock.codec.v291.serializer.EntityEventSerializer_v291;
import org.cloudburstmc.protocol.bedrock.codec.v291.serializer.LevelEventSerializer_v291;
import org.cloudburstmc.protocol.bedrock.codec.v291.serializer.LevelSoundEvent1Serializer_v291;
import org.cloudburstmc.protocol.bedrock.codec.v313.serializer.LevelSoundEvent2Serializer_v313;
import org.cloudburstmc.protocol.bedrock.codec.v332.serializer.LevelSoundEventSerializer_v332;
import org.cloudburstmc.protocol.bedrock.codec.v448.Bedrock_v448;
import org.cloudburstmc.protocol.bedrock.codec.v465.serializer.AddVolumeEntitySerializer_v465;
import org.cloudburstmc.protocol.bedrock.codec.v465.serializer.AnimateEntitySerializer_v465;
import org.cloudburstmc.protocol.bedrock.codec.v465.serializer.CraftingDataSerializer_v465;
import org.cloudburstmc.protocol.bedrock.codec.v465.serializer.CreatePhotoSerializer_v465;
import org.cloudburstmc.protocol.bedrock.codec.v465.serializer.EduUriResourceSerializer_v465;
import org.cloudburstmc.protocol.bedrock.codec.v465.serializer.EducationSettingsSerializer_v465;
import org.cloudburstmc.protocol.bedrock.codec.v465.serializer.EntityPickRequestSerializer_v465;
import org.cloudburstmc.protocol.bedrock.codec.v465.serializer.HurtArmorSerializer_v465;
import org.cloudburstmc.protocol.bedrock.codec.v465.serializer.PhotoTransferSerializer_v465;
import org.cloudburstmc.protocol.bedrock.codec.v465.serializer.StartGameSerializer_v465;
import org.cloudburstmc.protocol.bedrock.codec.v465.serializer.UpdateSubChunkBlocksSerializer_v465;
import org.cloudburstmc.protocol.bedrock.data.LevelEvent;
import org.cloudburstmc.protocol.bedrock.data.LevelEventType;
import org.cloudburstmc.protocol.bedrock.data.ParticleType;
import org.cloudburstmc.protocol.bedrock.data.SoundEvent;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityEventType;
import org.cloudburstmc.protocol.bedrock.packet.AddVolumeEntityPacket;
import org.cloudburstmc.protocol.bedrock.packet.AnimateEntityPacket;
import org.cloudburstmc.protocol.bedrock.packet.CraftingDataPacket;
import org.cloudburstmc.protocol.bedrock.packet.CreatePhotoPacket;
import org.cloudburstmc.protocol.bedrock.packet.EduUriResourcePacket;
import org.cloudburstmc.protocol.bedrock.packet.EducationSettingsPacket;
import org.cloudburstmc.protocol.bedrock.packet.EntityEventPacket;
import org.cloudburstmc.protocol.bedrock.packet.EntityPickRequestPacket;
import org.cloudburstmc.protocol.bedrock.packet.HurtArmorPacket;
import org.cloudburstmc.protocol.bedrock.packet.LevelEventPacket;
import org.cloudburstmc.protocol.bedrock.packet.LevelSoundEvent1Packet;
import org.cloudburstmc.protocol.bedrock.packet.LevelSoundEvent2Packet;
import org.cloudburstmc.protocol.bedrock.packet.LevelSoundEventPacket;
import org.cloudburstmc.protocol.bedrock.packet.PhotoTransferPacket;
import org.cloudburstmc.protocol.bedrock.packet.StartGamePacket;
import org.cloudburstmc.protocol.bedrock.packet.UpdateSubChunkBlocksPacket;
import org.cloudburstmc.protocol.common.util.TypeMap;

import java.util.Optional;
import java.util.OptionalDouble;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Bedrock_v465 extends Bedrock_v448 {

    protected static final TypeMap<EntityEventType> ENTITY_EVENTS = Bedrock_v448.ENTITY_EVENTS.toBuilder()
            .insert(76, EntityEventType.ENTITY_GROW_UP)
            .build();

    protected static final TypeMap<LevelEventType> LEVEL_EVENTS = Bedrock_v448.LEVEL_EVENTS.toBuilder()
            .insert(LEVEL_EVENT_PARTICLE + 34, LevelEvent.PARTICLE_TURTLE_EGG)
            .insert(LEVEL_EVENT_PARTICLE + 35, LevelEvent.PARTICLE_SKULK_SHRIEK)
            .insert(LEVEL_EVENT_PARTICLE_TYPE + 82, ParticleType.SHRIEK)
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
            .helper(() -> new BedrockCodecHelper_v465(ENTITY_DATA, ENTITY_DATA_TYPES, ENTITY_FLAGS, GAME_RULE_TYPES, ITEM_STACK_REQUEST_TYPES))
            .updateSerializer(StartGamePacket.class, StartGameSerializer_v465.INSTANCE)
            .updateSerializer(EntityEventPacket.class, new EntityEventSerializer_v291(ENTITY_EVENTS))
            .updateSerializer(LevelEventPacket.class, new LevelEventSerializer_v291(LEVEL_EVENTS))
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
            .registerPacket(EduUriResourcePacket.class, EduUriResourceSerializer_v465.INSTANCE, 170)
            .registerPacket(CreatePhotoPacket.class, CreatePhotoSerializer_v465.INSTANCE, 171)
            .registerPacket(UpdateSubChunkBlocksPacket.class, UpdateSubChunkBlocksSerializer_v465.INSTANCE, 172)
            .build();
}
