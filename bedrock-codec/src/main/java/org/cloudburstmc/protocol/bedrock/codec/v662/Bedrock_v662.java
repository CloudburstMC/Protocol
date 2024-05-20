package org.cloudburstmc.protocol.bedrock.codec.v662;

import org.cloudburstmc.protocol.bedrock.codec.BedrockCodec;
import org.cloudburstmc.protocol.bedrock.codec.v291.serializer.LevelEventSerializer_v291;
import org.cloudburstmc.protocol.bedrock.codec.v291.serializer.LevelSoundEvent1Serializer_v291;
import org.cloudburstmc.protocol.bedrock.codec.v313.serializer.LevelSoundEvent2Serializer_v313;
import org.cloudburstmc.protocol.bedrock.codec.v332.serializer.LevelSoundEventSerializer_v332;
import org.cloudburstmc.protocol.bedrock.codec.v361.serializer.LevelEventGenericSerializer_v361;
import org.cloudburstmc.protocol.bedrock.codec.v575.BedrockCodecHelper_v575;
import org.cloudburstmc.protocol.bedrock.codec.v594.serializer.AvailableCommandsSerializer_v594;
import org.cloudburstmc.protocol.bedrock.codec.v649.Bedrock_v649;
import org.cloudburstmc.protocol.bedrock.codec.v662.serializer.*;
import org.cloudburstmc.protocol.bedrock.data.LevelEvent;
import org.cloudburstmc.protocol.bedrock.data.LevelEventType;
import org.cloudburstmc.protocol.bedrock.data.ParticleType;
import org.cloudburstmc.protocol.bedrock.data.SoundEvent;
import org.cloudburstmc.protocol.bedrock.data.command.CommandParam;
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.TextProcessingEventOrigin;
import org.cloudburstmc.protocol.bedrock.packet.*;
import org.cloudburstmc.protocol.common.util.TypeMap;

public class Bedrock_v662 extends Bedrock_v649 {

    protected static final TypeMap<CommandParam> COMMAND_PARAMS = Bedrock_v649.COMMAND_PARAMS.toBuilder()
            .shift(24, 4)
            .insert(24, CommandParam.RATIONAL_RANGE_VAL)
            .insert(25, CommandParam.RATIONAL_RANGE_POST_VAL)
            .insert(26, CommandParam.RATIONAL_RANGE)
            .insert(27, CommandParam.RATIONAL_RANGE_FULL)
            .shift(48, 8)
            .insert(48, CommandParam.PROPERTY_VALUE)
            .insert(49, CommandParam.HAS_PROPERTY_PARAM_VALUE)
            .insert(50, CommandParam.HAS_PROPERTY_PARAM_ENUM_VALUE)
            .insert(51, CommandParam.HAS_PROPERTY_ARG)
            .insert(52, CommandParam.HAS_PROPERTY_ARGS)
            .insert(53, CommandParam.HAS_PROPERTY_ELEMENT)
            .insert(54, CommandParam.HAS_PROPERTY_ELEMENTS)
            .insert(55, CommandParam.HAS_PROPERTY_SELECTOR)
            .build();

    protected static final TypeMap<ParticleType> PARTICLE_TYPES = Bedrock_v649.PARTICLE_TYPES
            .toBuilder()
            .replace(18, ParticleType.BREEZE_WIND_EXPLOSION)
            .insert(90, ParticleType.VAULT_CONNECTION)
            .insert(91, ParticleType.WIND_EXPLOSION)
            .build();

    protected static final TypeMap<LevelEventType> LEVEL_EVENTS = Bedrock_v649.LEVEL_EVENTS.toBuilder()
            .replace(LEVEL_EVENT_BLOCK + 110, LevelEvent.PARTICLE_BREEZE_WIND_EXPLOSION)
            .replace(LEVEL_EVENT_BLOCK + 114, LevelEvent.PARTICLE_WIND_EXPLOSION)
            .insert(LEVEL_EVENT_BLOCK + 115, LevelEvent.ALL_PLAYERS_SLEEPING)
            .insert(9811, LevelEvent.ANIMATION_VAULT_ACTIVATE)
            .insert(9812, LevelEvent.ANIMATION_VAULT_DEACTIVATE)
            .insert(9813, LevelEvent.ANIMATION_VAULT_EJECT_ITEM)
            .insert(LEVEL_EVENT_PARTICLE_TYPE, PARTICLE_TYPES)
            .build();

    protected static final TypeMap<TextProcessingEventOrigin> TEXT_PROCESSING_ORIGINS = Bedrock_v649.TEXT_PROCESSING_ORIGINS
            .toBuilder()
            .replace(14, TextProcessingEventOrigin.SERVER_FORM) // replaces PASS_THROUGH_WITHOUT_SIFT
            .build();

    protected static final TypeMap<SoundEvent> SOUND_EVENTS = Bedrock_v649.SOUND_EVENTS
            .toBuilder()
            .replace(500, SoundEvent.VAULT_OPEN_SHUTTER)
            .insert(501, SoundEvent.VAULT_CLOSE_SHUTTER)
            .insert(502, SoundEvent.VAULT_EJECT_ITEM)
            .insert(503, SoundEvent.VAULT_INSERT_ITEM)
            .insert(504, SoundEvent.VAULT_INSERT_ITEM_FAIL)
            .insert(505, SoundEvent.VAULT_AMBIENT)
            .insert(506, SoundEvent.VAULT_ACTIVATE)
            .insert(507, SoundEvent.VAULT_DEACTIVATE)
            .insert(508, SoundEvent.HURT_REDUCED)
            .insert(509, SoundEvent.WIND_CHARGE_BURST)
            .insert(511, SoundEvent.UNDEFINED)
            .build();

    public static final BedrockCodec CODEC = Bedrock_v649.CODEC.toBuilder()
            .raknetProtocolVersion(11)
            .protocolVersion(662)
            .minecraftVersion("1.20.70")
            .helper(() -> new BedrockCodecHelper_v575(ENTITY_DATA, GAME_RULE_TYPES, ITEM_STACK_REQUEST_TYPES, CONTAINER_SLOT_TYPES, PLAYER_ABILITIES, TEXT_PROCESSING_ORIGINS))
            .updateSerializer(LevelEventPacket.class, new LevelEventSerializer_v291(LEVEL_EVENTS))
            .updateSerializer(LevelEventGenericPacket.class, new LevelEventGenericSerializer_v361(LEVEL_EVENTS))
            .updateSerializer(LevelSoundEvent1Packet.class, new LevelSoundEvent1Serializer_v291(SOUND_EVENTS))
            .updateSerializer(LevelSoundEvent2Packet.class, new LevelSoundEvent2Serializer_v313(SOUND_EVENTS))
            .updateSerializer(LevelSoundEventPacket.class, new LevelSoundEventSerializer_v332(SOUND_EVENTS))
            .updateSerializer(AvailableCommandsPacket.class, new AvailableCommandsSerializer_v594(COMMAND_PARAMS))
            .updateSerializer(LecternUpdatePacket.class, LecternUpdateSerializer_v662.INSTANCE)
            .updateSerializer(MobEffectPacket.class, MobEffectSerializer_v662.INSTANCE)
            .updateSerializer(PlayerAuthInputPacket.class, new PlayerAuthInputSerializer_v662())
            .updateSerializer(ResourcePacksInfoPacket.class, ResourcePacksInfoSerializer_v622.INSTANCE)
            .updateSerializer(SetEntityMotionPacket.class, SetEntityMotionSerializer_v662.INSTANCE)
            .deregisterPacket(ItemFrameDropItemPacket.class) // this packet is now deprecated
            .build();
}