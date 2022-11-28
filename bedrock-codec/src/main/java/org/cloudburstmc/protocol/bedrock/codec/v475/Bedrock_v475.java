package org.cloudburstmc.protocol.bedrock.codec.v475;

import org.cloudburstmc.protocol.bedrock.codec.BedrockCodec;
import org.cloudburstmc.protocol.bedrock.codec.v291.serializer.LevelEventSerializer_v291;
import org.cloudburstmc.protocol.bedrock.codec.v291.serializer.LevelSoundEvent1Serializer_v291;
import org.cloudburstmc.protocol.bedrock.codec.v313.serializer.LevelSoundEvent2Serializer_v313;
import org.cloudburstmc.protocol.bedrock.codec.v332.serializer.LevelSoundEventSerializer_v332;
import org.cloudburstmc.protocol.bedrock.codec.v361.serializer.LevelEventGenericSerializer_v361;
import org.cloudburstmc.protocol.bedrock.codec.v465.BedrockCodecHelper_v465;
import org.cloudburstmc.protocol.bedrock.codec.v471.Bedrock_v471;
import org.cloudburstmc.protocol.bedrock.codec.v475.serializer.StartGameSerializer_v475;
import org.cloudburstmc.protocol.bedrock.codec.v475.serializer.SubChunkSerializer_v475;
import org.cloudburstmc.protocol.bedrock.data.LevelEvent;
import org.cloudburstmc.protocol.bedrock.data.LevelEventType;
import org.cloudburstmc.protocol.bedrock.data.SoundEvent;
import org.cloudburstmc.protocol.bedrock.packet.*;
import org.cloudburstmc.protocol.common.util.TypeMap;

public class Bedrock_v475 extends Bedrock_v471 {

    protected static final TypeMap<LevelEventType> LEVEL_EVENTS = Bedrock_v471.LEVEL_EVENTS.toBuilder()
            .insert(9801, LevelEvent.SLEEPING_PLAYERS)
            .build();

    protected static final TypeMap<SoundEvent> SOUND_EVENTS = Bedrock_v471.SOUND_EVENTS.toBuilder()
            .replace(371, SoundEvent.RECORD_OTHERSIDE)
            .insert(372, SoundEvent.UNDEFINED)
            .build();

    public static final BedrockCodec CODEC = Bedrock_v471.CODEC.toBuilder()
            .protocolVersion(475)
            .minecraftVersion("1.18.0")
            .helper(() -> new BedrockCodecHelper_v465(ENTITY_DATA, GAME_RULE_TYPES, ITEM_STACK_REQUEST_TYPES, CONTAINER_SLOT_TYPES))
            .updateSerializer(StartGamePacket.class, StartGameSerializer_v475.INSTANCE)
            .updateSerializer(LevelEventPacket.class, new LevelEventSerializer_v291(LEVEL_EVENTS))
            .updateSerializer(LevelEventGenericPacket.class, new LevelEventGenericSerializer_v361(LEVEL_EVENTS))
            .updateSerializer(LevelSoundEvent1Packet.class, new LevelSoundEvent1Serializer_v291(SOUND_EVENTS))
            .updateSerializer(LevelSoundEvent2Packet.class, new LevelSoundEvent2Serializer_v313(SOUND_EVENTS))
            .updateSerializer(LevelSoundEventPacket.class, new LevelSoundEventSerializer_v332(SOUND_EVENTS))
            .updateSerializer(SubChunkPacket.class, SubChunkSerializer_v475.INSTANCE)
            .build();
}
