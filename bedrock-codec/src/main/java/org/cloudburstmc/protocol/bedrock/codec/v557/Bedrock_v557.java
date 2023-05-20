package org.cloudburstmc.protocol.bedrock.codec.v557;

import org.cloudburstmc.protocol.bedrock.codec.BedrockCodec;
import org.cloudburstmc.protocol.bedrock.codec.EntityDataTypeMap;
import org.cloudburstmc.protocol.bedrock.codec.v291.serializer.LevelSoundEvent1Serializer_v291;
import org.cloudburstmc.protocol.bedrock.codec.v313.serializer.LevelSoundEvent2Serializer_v313;
import org.cloudburstmc.protocol.bedrock.codec.v332.serializer.LevelSoundEventSerializer_v332;
import org.cloudburstmc.protocol.bedrock.codec.v554.Bedrock_v554;
import org.cloudburstmc.protocol.bedrock.codec.v557.serializer.AddEntitySerializer_v557;
import org.cloudburstmc.protocol.bedrock.codec.v557.serializer.AddPlayerSerializer_v557;
import org.cloudburstmc.protocol.bedrock.codec.v557.serializer.SetEntityDataSerializer_v557;
import org.cloudburstmc.protocol.bedrock.data.SoundEvent;
import org.cloudburstmc.protocol.bedrock.packet.*;
import org.cloudburstmc.protocol.common.util.TypeMap;

public class Bedrock_v557 extends Bedrock_v554 {

    public static final EntityDataTypeMap ENTITY_DATA = Bedrock_v554.ENTITY_DATA.toBuilder()
            .remove(120) // UPDATE_PROPERTIES
            .shift(121, -1)
            .build();

    public static final TypeMap<SoundEvent> SOUND_EVENTS = Bedrock_v554.SOUND_EVENTS.toBuilder()
            .remove(443) // UNDEFINED
            .insert(445, SoundEvent.BUNDLE_DROP_CONTENTS)
            .insert(446, SoundEvent.BUNDLE_INSERT)
            .insert(447, SoundEvent.BUNDLE_REMOVE_ONE)
            .insert(448, SoundEvent.UNDEFINED)
            .build();

    public static final BedrockCodec CODEC = Bedrock_v554.CODEC.toBuilder()
            .raknetProtocolVersion(11)
            .protocolVersion(557)
            .minecraftVersion("1.19.40")
            .helper(() -> new BedrockCodecHelper_v557(ENTITY_DATA, GAME_RULE_TYPES, ITEM_STACK_REQUEST_TYPES, CONTAINER_SLOT_TYPES, PLAYER_ABILITIES, TEXT_PROCESSING_ORIGINS))
            .updateSerializer(AddPlayerPacket.class, new AddPlayerSerializer_v557())
            .updateSerializer(AddEntityPacket.class, new AddEntitySerializer_v557())
            .updateSerializer(SetEntityDataPacket.class, new SetEntityDataSerializer_v557())
            .updateSerializer(LevelSoundEvent1Packet.class, new LevelSoundEvent1Serializer_v291(SOUND_EVENTS))
            .updateSerializer(LevelSoundEvent2Packet.class, new LevelSoundEvent2Serializer_v313(SOUND_EVENTS))
            .updateSerializer(LevelSoundEventPacket.class, new LevelSoundEventSerializer_v332(SOUND_EVENTS))
            .build();
}
