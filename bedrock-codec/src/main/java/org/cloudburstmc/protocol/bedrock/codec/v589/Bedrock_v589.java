package org.cloudburstmc.protocol.bedrock.codec.v589;

import org.cloudburstmc.protocol.bedrock.codec.BedrockCodec;
import org.cloudburstmc.protocol.bedrock.codec.v291.serializer.LevelSoundEvent1Serializer_v291;
import org.cloudburstmc.protocol.bedrock.codec.v313.serializer.LevelSoundEvent2Serializer_v313;
import org.cloudburstmc.protocol.bedrock.codec.v332.serializer.LevelSoundEventSerializer_v332;
import org.cloudburstmc.protocol.bedrock.codec.v575.BedrockCodecHelper_v575;
import org.cloudburstmc.protocol.bedrock.codec.v582.Bedrock_v582;
import org.cloudburstmc.protocol.bedrock.codec.v589.serializer.EmoteSerializer_v589;
import org.cloudburstmc.protocol.bedrock.codec.v589.serializer.EventSerializer_v589;
import org.cloudburstmc.protocol.bedrock.codec.v589.serializer.StartGameSerializer_v589;
import org.cloudburstmc.protocol.bedrock.codec.v589.serializer.UnlockedRecipesSerializer_v589;
import org.cloudburstmc.protocol.bedrock.data.SoundEvent;
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.TextProcessingEventOrigin;
import org.cloudburstmc.protocol.bedrock.packet.*;
import org.cloudburstmc.protocol.common.util.TypeMap;

public class Bedrock_v589 extends Bedrock_v582 {

    protected static final TypeMap<SoundEvent> SOUND_EVENTS = Bedrock_v582.SOUND_EVENTS
            .toBuilder()
            .replace(466, SoundEvent.SNIFFER_EGG_CRACK)
            .insert(467, SoundEvent.SNIFFER_EGG_HATCHED)
            .insert(468, SoundEvent.WAXED_SIGN_INTERACT_FAIL)
            .insert(469, SoundEvent.RECORD_RELIC)
            .insert(470, SoundEvent.UNDEFINED)
            .build();

    protected static final TypeMap<TextProcessingEventOrigin> TEXT_PROCESSING_ORIGINS = TypeMap.builder(TextProcessingEventOrigin.class)
            .insert(0, TextProcessingEventOrigin.SERVER_CHAT_PUBLIC)
            .insert(1, TextProcessingEventOrigin.SERVER_CHAT_WHISPER)
            .insert(2, TextProcessingEventOrigin.SIGN_TEXT)
            .insert(3, TextProcessingEventOrigin.ANVIL_TEXT)
            .insert(4, TextProcessingEventOrigin.BOOK_AND_QUILL_TEXT)
            .insert(5, TextProcessingEventOrigin.COMMAND_BLOCK_TEXT)
            .insert(6, TextProcessingEventOrigin.BLOCK_ENTITY_DATA_TEXT)
            .insert(7, TextProcessingEventOrigin.JOIN_EVENT_TEXT)
            .insert(8, TextProcessingEventOrigin.LEAVE_EVENT_TEXT)
            .insert(9, TextProcessingEventOrigin.SLASH_COMMAND_TEXT)
            .insert(10, TextProcessingEventOrigin.CARTOGRAPHY_TEXT)
            .insert(11, TextProcessingEventOrigin.KICK_COMMAND)
            .insert(12, TextProcessingEventOrigin.TITLE_COMMAND)
            .insert(13, TextProcessingEventOrigin.SUMMON_COMMAND)
            .build();

    public static final BedrockCodec CODEC = Bedrock_v582.CODEC.toBuilder()
            .raknetProtocolVersion(11)
            .protocolVersion(589)
            .minecraftVersion("1.20.0")
            .helper(() -> new BedrockCodecHelper_v575(ENTITY_DATA, GAME_RULE_TYPES, ITEM_STACK_REQUEST_TYPES, CONTAINER_SLOT_TYPES, PLAYER_ABILITIES, TEXT_PROCESSING_ORIGINS))
            .updateSerializer(StartGamePacket.class, new StartGameSerializer_v589())
            .updateSerializer(EventPacket.class, EventSerializer_v589.INSTANCE)
            .updateSerializer(EmotePacket.class, EmoteSerializer_v589.INSTANCE)
            .updateSerializer(UnlockedRecipesPacket.class, new UnlockedRecipesSerializer_v589())
            .updateSerializer(LevelSoundEvent1Packet.class, new LevelSoundEvent1Serializer_v291(SOUND_EVENTS))
            .updateSerializer(LevelSoundEvent2Packet.class, new LevelSoundEvent2Serializer_v313(SOUND_EVENTS))
            .updateSerializer(LevelSoundEventPacket.class, new LevelSoundEventSerializer_v332(SOUND_EVENTS))
            .build();
}
