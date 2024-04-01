package org.cloudburstmc.protocol.bedrock.codec.v630;

import org.cloudburstmc.protocol.bedrock.codec.BedrockCodec;
import org.cloudburstmc.protocol.bedrock.codec.EntityDataTypeMap;
import org.cloudburstmc.protocol.bedrock.codec.v291.serializer.LevelEventSerializer_v291;
import org.cloudburstmc.protocol.bedrock.codec.v291.serializer.LevelSoundEvent1Serializer_v291;
import org.cloudburstmc.protocol.bedrock.codec.v313.serializer.LevelSoundEvent2Serializer_v313;
import org.cloudburstmc.protocol.bedrock.codec.v332.serializer.LevelSoundEventSerializer_v332;
import org.cloudburstmc.protocol.bedrock.codec.v361.serializer.LevelEventGenericSerializer_v361;
import org.cloudburstmc.protocol.bedrock.codec.v575.BedrockCodecHelper_v575;
import org.cloudburstmc.protocol.bedrock.codec.v622.Bedrock_v622;
import org.cloudburstmc.protocol.bedrock.codec.v630.serializer.SetPlayerInventoryOptionsSerializer_v360;
import org.cloudburstmc.protocol.bedrock.codec.v630.serializer.ShowStoreOfferSerializer_v630;
import org.cloudburstmc.protocol.bedrock.codec.v630.serializer.ToggleCrafterSlotRequestSerializer_v630;
import org.cloudburstmc.protocol.bedrock.data.LevelEvent;
import org.cloudburstmc.protocol.bedrock.data.LevelEventType;
import org.cloudburstmc.protocol.bedrock.data.ParticleType;
import org.cloudburstmc.protocol.bedrock.data.SoundEvent;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityDataTypes;
import org.cloudburstmc.protocol.bedrock.data.inventory.ContainerSlotType;
import org.cloudburstmc.protocol.bedrock.packet.*;
import org.cloudburstmc.protocol.bedrock.transformer.TypeMapTransformer;
import org.cloudburstmc.protocol.common.util.TypeMap;

public class Bedrock_v630 extends Bedrock_v622 {

    protected static final TypeMap<ParticleType> PARTICLE_TYPES = Bedrock_v622.PARTICLE_TYPES
            .toBuilder()
            .insert(87, ParticleType.DUST_PLUME)
            .insert(88, ParticleType.WHITE_SMOKE)
            .build();

    protected static final TypeMap<LevelEventType> LEVEL_EVENTS = Bedrock_v622.LEVEL_EVENTS.toBuilder()
            .insert(LEVEL_EVENT_PARTICLE + 40, LevelEvent.DUST_PLUME)
            .replace(LEVEL_EVENT_BLOCK + 109, LevelEvent.PARTICLE_SHOOT_WHITE_SMOKE)
            .insert(LEVEL_EVENT_BLOCK + 110, LevelEvent.ALL_PLAYERS_SLEEPING)
            .insert(LEVEL_EVENT_PARTICLE_TYPE, PARTICLE_TYPES)
            .build();

    protected static final EntityDataTypeMap ENTITY_DATA = Bedrock_v622.ENTITY_DATA
            .toBuilder()
            .update(EntityDataTypes.AREA_EFFECT_CLOUD_PARTICLE, new TypeMapTransformer<>(PARTICLE_TYPES))
            .build();

    protected static final TypeMap<ContainerSlotType> CONTAINER_SLOT_TYPES = Bedrock_v622.CONTAINER_SLOT_TYPES.toBuilder()
            .insert(62, ContainerSlotType.CRAFTER_BLOCK_CONTAINER)
            .build();

    protected static final TypeMap<SoundEvent> SOUND_EVENTS = Bedrock_v622.SOUND_EVENTS
            .toBuilder()
            .replace(479, SoundEvent.CRAFTER_CRAFT)
            .insert(480, SoundEvent.CRAFTER_FAILED)
            .insert(481, SoundEvent.DECORATED_POT_INSERT)
            .insert(482, SoundEvent.DECORATED_POT_INSERT_FAILED)
            .insert(483, SoundEvent.CRAFTER_DISABLE_SLOT)
            .insert(490, SoundEvent.COPPER_BULB_ON)
            .insert(491, SoundEvent.COPPER_BULB_OFF)
            .insert(492, SoundEvent.UNDEFINED)
            .build();


    public static final BedrockCodec CODEC = Bedrock_v622.CODEC.toBuilder()
            .raknetProtocolVersion(11)
            .protocolVersion(630)
            .minecraftVersion("1.20.50")
            .helper(() -> new BedrockCodecHelper_v575(ENTITY_DATA, GAME_RULE_TYPES, ITEM_STACK_REQUEST_TYPES, CONTAINER_SLOT_TYPES, PLAYER_ABILITIES, TEXT_PROCESSING_ORIGINS))
            .updateSerializer(LevelEventPacket.class, new LevelEventSerializer_v291(LEVEL_EVENTS))
            .updateSerializer(LevelEventGenericPacket.class, new LevelEventGenericSerializer_v361(LEVEL_EVENTS))
            .updateSerializer(LevelSoundEvent1Packet.class, new LevelSoundEvent1Serializer_v291(SOUND_EVENTS))
            .updateSerializer(LevelSoundEvent2Packet.class, new LevelSoundEvent2Serializer_v313(SOUND_EVENTS))
            .updateSerializer(LevelSoundEventPacket.class, new LevelSoundEventSerializer_v332(SOUND_EVENTS))
            .updateSerializer(ShowStoreOfferPacket.class, ShowStoreOfferSerializer_v630.INSTANCE)
            .registerPacket(ToggleCrafterSlotRequestPacket::new, new ToggleCrafterSlotRequestSerializer_v630(), 306)
            .registerPacket(SetPlayerInventoryOptionsPacket::new, new SetPlayerInventoryOptionsSerializer_v360(), 307)
            .build();
}