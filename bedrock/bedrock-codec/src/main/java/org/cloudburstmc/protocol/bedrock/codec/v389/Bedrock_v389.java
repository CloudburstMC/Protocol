package org.cloudburstmc.protocol.bedrock.codec.v389;

import org.cloudburstmc.protocol.bedrock.codec.BedrockCodec;
import org.cloudburstmc.protocol.bedrock.codec.v291.serializer.LevelEventSerializer_v291;
import org.cloudburstmc.protocol.bedrock.codec.v388.Bedrock_v388;
import org.cloudburstmc.protocol.bedrock.codec.v389.serializer.EventSerializer_v389;
import org.cloudburstmc.protocol.bedrock.data.LevelEventType;
import org.cloudburstmc.protocol.bedrock.data.ParticleType;
import org.cloudburstmc.protocol.bedrock.packet.EventPacket;
import org.cloudburstmc.protocol.bedrock.packet.LevelEventPacket;
import org.cloudburstmc.protocol.common.util.TypeMap;

public class Bedrock_v389 extends Bedrock_v388 {

    protected static final TypeMap<LevelEventType> LEVEL_EVENTS = Bedrock_v388.LEVEL_EVENTS.toBuilder()
            .shift(LEVEL_EVENT_PARTICLE_TYPE + 28, 1)
            .insert(LEVEL_EVENT_PARTICLE_TYPE + 28, ParticleType.DRIP_HONEY)
            .insert(LEVEL_EVENT_PARTICLE_TYPE + 59, ParticleType.SHULKER_BULLET)
            .insert(LEVEL_EVENT_PARTICLE_TYPE + 60, ParticleType.BLEACH)
            .insert(LEVEL_EVENT_PARTICLE_TYPE + 61, ParticleType.DRAGON_DESTROY_BLOCK)
            .insert(LEVEL_EVENT_PARTICLE_TYPE + 62, ParticleType.MYCELIUM_DUST)
            .insert(LEVEL_EVENT_PARTICLE_TYPE + 63, ParticleType.FALLING_BORDER_DUST)
            .insert(LEVEL_EVENT_PARTICLE_TYPE + 64, ParticleType.CAMPFIRE_SMOKE)
            .insert(LEVEL_EVENT_PARTICLE_TYPE + 65, ParticleType.CAMPFIRE_SMOKE_TALL)
            .insert(LEVEL_EVENT_PARTICLE_TYPE + 66, ParticleType.DRAGON_BREATH_FIRE)
            .insert(LEVEL_EVENT_PARTICLE_TYPE + 67, ParticleType.DRAGON_BREATH_TRAIL)
            .build();

    public static BedrockCodec CODEC = Bedrock_v388.CODEC.toBuilder()
            .protocolVersion(389)
            .minecraftVersion("1.14.0")
            .updateSerializer(EventPacket.class, EventSerializer_v389.INSTANCE)
            .updateSerializer(LevelEventPacket.class, new LevelEventSerializer_v291(LEVEL_EVENTS))
            .build();
}
