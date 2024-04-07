package org.cloudburstmc.protocol.bedrock.codec.v503;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodec;
import org.cloudburstmc.protocol.bedrock.codec.EntityDataTypeMap;
import org.cloudburstmc.protocol.bedrock.codec.v291.serializer.AvailableCommandsSerializer_v291;
import org.cloudburstmc.protocol.bedrock.codec.v291.serializer.EntityEventSerializer_v291;
import org.cloudburstmc.protocol.bedrock.codec.v291.serializer.LevelEventSerializer_v291;
import org.cloudburstmc.protocol.bedrock.codec.v291.serializer.LevelSoundEvent1Serializer_v291;
import org.cloudburstmc.protocol.bedrock.codec.v313.serializer.LevelSoundEvent2Serializer_v313;
import org.cloudburstmc.protocol.bedrock.codec.v332.serializer.LevelSoundEventSerializer_v332;
import org.cloudburstmc.protocol.bedrock.codec.v361.serializer.LevelEventGenericSerializer_v361;
import org.cloudburstmc.protocol.bedrock.codec.v448.serializer.AvailableCommandsSerializer_v448;
import org.cloudburstmc.protocol.bedrock.codec.v486.Bedrock_v486;
import org.cloudburstmc.protocol.bedrock.codec.v503.serializer.*;
import org.cloudburstmc.protocol.bedrock.data.LevelEvent;
import org.cloudburstmc.protocol.bedrock.data.LevelEventType;
import org.cloudburstmc.protocol.bedrock.data.PacketRecipient;
import org.cloudburstmc.protocol.bedrock.data.SoundEvent;
import org.cloudburstmc.protocol.bedrock.data.command.CommandParam;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityDataFormat;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityDataTypes;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityEventType;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityFlag;
import org.cloudburstmc.protocol.bedrock.packet.*;
import org.cloudburstmc.protocol.bedrock.transformer.FlagTransformer;
import org.cloudburstmc.protocol.common.util.TypeMap;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Bedrock_v503 extends Bedrock_v486 {

    protected static final TypeMap<EntityFlag> ENTITY_FLAGS = Bedrock_v486.ENTITY_FLAGS.toBuilder()
            .insert(102, EntityFlag.JUMP_GOAL_JUMP)
            .insert(103, EntityFlag.EMERGING)
            .insert(104, EntityFlag.SNIFFING)
            .insert(105, EntityFlag.DIGGING)
            .build();

    protected static final EntityDataTypeMap ENTITY_DATA = Bedrock_v486.ENTITY_DATA.toBuilder()
            .update(EntityDataTypes.FLAGS, new FlagTransformer(ENTITY_FLAGS, 0))
            .update(EntityDataTypes.FLAGS_2, new FlagTransformer(ENTITY_FLAGS, 1))
            .insert(EntityDataTypes.MOVEMENT_SOUND_DISTANCE_OFFSET, 125, EntityDataFormat.FLOAT)
            .insert(EntityDataTypes.HEARTBEAT_INTERVAL_TICKS, 126, EntityDataFormat.INT)
            .insert(EntityDataTypes.HEARTBEAT_SOUND_EVENT, 127, EntityDataFormat.INT)
            .build();

    protected static final TypeMap<EntityEventType> ENTITY_EVENTS = Bedrock_v486.ENTITY_EVENTS.toBuilder()
            .insert(77, EntityEventType.VIBRATION_DETECTED)
            .build();

    protected static final TypeMap<CommandParam> COMMAND_PARAMS = Bedrock_v486.COMMAND_PARAMS.toBuilder()
            .shift(32, 6)
            .insert(37, CommandParam.EQUIPMENT_SLOTS)
            .build();

    protected static final TypeMap<LevelEventType> LEVEL_EVENTS = Bedrock_v486.LEVEL_EVENTS.toBuilder()
            .insert(LEVEL_EVENT_PARTICLE + 37, LevelEvent.SCULK_CHARGE)
            .insert(LEVEL_EVENT_PARTICLE + 38, LevelEvent.SCULK_CHARGE_POP)
            .build();

    protected static final TypeMap<SoundEvent> SOUND_EVENTS = Bedrock_v486.SOUND_EVENTS.toBuilder()
            .replace(375, SoundEvent.LISTENING)
            .insert(376, SoundEvent.HEARTBEAT)
            .insert(377, SoundEvent.HORN_BREAK)
            .insert(378, SoundEvent.SCULK_PLACE)
            .insert(379, SoundEvent.SCULK_SPREAD)
            .insert(380, SoundEvent.SCULK_CHARGE)
            .insert(381, SoundEvent.SCULK_SENSOR_PLACE)
            .insert(382, SoundEvent.SCULK_SHRIEKER_PLACE)
            .insert(383, SoundEvent.GOAT_CALL_0)
            .insert(384, SoundEvent.GOAT_CALL_1)
            .insert(385, SoundEvent.GOAT_CALL_2)
            .insert(386, SoundEvent.GOAT_CALL_3)
            .insert(387, SoundEvent.GOAT_CALL_4)
            .insert(388, SoundEvent.GOAT_CALL_5)
            .insert(389, SoundEvent.GOAT_CALL_6)
            .insert(390, SoundEvent.GOAT_CALL_7)
            .insert(391, SoundEvent.GOAT_CALL_8)
            .insert(392, SoundEvent.GOAT_CALL_9)
            .insert(393, SoundEvent.GOAT_HARMONY_0)
            .insert(394, SoundEvent.GOAT_HARMONY_1)
            .insert(395, SoundEvent.GOAT_HARMONY_2)
            .insert(396, SoundEvent.GOAT_HARMONY_3)
            .insert(397, SoundEvent.GOAT_HARMONY_4)
            .insert(398, SoundEvent.GOAT_HARMONY_5)
            .insert(399, SoundEvent.GOAT_HARMONY_6)
            .insert(400, SoundEvent.GOAT_HARMONY_7)
            .insert(401, SoundEvent.GOAT_HARMONY_8)
            .insert(402, SoundEvent.GOAT_HARMONY_9)
            .insert(403, SoundEvent.GOAT_MELODY_0)
            .insert(404, SoundEvent.GOAT_MELODY_1)
            .insert(405, SoundEvent.GOAT_MELODY_2)
            .insert(406, SoundEvent.GOAT_MELODY_3)
            .insert(407, SoundEvent.GOAT_MELODY_4)
            .insert(408, SoundEvent.GOAT_MELODY_5)
            .insert(409, SoundEvent.GOAT_MELODY_6)
            .insert(410, SoundEvent.GOAT_MELODY_7)
            .insert(411, SoundEvent.GOAT_MELODY_8)
            .insert(412, SoundEvent.GOAT_MELODY_9)
            .insert(413, SoundEvent.GOAT_BASS_0)
            .insert(414, SoundEvent.GOAT_BASS_1)
            .insert(415, SoundEvent.GOAT_BASS_2)
            .insert(416, SoundEvent.GOAT_BASS_3)
            .insert(417, SoundEvent.GOAT_BASS_4)
            .insert(418, SoundEvent.GOAT_BASS_5)
            .insert(419, SoundEvent.GOAT_BASS_6)
            .insert(420, SoundEvent.GOAT_BASS_7)
            .insert(421, SoundEvent.GOAT_BASS_8)
            .insert(422, SoundEvent.GOAT_BASS_9)
            .insert(423, SoundEvent.UNDEFINED)
            .build();

    public static final BedrockCodec CODEC = Bedrock_v486.CODEC.toBuilder()
            .protocolVersion(503)
            .minecraftVersion("1.18.30")
            .helper(() -> new BedrockCodecHelper_v503(ENTITY_DATA, GAME_RULE_TYPES, ITEM_STACK_REQUEST_TYPES, CONTAINER_SLOT_TYPES))
            .updateSerializer(StartGamePacket.class, new StartGameSerializer_v503())
            .updateSerializer(AddPlayerPacket.class, new AddPlayerSerializer_v503())
            .updateSerializer(AvailableCommandsPacket.class, new AvailableCommandsSerializer_v448(COMMAND_PARAMS))
            .updateSerializer(EntityEventPacket.class, new EntityEventSerializer_v291(ENTITY_EVENTS))
            .updateSerializer(LevelEventPacket.class, new LevelEventSerializer_v291(LEVEL_EVENTS))
            .updateSerializer(LevelEventGenericPacket.class, new LevelEventGenericSerializer_v361(LEVEL_EVENTS))
            .updateSerializer(LevelSoundEvent1Packet.class, new LevelSoundEvent1Serializer_v291(SOUND_EVENTS))
            .updateSerializer(LevelSoundEvent2Packet.class, new LevelSoundEvent2Serializer_v313(SOUND_EVENTS))
            .updateSerializer(LevelSoundEventPacket.class, new LevelSoundEventSerializer_v332(SOUND_EVENTS))
            .updateSerializer(SpawnParticleEffectPacket.class, new SpawnParticleEffectSerializer_v503())
            .updateSerializer(AddVolumeEntityPacket.class, new AddVolumeEntitySerializer_v503())
            .updateSerializer(RemoveVolumeEntityPacket.class, new RemoveVolumeEntitySerializer_v503())
            .registerPacket(TickingAreasLoadStatusPacket::new, new TickingAreasLoadStatusSerializer_v503(), 179, PacketRecipient.CLIENT)
            .registerPacket(DimensionDataPacket::new, new DimensionDataSerializer_v503(), 180, PacketRecipient.CLIENT)
            .registerPacket(AgentActionEventPacket::new, new AgentActionEventSerializer_v503(), 181, PacketRecipient.CLIENT)
            .registerPacket(ChangeMobPropertyPacket::new, new ChangeMobPropertySerializer_v503(), 182, PacketRecipient.SERVER)
            .build();
}
