package org.cloudburstmc.protocol.bedrock.codec.v819;

import org.cloudburstmc.protocol.bedrock.codec.BedrockCodec;
import org.cloudburstmc.protocol.bedrock.codec.v786.serializer.LevelSoundEventSerializer_v786;
import org.cloudburstmc.protocol.bedrock.codec.v818.Bedrock_v818;
import org.cloudburstmc.protocol.bedrock.data.SoundEvent;
import org.cloudburstmc.protocol.bedrock.packet.LevelSoundEventPacket;
import org.cloudburstmc.protocol.common.util.TypeMap;

public class Bedrock_v819 extends Bedrock_v818 {

    protected static final TypeMap<SoundEvent> SOUND_EVENTS = Bedrock_v818.SOUND_EVENTS
            .toBuilder()
            .replace(561, SoundEvent.RECORD_LAVA_CHICKEN)
            .insert(562, SoundEvent.UNDEFINED)
            .build();

    public static final BedrockCodec CODEC = Bedrock_v818.CODEC.toBuilder()
            .minecraftVersion("1.21.93")
            .protocolVersion(819)
            .updateSerializer(LevelSoundEventPacket.class, new LevelSoundEventSerializer_v786(SOUND_EVENTS))
            .build();

}
