package org.cloudburstmc.protocol.bedrock.codec.v575;

import org.cloudburstmc.protocol.bedrock.codec.BedrockCodec;
import org.cloudburstmc.protocol.bedrock.codec.EntityDataTypeMap;
import org.cloudburstmc.protocol.bedrock.codec.v291.serializer.LevelEventSerializer_v291;
import org.cloudburstmc.protocol.bedrock.codec.v291.serializer.LevelSoundEvent1Serializer_v291;
import org.cloudburstmc.protocol.bedrock.codec.v313.serializer.LevelSoundEvent2Serializer_v313;
import org.cloudburstmc.protocol.bedrock.codec.v332.serializer.LevelSoundEventSerializer_v332;
import org.cloudburstmc.protocol.bedrock.codec.v361.serializer.LevelEventGenericSerializer_v361;
import org.cloudburstmc.protocol.bedrock.codec.v568.Bedrock_v568;
import org.cloudburstmc.protocol.bedrock.codec.v575.serializer.CameraInstructionSerializer_v575;
import org.cloudburstmc.protocol.bedrock.codec.v575.serializer.CameraPresetsSerializer_v575;
import org.cloudburstmc.protocol.bedrock.codec.v575.serializer.PlayerAuthInputSerializer_v575;
import org.cloudburstmc.protocol.bedrock.codec.v575.serializer.UnlockedRecipesSerializer_v575;
import org.cloudburstmc.protocol.bedrock.data.Ability;
import org.cloudburstmc.protocol.bedrock.data.LevelEventType;
import org.cloudburstmc.protocol.bedrock.data.ParticleType;
import org.cloudburstmc.protocol.bedrock.data.SoundEvent;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityDataTypes;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityFlag;
import org.cloudburstmc.protocol.bedrock.packet.*;
import org.cloudburstmc.protocol.bedrock.transformer.FlagTransformer;
import org.cloudburstmc.protocol.common.util.TypeMap;

public class Bedrock_v575 extends Bedrock_v568 {

    protected static final TypeMap<Ability> PLAYER_ABILITIES = Bedrock_v568.PLAYER_ABILITIES
            .toBuilder()
            .insert(18, Ability.PRIVILEGED_BUILDER)
            .build();

    protected static final TypeMap<EntityFlag> ENTITY_FLAGS = Bedrock_v568.ENTITY_FLAGS
            .toBuilder()
            .insert(110, EntityFlag.SCENTING)
            .insert(111, EntityFlag.RISING)
            .insert(112, EntityFlag.FEELING_HAPPY)
            .insert(113, EntityFlag.SEARCHING)
            .build();

    protected static final EntityDataTypeMap ENTITY_DATA = Bedrock_v568.ENTITY_DATA
            .toBuilder()
            .update(EntityDataTypes.FLAGS, new FlagTransformer(ENTITY_FLAGS, 0))
            .update(EntityDataTypes.FLAGS_2, new FlagTransformer(ENTITY_FLAGS, 1))
            .build();

    protected static final TypeMap<SoundEvent> SOUND_EVENTS = Bedrock_v568.SOUND_EVENTS
            .toBuilder()
            .replace(462, SoundEvent.BRUSH)
            .insert(463, SoundEvent.BRUSH_COMPLETED)
            .insert(464, SoundEvent.SHATTER_DECORATED_POT)
            .insert(465, SoundEvent.BREAK_DECORATED_POD)
            .insert(466, SoundEvent.UNDEFINED)
            .build();

    protected static final TypeMap<ParticleType> PARTICLE_TYPES = Bedrock_v568.PARTICLE_TYPES
            .toBuilder()
            .insert(85, ParticleType.BRUSH_DUST)
            .build();

    protected static final TypeMap<LevelEventType> LEVEL_EVENTS = Bedrock_v568.LEVEL_EVENTS.toBuilder()
            .insert(LEVEL_EVENT_PARTICLE_TYPE, PARTICLE_TYPES)
            .build();

    public static final BedrockCodec CODEC = Bedrock_v568.CODEC.toBuilder()
            .raknetProtocolVersion(11)
            .protocolVersion(575)
            .minecraftVersion("1.19.70")
            .helper(() -> new BedrockCodecHelper_v575(ENTITY_DATA, GAME_RULE_TYPES, ITEM_STACK_REQUEST_TYPES, CONTAINER_SLOT_TYPES, PLAYER_ABILITIES))
            .updateSerializer(LevelSoundEvent1Packet.class, new LevelSoundEvent1Serializer_v291(SOUND_EVENTS))
            .updateSerializer(LevelSoundEvent2Packet.class, new LevelSoundEvent2Serializer_v313(SOUND_EVENTS))
            .updateSerializer(LevelSoundEventPacket.class, new LevelSoundEventSerializer_v332(SOUND_EVENTS))
            .updateSerializer(LevelEventPacket.class, new LevelEventSerializer_v291(LEVEL_EVENTS))
            .updateSerializer(LevelEventGenericPacket.class, new LevelEventGenericSerializer_v361(LEVEL_EVENTS))
            .updateSerializer(PlayerAuthInputPacket.class, new PlayerAuthInputSerializer_v575())
            .registerPacket(CameraPresetsPacket::new, new CameraPresetsSerializer_v575(), 198)
            .registerPacket(UnlockedRecipesPacket::new, new UnlockedRecipesSerializer_v575(), 199)
            .registerPacket(CameraInstructionPacket::new, new CameraInstructionSerializer_v575(), 300)
            .build();
}
