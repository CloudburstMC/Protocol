package org.cloudburstmc.protocol.bedrock.codec.v827;

import org.cloudburstmc.protocol.bedrock.codec.BedrockCodec;
import org.cloudburstmc.protocol.bedrock.codec.v786.serializer.LevelSoundEventSerializer_v786;
import org.cloudburstmc.protocol.bedrock.codec.v819.Bedrock_v819;
import org.cloudburstmc.protocol.bedrock.codec.v827.serializer.*;
import org.cloudburstmc.protocol.bedrock.data.SoundEvent;
import org.cloudburstmc.protocol.bedrock.data.biome.BiomeDefinitionData;
import org.cloudburstmc.protocol.bedrock.packet.*;
import org.cloudburstmc.protocol.common.util.TypeMap;

public class Bedrock_v827 extends Bedrock_v819 {

    protected static final TypeMap<SoundEvent> SOUND_EVENTS = Bedrock_v819.SOUND_EVENTS
            .toBuilder()
            .replace(561, SoundEvent.EQUIP_COPPER)
            .replace(562, SoundEvent.RECORD_LAVA_CHICKEN)
            .insert(563, SoundEvent.UNDEFINED)
            .build();

    public static final BedrockCodec CODEC = Bedrock_v819.CODEC.toBuilder()
            .raknetProtocolVersion(11)
            .protocolVersion(827)
            .minecraftVersion("1.21.100")
            .updateSerializer(StartGamePacket.class, StartGameSerializer_v827.INSTANCE)
            .updateSerializer(CameraInstructionPacket.class, CameraInstructionSerializer_v827.INSTANCE)
            .updateSerializer(CorrectPlayerMovePredictionPacket.class, CorrectPlayerMovePredictionSerializer_v827.INSTANCE)
            .updateSerializer(CameraAimAssistPacket.class, CameraAimAssistSerializer_v827.INSTANCE)
            .updateSerializer(BiomeDefinitionListPacket.class, BiomeDefinitionListSerializer_v827.INSTANCE)
            .updateSerializer(LevelSoundEventPacket.class, new LevelSoundEventSerializer_v786(SOUND_EVENTS))
            .build();
}
