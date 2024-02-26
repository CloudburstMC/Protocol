package org.cloudburstmc.protocol.bedrock.codec.v649;

import org.cloudburstmc.protocol.bedrock.codec.BedrockCodec;
import org.cloudburstmc.protocol.bedrock.codec.v291.serializer.LevelEventSerializer_v291;
import org.cloudburstmc.protocol.bedrock.codec.v291.serializer.LevelSoundEvent1Serializer_v291;
import org.cloudburstmc.protocol.bedrock.codec.v313.serializer.LevelSoundEvent2Serializer_v313;
import org.cloudburstmc.protocol.bedrock.codec.v332.serializer.LevelSoundEventSerializer_v332;
import org.cloudburstmc.protocol.bedrock.codec.v361.serializer.LevelEventGenericSerializer_v361;
import org.cloudburstmc.protocol.bedrock.codec.v575.BedrockCodecHelper_v575;
import org.cloudburstmc.protocol.bedrock.codec.v630.Bedrock_v630;
import org.cloudburstmc.protocol.bedrock.codec.v649.serializer.*;
import org.cloudburstmc.protocol.bedrock.data.LevelEvent;
import org.cloudburstmc.protocol.bedrock.data.LevelEventType;
import org.cloudburstmc.protocol.bedrock.data.ParticleType;
import org.cloudburstmc.protocol.bedrock.data.SoundEvent;
import org.cloudburstmc.protocol.bedrock.packet.*;
import org.cloudburstmc.protocol.common.util.TypeMap;

public class Bedrock_v649 extends Bedrock_v630 {

    protected static final TypeMap<ParticleType> PARTICLE_TYPES = Bedrock_v630.PARTICLE_TYPES
            .toBuilder()
            .shift(18, 1)
            .insert(18, ParticleType.WIND_EXPLOSION)
            .build();

    protected static final TypeMap<LevelEventType> LEVEL_EVENTS = Bedrock_v630.LEVEL_EVENTS.toBuilder()
            .replace(LEVEL_EVENT_BLOCK + 109, LevelEvent.PARTICLE_SHOOT_WHITE_SMOKE)
            .replace(LEVEL_EVENT_BLOCK + 110, LevelEvent.PARTICLE_WIND_EXPLOSION)
            .insert(LEVEL_EVENT_BLOCK + 111, LevelEvent.PARTICLE_TRAIL_SPAWNER_DETECTION)
            .insert(LEVEL_EVENT_BLOCK + 112, LevelEvent.PARTICLE_TRAIL_SPAWNER_SPAWNING)
            .insert(LEVEL_EVENT_BLOCK + 113, LevelEvent.PARTICLE_TRAIL_SPAWNER_EJECTING)
            .insert(LEVEL_EVENT_BLOCK + 114, LevelEvent.ALL_PLAYERS_SLEEPING)
            .insert(LEVEL_EVENT_PARTICLE_TYPE, PARTICLE_TYPES)
            .build();

    protected static final TypeMap<SoundEvent> SOUND_EVENTS = Bedrock_v630.SOUND_EVENTS
            .toBuilder()
            .replace(492, SoundEvent.AMBIENT_IN_AIR)
            .insert(493, SoundEvent.WIND_BURST)
            .insert(494, SoundEvent.IMITATE_BREEZE)
            .insert(495, SoundEvent.ARMADILLO_BRUSH)
            .insert(496, SoundEvent.ARMADILLO_SCUTE_DROP)
            .insert(497, SoundEvent.EQUIP_WOLF)
            .insert(498, SoundEvent.UNEQUIP_WOLF)
            .insert(499, SoundEvent.REFLECT)
            .insert(500, SoundEvent.UNDEFINED)
            .build();

    public static final BedrockCodec CODEC = Bedrock_v630.CODEC.toBuilder()
            .raknetProtocolVersion(11)
            .protocolVersion(649)
            .minecraftVersion("1.20.60")
            .helper(() -> new BedrockCodecHelper_v575(ENTITY_DATA, GAME_RULE_TYPES, ITEM_STACK_REQUEST_TYPES, CONTAINER_SLOT_TYPES, PLAYER_ABILITIES, TEXT_PROCESSING_ORIGINS))
            .updateSerializer(LevelEventPacket.class, new LevelEventSerializer_v291(LEVEL_EVENTS))
            .updateSerializer(LevelEventGenericPacket.class, new LevelEventGenericSerializer_v361(LEVEL_EVENTS))
            .updateSerializer(LevelSoundEvent1Packet.class, new LevelSoundEvent1Serializer_v291(SOUND_EVENTS))
            .updateSerializer(LevelSoundEvent2Packet.class, new LevelSoundEvent2Serializer_v313(SOUND_EVENTS))
            .updateSerializer(LevelSoundEventPacket.class, new LevelSoundEventSerializer_v332(SOUND_EVENTS))
            .updateSerializer(CorrectPlayerMovePredictionPacket.class, CorrectPlayerMovePredictionSerializer_v649.INSTANCE)
            .updateSerializer(LevelChunkPacket.class, LevelChunkSerializer_v649.INSTANCE)
            .updateSerializer(PlayerAuthInputPacket.class, new PlayerAuthInputSerializer_v649())
            .updateSerializer(PlayerListPacket.class, PlayerListSerializer_v649.INSTANCE)
            .registerPacket(SetHudPacket::new, SetHudSerializer_v649.INSTANCE, 308)
            .build();
}
