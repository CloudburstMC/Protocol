package org.cloudburstmc.protocol.bedrock.codec.v553;

import org.cloudburstmc.protocol.bedrock.codec.BedrockCodec;
import org.cloudburstmc.protocol.bedrock.codec.v291.serializer.LevelSoundEvent1Serializer_v291;
import org.cloudburstmc.protocol.bedrock.codec.v313.serializer.LevelSoundEvent2Serializer_v313;
import org.cloudburstmc.protocol.bedrock.codec.v332.serializer.LevelSoundEventSerializer_v332;
import org.cloudburstmc.protocol.bedrock.codec.v544.Bedrock_v544;
import org.cloudburstmc.protocol.bedrock.codec.v545.Bedrock_v545;
import org.cloudburstmc.protocol.bedrock.codec.v553.serializer.*;
import org.cloudburstmc.protocol.bedrock.data.SoundEvent;
import org.cloudburstmc.protocol.bedrock.packet.*;
import org.cloudburstmc.protocol.common.util.TypeMap;

public class Bedrock_v553 extends Bedrock_v545 {

    protected static final TypeMap<SoundEvent> SOUND_EVENTS = Bedrock_v545.SOUND_EVENTS.toBuilder()
            .replace(442, SoundEvent.ENCHANTING_TABLE_USE)
            .insert(443, SoundEvent.UNDEFINED)
            .build();

    public static final BedrockCodec CODEC = Bedrock_v544.CODEC.toBuilder()
            .raknetProtocolVersion(11)
            .protocolVersion(553)
            .minecraftVersion("1.19.30")
            .helper(() -> new BedrockCodecHelper_v553(ENTITY_DATA, GAME_RULE_TYPES, ITEM_STACK_REQUEST_TYPES))
            .updateSerializer(TextPacket.class, new TextSerializer_v553())
            .updateSerializer(CraftingDataPacket.class, new CraftingDataSerializer_v553())
            .updateSerializer(NetworkSettingsPacket.class, new NetworkSettingsSerializer_v553())
            .updateSerializer(StructureBlockUpdatePacket.class, new StructureBlockUpdateSerializer_v553())
            .updateSerializer(LevelSoundEvent1Packet.class, new LevelSoundEvent1Serializer_v291(SOUND_EVENTS))
            .updateSerializer(LevelSoundEvent2Packet.class, new LevelSoundEvent2Serializer_v313(SOUND_EVENTS))
            .updateSerializer(LevelSoundEventPacket.class, new LevelSoundEventSerializer_v332(SOUND_EVENTS))
            .registerPacket(ServerStatsPacket.class, new ServerStatsSerializer_v553(), 192)
            .registerPacket(RequestNetworkSettingsPacket.class, new RequestNetworkSettingsSerializer_v553(), 193)
            .registerPacket(GameTestRequestPacket.class, new GameTestRequestSerializer_v553(), 194)
            .registerPacket(GameTestResultsPacket.class, new GameTestResultsSerializer_v553(), 195)
            .build();

}