package org.cloudburstmc.protocol.bedrock.codec.v757;

import org.cloudburstmc.protocol.bedrock.codec.BedrockCodec;
import org.cloudburstmc.protocol.bedrock.codec.v291.serializer.LevelEventSerializer_v291;
import org.cloudburstmc.protocol.bedrock.codec.v291.serializer.LevelSoundEvent1Serializer_v291;
import org.cloudburstmc.protocol.bedrock.codec.v313.serializer.LevelSoundEvent2Serializer_v313;
import org.cloudburstmc.protocol.bedrock.codec.v332.serializer.LevelSoundEventSerializer_v332;
import org.cloudburstmc.protocol.bedrock.codec.v361.serializer.LevelEventGenericSerializer_v361;
import org.cloudburstmc.protocol.bedrock.codec.v729.BedrockCodecHelper_v729;
import org.cloudburstmc.protocol.bedrock.codec.v748.Bedrock_v748;
import org.cloudburstmc.protocol.bedrock.codec.v757.serializer.CameraAimAssistPresetsSerializer_v757;
import org.cloudburstmc.protocol.bedrock.codec.v757.serializer.CameraAimAssistSerializer_v757;
import org.cloudburstmc.protocol.bedrock.codec.v757.serializer.ResourcePacksInfoSerializer_v757;
import org.cloudburstmc.protocol.bedrock.data.LevelEvent;
import org.cloudburstmc.protocol.bedrock.data.LevelEventType;
import org.cloudburstmc.protocol.bedrock.data.PacketRecipient;
import org.cloudburstmc.protocol.bedrock.data.SoundEvent;
import org.cloudburstmc.protocol.bedrock.packet.*;
import org.cloudburstmc.protocol.common.util.TypeMap;

public class Bedrock_v757 extends Bedrock_v748 {

    protected static final TypeMap<SoundEvent> SOUND_EVENTS = Bedrock_v748.SOUND_EVENTS
            .toBuilder()
            .insert(532, SoundEvent.IMITATE_CREAKING)
            .replace(534, SoundEvent.SPONGE_ABSORB)
            .insert(536, SoundEvent.BLOCK_CREAKING_HEART_TRAIL)
            .insert(537, SoundEvent.CREAKING_HEART_SPAWN)
            .insert(538, SoundEvent.ACTIVATE)
            .insert(539, SoundEvent.DEACTIVATE)
            .insert(540, SoundEvent.FREEZE)
            .insert(541, SoundEvent.UNFREEZE)
            .insert(542, SoundEvent.UNDEFINED)
            .build();

    protected static final TypeMap<LevelEventType> LEVEL_EVENTS = Bedrock_v748.LEVEL_EVENTS.toBuilder()
            .insert(9816, LevelEvent.PARTICLE_CREAKING_HEART_TRIAL)
            .insert(9817, LevelEvent.PARTICLE_CREAKING_TEARDOWN)
            .build();

    public static final BedrockCodec CODEC = Bedrock_v748.CODEC.toBuilder()
            .raknetProtocolVersion(11)
            .protocolVersion(757)
            .minecraftVersion("1.21.50.24")
            .helper(() -> new BedrockCodecHelper_v729(ENTITY_DATA, GAME_RULE_TYPES, ITEM_STACK_REQUEST_TYPES, CONTAINER_SLOT_TYPES, PLAYER_ABILITIES, TEXT_PROCESSING_ORIGINS))
            .updateSerializer(LevelEventPacket.class, new LevelEventSerializer_v291(LEVEL_EVENTS))
            .updateSerializer(LevelEventGenericPacket.class, new LevelEventGenericSerializer_v361(LEVEL_EVENTS))
            .updateSerializer(LevelSoundEvent1Packet.class, new LevelSoundEvent1Serializer_v291(SOUND_EVENTS))
            .updateSerializer(LevelSoundEvent2Packet.class, new LevelSoundEvent2Serializer_v313(SOUND_EVENTS))
            .updateSerializer(LevelSoundEventPacket.class, new LevelSoundEventSerializer_v332(SOUND_EVENTS))
            .updateSerializer(CameraAimAssistPacket.class, CameraAimAssistSerializer_v757.INSTANCE)
            .updateSerializer(ResourcePacksInfoPacket.class, ResourcePacksInfoSerializer_v757.INSTANCE)
            .registerPacket(CameraAimAssistPresetsPacket::new, CameraAimAssistPresetsSerializer_v757.INSTANCE, 320, PacketRecipient.CLIENT)
            .build();
}