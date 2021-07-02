package org.cloudburstmc.protocol.bedrock.codec.v448;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.v440.Bedrock_v440;
import org.cloudburstmc.protocol.bedrock.data.LevelEventType;
import org.cloudburstmc.protocol.bedrock.data.ParticleType;
import org.cloudburstmc.protocol.bedrock.data.SoundEvent;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityFlag;
import org.cloudburstmc.protocol.common.util.TypeMap;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Bedrock_v448 extends Bedrock_v440 {

    protected static final TypeMap<EntityFlag> ENTITY_FLAGS = Bedrock_v440.ENTITY_FLAGS.toBuilder()
            .insert(98, EntityFlag.IN_ASCENDABLE_BLOCK)
            .insert(99, EntityFlag.OVER_DESCENDABLE_BLOCK)
            .build();

    protected static final TypeMap<LevelEventType> LEVEL_EVENTS = Bedrock_v440.LEVEL_EVENTS.toBuilder()
            .shift(9, 1)
            .insert(9, ParticleType.CANDLE_FLAME)
            .build();

    protected static final TypeMap<SoundEvent> SOUND_EVENTS = Bedrock_v440.SOUND_EVENTS.toBuilder()
            .replace(360, SoundEvent.CAKE_ADD_CANDLE)
            .insert(361, SoundEvent.EXTINGUISH_CANDLE)
            .insert(362, SoundEvent.AMBIENT_CANDLE)
            .insert(363, SoundEvent.UNDEFINED)
            .build();
}
