package org.cloudburstmc.protocol.bedrock.codec.v843;

import org.cloudburstmc.protocol.bedrock.codec.BedrockCodec;
import org.cloudburstmc.protocol.bedrock.codec.v291.serializer.LevelEventSerializer_v291;
import org.cloudburstmc.protocol.bedrock.codec.v361.serializer.LevelEventGenericSerializer_v361;
import org.cloudburstmc.protocol.bedrock.codec.v766.Bedrock_v766;
import org.cloudburstmc.protocol.bedrock.codec.v786.serializer.LevelSoundEventSerializer_v786;
import org.cloudburstmc.protocol.bedrock.codec.v827.Bedrock_v827;
import org.cloudburstmc.protocol.bedrock.codec.v843.serializer.BiomeDefinitionListSerializer_v843;
import org.cloudburstmc.protocol.bedrock.codec.v843.serializer.PlayerArmorDamageSerializer_v843;
import org.cloudburstmc.protocol.bedrock.codec.v843.serializer.ServerboundPackSettingChangeSerializer_v843;
import org.cloudburstmc.protocol.bedrock.data.*;
import org.cloudburstmc.protocol.bedrock.packet.*;
import org.cloudburstmc.protocol.common.util.TypeMap;

public class Bedrock_v843 extends Bedrock_v827 {

    protected static final TypeMap<SoundEvent> SOUND_EVENTS = Bedrock_v827.SOUND_EVENTS
            .toBuilder()
            .replace(563, SoundEvent.PLACE_ITEM)
            .insert(564, SoundEvent.SINGLE_ITEM_SWAP)
            .insert(565, SoundEvent.MULTI_ITEM_SWAP)
            .insert(566, SoundEvent.UNDEFINED)
            .build();

    protected static final TypeMap<ParticleType> PARTICLE_TYPES = Bedrock_v766.PARTICLE_TYPES.toBuilder()
            .insert(98, ParticleType.GREEN_FLAME)
            .build();

    protected static final TypeMap<LevelEventType> LEVEL_EVENTS = Bedrock_v766.LEVEL_EVENTS.toBuilder()
            .insert(LEVEL_EVENT_PARTICLE_TYPE, PARTICLE_TYPES)
            .build();

    public static final BedrockCodec CODEC = Bedrock_v827.CODEC.toBuilder()
            .raknetProtocolVersion(11)
            .protocolVersion(843)
            .minecraftVersion("1.21.110")
            .helper(() -> new BedrockCodecHelper_v843(ENTITY_DATA, GAME_RULE_TYPES, ITEM_STACK_REQUEST_TYPES, CONTAINER_SLOT_TYPES, PLAYER_ABILITIES, TEXT_PROCESSING_ORIGINS))
            .updateSerializer(BiomeDefinitionListPacket.class, BiomeDefinitionListSerializer_v843.INSTANCE)
            .updateSerializer(LevelEventPacket.class, new LevelEventSerializer_v291(LEVEL_EVENTS))
            .updateSerializer(LevelEventGenericPacket.class, new LevelEventGenericSerializer_v361(LEVEL_EVENTS))
            .updateSerializer(LevelSoundEventPacket.class, new LevelSoundEventSerializer_v786(SOUND_EVENTS))
            .updateSerializer(PlayerArmorDamagePacket.class, PlayerArmorDamageSerializer_v843.INSTANCE)
            .registerPacket(ServerboundPackSettingChangePacket::new, ServerboundPackSettingChangeSerializer_v843.INSTANCE, 329, PacketRecipient.SERVER)
            .build();
}
