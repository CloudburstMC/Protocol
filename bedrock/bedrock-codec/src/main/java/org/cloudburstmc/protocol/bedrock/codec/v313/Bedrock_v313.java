package org.cloudburstmc.protocol.bedrock.codec.v313;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodec;
import org.cloudburstmc.protocol.bedrock.codec.v291.Bedrock_v291;
import org.cloudburstmc.protocol.bedrock.codec.v291.serializer.EntityEventSerializer_v291;
import org.cloudburstmc.protocol.bedrock.codec.v291.serializer.LevelEventSerializer_v291;
import org.cloudburstmc.protocol.bedrock.codec.v291.serializer.LevelSoundEvent1Serializer_v291;
import org.cloudburstmc.protocol.bedrock.codec.v313.serializer.*;
import org.cloudburstmc.protocol.bedrock.data.LevelEvent;
import org.cloudburstmc.protocol.bedrock.data.LevelEventType;
import org.cloudburstmc.protocol.bedrock.data.ParticleType;
import org.cloudburstmc.protocol.bedrock.data.SoundEvent;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityData;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityEventType;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityFlag;
import org.cloudburstmc.protocol.bedrock.packet.*;
import org.cloudburstmc.protocol.common.util.TypeMap;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Bedrock_v313 extends Bedrock_v291 {

    protected static final TypeMap<EntityData> ENTITY_DATA = Bedrock_v291.ENTITY_DATA.toBuilder()
            .insert(88, EntityData.SITTING_AMOUNT)
            .insert(89, EntityData.SITTING_AMOUNT_PREVIOUS)
            .insert(90, EntityData.EATING_COUNTER)
            .insert(91, EntityData.FLAGS_2)
            .insert(92, EntityData.LAYING_AMOUNT)
            .insert(93, EntityData.LAYING_AMOUNT_PREVIOUS)
            .build();

    protected static final TypeMap<EntityFlag> ENTITY_FLAGS = Bedrock_v291.ENTITY_FLAGS.toBuilder()
            .insert(61, EntityFlag.TRANSITION_SITTING)
            .insert(62, EntityFlag.EATING)
            .insert(63, EntityFlag.LAYING_DOWN)
            .insert(64, EntityFlag.SNEEZING)
            .insert(65, EntityFlag.TRUSTING)
            .insert(66, EntityFlag.ROLLING)
            .insert(67, EntityFlag.SCARED)
            .insert(68, EntityFlag.IN_SCAFFOLDING)
            .insert(69, EntityFlag.OVER_SCAFFOLDING)
            .insert(70, EntityFlag.DESCEND_THROUGH_BLOCK)
            .build();

    protected static final TypeMap<SoundEvent> SOUND_EVENTS = Bedrock_v291.SOUND_EVENTS.toBuilder()
            .replace(239, SoundEvent.BLOCK_BAMBOO_SAPLING_PLACE)
            .insert(240, SoundEvent.PRE_SNEEZE)
            .insert(241, SoundEvent.SNEEZE)
            .insert(242, SoundEvent.AMBIENT_TAME)
            .insert(243, SoundEvent.SCARED)
            .insert(244, SoundEvent.BLOCK_SCAFFOLDING_CLIMB)
            .insert(245, SoundEvent.CROSSBOW_LOADING_START)
            .insert(246, SoundEvent.CROSSBOW_LOADING_MIDDLE)
            .insert(247, SoundEvent.CROSSBOW_LOADING_END)
            .insert(248, SoundEvent.CROSSBOW_SHOOT)
            .insert(249, SoundEvent.CROSSBOW_QUICK_CHARGE_START)
            .insert(250, SoundEvent.CROSSBOW_QUICK_CHARGE_MIDDLE)
            .insert(251, SoundEvent.CROSSBOW_QUICK_CHARGE_END)
            .insert(252, SoundEvent.AMBIENT_AGGRESSIVE)
            .insert(253, SoundEvent.AMBIENT_WORRIED)
            .insert(254, SoundEvent.CANT_BREED)
            .insert(255, SoundEvent.UNDEFINED)
            .build();

    protected static final TypeMap<EntityEventType> ENTITY_EVENTS = Bedrock_v291.ENTITY_EVENTS.toBuilder()
            .insert(73, EntityEventType.SUMMON_AGENT)
            .build();

    protected static final TypeMap<LevelEventType> LEVEL_EVENTS = Bedrock_v291.LEVEL_EVENTS.toBuilder()
            .insert(LEVEL_EVENT_BLOCK + 11, LevelEvent.AGENT_SPAWN_EFFECT)
            .insert(LEVEL_EVENT_PARTICLE_TYPE + 45, ParticleType.FIREWORKS_STARTER)
            .insert(LEVEL_EVENT_PARTICLE_TYPE + 46, ParticleType.FIREWORKS)
            .insert(LEVEL_EVENT_PARTICLE_TYPE + 47, ParticleType.FIREWORKS_OVERLAY)
            .insert(LEVEL_EVENT_PARTICLE_TYPE + 48, ParticleType.BALLOON_GAS)
            .insert(LEVEL_EVENT_PARTICLE_TYPE + 49, ParticleType.COLORED_FLAME)
            .insert(LEVEL_EVENT_PARTICLE_TYPE + 50, ParticleType.SPARKLER)
            .insert(LEVEL_EVENT_PARTICLE_TYPE + 51, ParticleType.CONDUIT)
            .insert(LEVEL_EVENT_PARTICLE_TYPE + 52, ParticleType.BUBBLE_COLUMN_UP)
            .insert(LEVEL_EVENT_PARTICLE_TYPE + 53, ParticleType.BUBBLE_COLUMN_DOWN)
            .insert(LEVEL_EVENT_PARTICLE_TYPE + 54, ParticleType.SNEEZE)
            .build();

    public static final BedrockCodec CODEC = Bedrock_v291.CODEC.toBuilder()
            .protocolVersion(313)
            .minecraftVersion("1.8.0")
            .helper(() -> new BedrockCodecHelper_v313(ENTITY_DATA, ENTITY_DATA_TYPES, ENTITY_FLAGS, GAME_RULE_TYPES))
            .updateSerializer(ResourcePackStackPacket.class, ResourcePackStackSerializer_v313.INSTANCE)
            .updateSerializer(StartGamePacket.class, StartGameSerializer_v313.INSTANCE)
            .updateSerializer(AddEntityPacket.class, AddEntitySerializer_v313.INSTANCE)
            .updateSerializer(UpdateTradePacket.class, UpdateTradeSerializer_v313.INSTANCE)
            .updateSerializer(EntityEventPacket.class, new EntityEventSerializer_v291(ENTITY_EVENTS))
            .updateSerializer(LevelSoundEvent1Packet.class, new LevelSoundEvent1Serializer_v291(SOUND_EVENTS))
            .updateSerializer(LevelEventPacket.class, new LevelEventSerializer_v291(LEVEL_EVENTS))
            .registerPacket(SpawnParticleEffectPacket.class, SpawnParticleEffectSerializer_v313.INSTANCE, 118)
            .registerPacket(AvailableEntityIdentifiersPacket.class, AvailableEntityIdentifiersSerializer_v313.INSTANCE, 119)
            .registerPacket(LevelSoundEvent2Packet.class, new LevelSoundEvent2Serializer_v313(SOUND_EVENTS), 120)
            .registerPacket(NetworkChunkPublisherUpdatePacket.class, NetworkChunkPublisherUpdateSerializer_v313.INSTANCE, 121)
            .registerPacket(BiomeDefinitionListPacket.class, BiomeDefinitionListSerializer_v313.INSTANCE, 122)
            .build();
}
