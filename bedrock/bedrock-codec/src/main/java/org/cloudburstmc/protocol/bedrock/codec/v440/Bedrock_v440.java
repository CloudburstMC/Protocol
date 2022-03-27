package org.cloudburstmc.protocol.bedrock.codec.v440;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodec;
import org.cloudburstmc.protocol.bedrock.codec.v291.serializer.LevelEventSerializer_v291;
import org.cloudburstmc.protocol.bedrock.codec.v291.serializer.LevelSoundEvent1Serializer_v291;
import org.cloudburstmc.protocol.bedrock.codec.v313.serializer.LevelSoundEvent2Serializer_v313;
import org.cloudburstmc.protocol.bedrock.codec.v332.serializer.LevelSoundEventSerializer_v332;
import org.cloudburstmc.protocol.bedrock.codec.v422.Bedrock_v422;
import org.cloudburstmc.protocol.bedrock.codec.v431.Bedrock_v431;
import org.cloudburstmc.protocol.bedrock.codec.v440.serializer.AddVolumeEntitySerializer_v440;
import org.cloudburstmc.protocol.bedrock.codec.v440.serializer.RemoveVolumeEntitySerializer_v440;
import org.cloudburstmc.protocol.bedrock.codec.v440.serializer.StartGameSerializer_v440;
import org.cloudburstmc.protocol.bedrock.codec.v440.serializer.SyncEntityPropertySerializer_v440;
import org.cloudburstmc.protocol.bedrock.data.LevelEventType;
import org.cloudburstmc.protocol.bedrock.data.ParticleType;
import org.cloudburstmc.protocol.bedrock.data.SoundEvent;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityData;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityFlag;
import org.cloudburstmc.protocol.bedrock.packet.AddVolumeEntityPacket;
import org.cloudburstmc.protocol.bedrock.packet.LevelEventPacket;
import org.cloudburstmc.protocol.bedrock.packet.LevelSoundEvent1Packet;
import org.cloudburstmc.protocol.bedrock.packet.LevelSoundEvent2Packet;
import org.cloudburstmc.protocol.bedrock.packet.LevelSoundEventPacket;
import org.cloudburstmc.protocol.bedrock.packet.RemoveVolumeEntityPacket;
import org.cloudburstmc.protocol.bedrock.packet.StartGamePacket;
import org.cloudburstmc.protocol.bedrock.packet.SyncEntityPropertyPacket;
import org.cloudburstmc.protocol.common.util.TypeMap;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Bedrock_v440 extends Bedrock_v431 {

    protected static final TypeMap<EntityData> ENTITY_DATA = Bedrock_v431.ENTITY_DATA.toBuilder()
            .replace(120, EntityData.BASE_RUNTIME_ID)
            .replace(121, EntityData.FREEZING_EFFECT_STRENGTH)
            .replace(122, EntityData.BUOYANCY_DATA)
            .replace(123, EntityData.GOAT_HORN_COUNT)
            .insert(124, EntityData.UPDATE_PROPERTIES)
            .build();

    protected static final TypeMap<EntityFlag> ENTITY_FLAGS = Bedrock_v431.ENTITY_FLAGS.toBuilder()
            .insert(97, EntityFlag.PLAYING_DEAD)
            .build();

    protected static final TypeMap<LevelEventType> LEVEL_EVENTS = Bedrock_v431.LEVEL_EVENTS.toBuilder()
            .insert(LEVEL_EVENT_PARTICLE_TYPE + 73, ParticleType.PORTAL_REVERSE)
            .insert(LEVEL_EVENT_PARTICLE_TYPE + 74, ParticleType.SNOWFLAKE)
            .insert(LEVEL_EVENT_PARTICLE_TYPE + 75, ParticleType.VIBRATION_SIGNAL)
            .insert(LEVEL_EVENT_PARTICLE_TYPE + 76, ParticleType.SCULK_SENSOR_REDSTONE)
            .insert(LEVEL_EVENT_PARTICLE_TYPE + 77, ParticleType.SPORE_BLOSSOM_SHOWER)
            .insert(LEVEL_EVENT_PARTICLE_TYPE + 78, ParticleType.SPORE_BLOSSOM_AMBIENT)
            .insert(LEVEL_EVENT_PARTICLE_TYPE + 79, ParticleType.WAX)
            .insert(LEVEL_EVENT_PARTICLE_TYPE + 80, ParticleType.ELECTRIC_SPARK)
            .build();

    protected static final TypeMap<SoundEvent> SOUND_EVENTS = Bedrock_v431.SOUND_EVENTS.toBuilder()
            .replace(339, SoundEvent.COPPER_WAX_ON)
            .insert(340, SoundEvent.COPPER_WAX_OFF)
            .insert(341, SoundEvent.SCRAPE)
            .insert(342, SoundEvent.PLAYER_HURT_DROWN)
            .insert(343, SoundEvent.PLAYER_HURT_ON_FIRE)
            .insert(344, SoundEvent.PLAYER_HURT_FREEZE)
            .insert(345, SoundEvent.USE_SPYGLASS)
            .insert(346, SoundEvent.STOP_USING_SPYGLASS)
            .insert(347, SoundEvent.AMETHYST_BLOCK_CHIME)
            .insert(348, SoundEvent.AMBIENT_SCREAMER)
            .insert(349, SoundEvent.HURT_SCREAMER)
            .insert(350, SoundEvent.DEATH_SCREAMER)
            .insert(351, SoundEvent.MILK_SCREAMER)
            .insert(352, SoundEvent.JUMP_TO_BLOCK)
            .insert(353, SoundEvent.PRE_RAM)
            .insert(354, SoundEvent.PRE_RAM_SCREAMER)
            .insert(355, SoundEvent.RAM_IMPACT)
            .insert(356, SoundEvent.RAM_IMPACT_SCREAMER)
            .insert(357, SoundEvent.SQUID_INK_SQUIRT)
            .insert(358, SoundEvent.GLOW_SQUID_INK_SQUIRT)
            .insert(359, SoundEvent.CONVERT_TO_STRAY)
            .insert(360, SoundEvent.UNDEFINED)
            .build();

    public static final BedrockCodec CODEC = Bedrock_v422.CODEC.toBuilder()
            .protocolVersion(440)
            .minecraftVersion("1.17.0")
            .helper(() -> new BedrockCodecHelper_v440(ENTITY_DATA, ENTITY_DATA_TYPES, ENTITY_FLAGS, GAME_RULE_TYPES, ITEM_STACK_REQUEST_TYPES))
            .updateSerializer(StartGamePacket.class, StartGameSerializer_v440.INSTANCE)
            .updateSerializer(LevelSoundEvent1Packet.class, new LevelSoundEvent1Serializer_v291(SOUND_EVENTS))
            .updateSerializer(LevelSoundEvent2Packet.class, new LevelSoundEvent2Serializer_v313(SOUND_EVENTS))
            .updateSerializer(LevelSoundEventPacket.class, new LevelSoundEventSerializer_v332(SOUND_EVENTS))
            .updateSerializer(LevelEventPacket.class, new LevelEventSerializer_v291(LEVEL_EVENTS))
            .registerPacket(SyncEntityPropertyPacket.class, SyncEntityPropertySerializer_v440.INSTANCE, 165)
            .registerPacket(AddVolumeEntityPacket.class, AddVolumeEntitySerializer_v440.INSTANCE, 166)
            .registerPacket(RemoveVolumeEntityPacket.class, RemoveVolumeEntitySerializer_v440.INSTANCE, 167)
            .build();
}
