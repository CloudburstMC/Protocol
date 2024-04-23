package org.cloudburstmc.protocol.bedrock.codec.v554;

import org.cloudburstmc.protocol.bedrock.codec.BedrockCodec;
import org.cloudburstmc.protocol.bedrock.codec.v291.serializer.LevelSoundEvent1Serializer_v291;
import org.cloudburstmc.protocol.bedrock.codec.v313.serializer.LevelSoundEvent2Serializer_v313;
import org.cloudburstmc.protocol.bedrock.codec.v332.serializer.LevelSoundEventSerializer_v332;
import org.cloudburstmc.protocol.bedrock.codec.v545.Bedrock_v545;
import org.cloudburstmc.protocol.bedrock.codec.v554.serializer.*;
import org.cloudburstmc.protocol.bedrock.data.PacketRecipient;
import org.cloudburstmc.protocol.bedrock.data.SoundEvent;
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.TextProcessingEventOrigin;
import org.cloudburstmc.protocol.bedrock.packet.*;
import org.cloudburstmc.protocol.common.util.TypeMap;

public class Bedrock_v554 extends Bedrock_v545 {

    protected static final TypeMap<SoundEvent> SOUND_EVENTS = Bedrock_v545.SOUND_EVENTS.toBuilder()
            .replace(442, SoundEvent.ENCHANTING_TABLE_USE)
            .insert(443, SoundEvent.UNDEFINED)
            .build();

    protected static final TypeMap<TextProcessingEventOrigin> TEXT_PROCESSING_ORIGINS = TypeMap.fromEnum(TextProcessingEventOrigin.class, 13);

    public static final BedrockCodec CODEC = Bedrock_v545.CODEC.toBuilder()
            .raknetProtocolVersion(11)
            .protocolVersion(554)
            .minecraftVersion("1.19.30")
            .helper(() -> new BedrockCodecHelper_v554(ENTITY_DATA, GAME_RULE_TYPES, ITEM_STACK_REQUEST_TYPES, CONTAINER_SLOT_TYPES, PLAYER_ABILITIES, TEXT_PROCESSING_ORIGINS))
            .updateSerializer(TextPacket.class, new TextSerializer_v554())
            .updateSerializer(NetworkSettingsPacket.class, new NetworkSettingsSerializer_v554())
            .updateSerializer(StructureBlockUpdatePacket.class, new StructureBlockUpdateSerializer_v554())
            .updateSerializer(LevelSoundEvent1Packet.class, new LevelSoundEvent1Serializer_v291(SOUND_EVENTS))
            .updateSerializer(LevelSoundEvent2Packet.class, new LevelSoundEvent2Serializer_v313(SOUND_EVENTS))
            .updateSerializer(LevelSoundEventPacket.class, new LevelSoundEventSerializer_v332(SOUND_EVENTS))
            .registerPacket(ServerStatsPacket::new, new ServerStatsSerializer_v554(), 192, PacketRecipient.CLIENT)
            .registerPacket(RequestNetworkSettingsPacket::new, new RequestNetworkSettingsSerializer_v554(), 193, PacketRecipient.SERVER)
            .registerPacket(GameTestRequestPacket::new, new GameTestRequestSerializer_v554(), 194, PacketRecipient.SERVER)
            .registerPacket(GameTestResultsPacket::new, new GameTestResultsSerializer_v554(), 195, PacketRecipient.CLIENT)
            .build();

}
