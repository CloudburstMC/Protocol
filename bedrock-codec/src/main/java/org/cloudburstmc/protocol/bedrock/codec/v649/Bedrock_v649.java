package org.cloudburstmc.protocol.bedrock.codec.v649;

import org.cloudburstmc.protocol.bedrock.codec.BedrockCodec;
import org.cloudburstmc.protocol.bedrock.codec.v291.serializer.LevelEventSerializer_v291;
import org.cloudburstmc.protocol.bedrock.codec.v361.serializer.LevelEventGenericSerializer_v361;
import org.cloudburstmc.protocol.bedrock.codec.v575.BedrockCodecHelper_v575;
import org.cloudburstmc.protocol.bedrock.codec.v622.Bedrock_v622;
import org.cloudburstmc.protocol.bedrock.codec.v630.Bedrock_v630;
import org.cloudburstmc.protocol.bedrock.codec.v649.serializer.*;
import org.cloudburstmc.protocol.bedrock.data.LevelEvent;
import org.cloudburstmc.protocol.bedrock.data.LevelEventType;
import org.cloudburstmc.protocol.bedrock.data.ParticleType;
import org.cloudburstmc.protocol.bedrock.packet.*;
import org.cloudburstmc.protocol.common.util.TypeMap;

public class Bedrock_v649 extends Bedrock_v630 {

    protected static final TypeMap<ParticleType> PARTICLE_TYPES = Bedrock_v622.PARTICLE_TYPES
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


    // TODO: check for command params

    public static final BedrockCodec CODEC = Bedrock_v630.CODEC.toBuilder()
            .raknetProtocolVersion(11)
            .protocolVersion(649)
            .minecraftVersion("1.20.60")
            .helper(() -> new BedrockCodecHelper_v575(ENTITY_DATA, GAME_RULE_TYPES, ITEM_STACK_REQUEST_TYPES, CONTAINER_SLOT_TYPES, PLAYER_ABILITIES, TEXT_PROCESSING_ORIGINS))
            .updateSerializer(LevelEventPacket.class, new LevelEventSerializer_v291(LEVEL_EVENTS))
            .updateSerializer(LevelEventGenericPacket.class, new LevelEventGenericSerializer_v361(LEVEL_EVENTS))
            .updateSerializer(CorrectPlayerMovePredictionPacket.class, CorrectPlayerMovePredictionSerializer_v649.INSTANCE)
            .updateSerializer(LevelChunkPacket.class, LevelChunkSerializer_v649.INSTANCE)
            .updateSerializer(PlayerAuthInputPacket.class, new PlayerAuthInputSerializer_v649())
            .updateSerializer(PlayerListPacket.class, PlayerListSerializer_v649.INSTANCE)
            .registerPacket(SetHudPacket::new, SetHudSerializer_v649.INSTANCE, 308)
            .build();
}