package org.cloudburstmc.protocol.bedrock.codec.v448;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodec;
import org.cloudburstmc.protocol.bedrock.codec.v291.serializer.LevelEventSerializer_v291;
import org.cloudburstmc.protocol.bedrock.codec.v291.serializer.LevelSoundEvent1Serializer_v291;
import org.cloudburstmc.protocol.bedrock.codec.v313.serializer.LevelSoundEvent2Serializer_v313;
import org.cloudburstmc.protocol.bedrock.codec.v332.serializer.LevelSoundEventSerializer_v332;
import org.cloudburstmc.protocol.bedrock.codec.v440.Bedrock_v440;
import org.cloudburstmc.protocol.bedrock.codec.v448.serializer.AvailableCommandsSerializer_v448;
import org.cloudburstmc.protocol.bedrock.codec.v448.serializer.NpcDialogueSerializer_v448;
import org.cloudburstmc.protocol.bedrock.codec.v448.serializer.NpcRequestSerializer_v448;
import org.cloudburstmc.protocol.bedrock.codec.v448.serializer.ResourcePacksInfoSerializer_v448;
import org.cloudburstmc.protocol.bedrock.codec.v448.serializer.SetTitleSerializer_v448;
import org.cloudburstmc.protocol.bedrock.codec.v448.serializer.SimulationTypeSerializer_v448;
import org.cloudburstmc.protocol.bedrock.data.LevelEventType;
import org.cloudburstmc.protocol.bedrock.data.ParticleType;
import org.cloudburstmc.protocol.bedrock.data.SoundEvent;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityFlag;
import org.cloudburstmc.protocol.bedrock.packet.AvailableCommandsPacket;
import org.cloudburstmc.protocol.bedrock.packet.LevelEventPacket;
import org.cloudburstmc.protocol.bedrock.packet.LevelSoundEvent1Packet;
import org.cloudburstmc.protocol.bedrock.packet.LevelSoundEvent2Packet;
import org.cloudburstmc.protocol.bedrock.packet.LevelSoundEventPacket;
import org.cloudburstmc.protocol.bedrock.packet.NpcDialoguePacket;
import org.cloudburstmc.protocol.bedrock.packet.NpcRequestPacket;
import org.cloudburstmc.protocol.bedrock.packet.ResourcePacksInfoPacket;
import org.cloudburstmc.protocol.bedrock.packet.SetTitlePacket;
import org.cloudburstmc.protocol.bedrock.packet.SimulationTypePacket;
import org.cloudburstmc.protocol.common.util.TypeMap;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Bedrock_v448 extends Bedrock_v440 {

    protected static final TypeMap<EntityFlag> ENTITY_FLAGS = Bedrock_v440.ENTITY_FLAGS.toBuilder()
            .insert(98, EntityFlag.IN_ASCENDABLE_BLOCK)
            .insert(99, EntityFlag.OVER_DESCENDABLE_BLOCK)
            .build();

    protected static final TypeMap<LevelEventType> LEVEL_EVENTS = Bedrock_v440.LEVEL_EVENTS.toBuilder()
            .shift(LEVEL_EVENT_PARTICLE_TYPE + 9, 1)
            .insert(LEVEL_EVENT_PARTICLE_TYPE + 9, ParticleType.CANDLE_FLAME)
            .build();

    protected static final TypeMap<SoundEvent> SOUND_EVENTS = Bedrock_v440.SOUND_EVENTS.toBuilder()
            .replace(360, SoundEvent.CAKE_ADD_CANDLE)
            .insert(361, SoundEvent.EXTINGUISH_CANDLE)
            .insert(362, SoundEvent.AMBIENT_CANDLE)
            .insert(363, SoundEvent.UNDEFINED)
            .build();

    public static final BedrockCodec CODEC = Bedrock_v440.CODEC.toBuilder()
            .protocolVersion(448)
            .minecraftVersion("1.17.10")
            .helper(() -> new BedrockCodecHelper_v448(ENTITY_DATA, ENTITY_DATA_TYPES, ENTITY_FLAGS, GAME_RULE_TYPES, ITEM_STACK_REQUEST_TYPES))
            .updateSerializer(AvailableCommandsPacket.class, new AvailableCommandsSerializer_v448(COMMAND_PARAMS))
            .updateSerializer(LevelSoundEvent1Packet.class, new LevelSoundEvent1Serializer_v291(SOUND_EVENTS))
            .updateSerializer(LevelSoundEvent2Packet.class, new LevelSoundEvent2Serializer_v313(SOUND_EVENTS))
            .updateSerializer(LevelSoundEventPacket.class, new LevelSoundEventSerializer_v332(SOUND_EVENTS))
            .updateSerializer(LevelEventPacket.class, new LevelEventSerializer_v291(LEVEL_EVENTS))
            .updateSerializer(NpcRequestPacket.class, NpcRequestSerializer_v448.INSTANCE)
            .updateSerializer(SetTitlePacket.class, SetTitleSerializer_v448.INSTANCE)
            .updateSerializer(ResourcePacksInfoPacket.class, ResourcePacksInfoSerializer_v448.INSTANCE)
            .registerPacket(SimulationTypePacket.class, SimulationTypeSerializer_v448.INSTANCE, 168)
            .registerPacket(NpcDialoguePacket.class, NpcDialogueSerializer_v448.INSTANCE, 169)
            .build();
}
