package org.cloudburstmc.protocol.bedrock.codec.v534;

import org.cloudburstmc.protocol.bedrock.codec.BedrockCodec;
import org.cloudburstmc.protocol.bedrock.codec.v291.serializer.EntityEventSerializer_v291;
import org.cloudburstmc.protocol.bedrock.codec.v291.serializer.LevelEventSerializer_v291;
import org.cloudburstmc.protocol.bedrock.codec.v291.serializer.LevelSoundEvent1Serializer_v291;
import org.cloudburstmc.protocol.bedrock.codec.v313.serializer.LevelSoundEvent2Serializer_v313;
import org.cloudburstmc.protocol.bedrock.codec.v332.serializer.LevelSoundEventSerializer_v332;
import org.cloudburstmc.protocol.bedrock.codec.v527.Bedrock_v527;
import org.cloudburstmc.protocol.bedrock.codec.v534.serializer.*;
import org.cloudburstmc.protocol.bedrock.data.SoundEvent;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityEventType;
import org.cloudburstmc.protocol.bedrock.packet.*;
import org.cloudburstmc.protocol.common.util.TypeMap;

public class Bedrock_v534 extends Bedrock_v527 {

    protected static final TypeMap<EntityEventType> ENTITY_EVENTS = Bedrock_v527.ENTITY_EVENTS.toBuilder()
            .insert(78, EntityEventType.DRINK_MILK)
            .build();

    protected static final TypeMap<SoundEvent> SOUND_EVENTS = Bedrock_v527.SOUND_EVENTS.toBuilder()
            .insert(432, SoundEvent.MILK_DRINK)
            .replace(441, SoundEvent.RECORD_PLAYING)
            .insert(442, SoundEvent.UNDEFINED)
            .build();

    public static final BedrockCodec CODEC = Bedrock_v527.CODEC.toBuilder()
            .protocolVersion(534)
            .minecraftVersion("1.19.10")
            .helper(() -> new BedrockCodecHelper_v534(ENTITY_DATA, GAME_RULE_TYPES, ITEM_STACK_REQUEST_TYPES))
            .updateSerializer(StartGamePacket.class, StartGameSerializer_v534.INSTANCE)
            .updateSerializer(AddEntityPacket.class, AddEntitySerializer_v534.INSTANCE)
            .updateSerializer(AddPlayerPacket.class, AddPlayerSerializer_v534.INSTANCE)
            .updateSerializer(EntityEventPacket.class, new EntityEventSerializer_v291(ENTITY_EVENTS))
            .updateSerializer(LevelEventPacket.class, new LevelEventSerializer_v291(LEVEL_EVENTS))
            .updateSerializer(LevelSoundEvent1Packet.class, new LevelSoundEvent1Serializer_v291(SOUND_EVENTS))
            .updateSerializer(LevelSoundEvent2Packet.class, new LevelSoundEvent2Serializer_v313(SOUND_EVENTS))
            .updateSerializer(LevelSoundEventPacket.class, new LevelSoundEventSerializer_v332(SOUND_EVENTS))
            .registerPacket(UpdateAbilitiesPacket.class, UpdateAbilitiesSerializer_v534.INSTANCE, 187)
            .registerPacket(UpdateAdventureSettingsPacket.class, UpdateAdventureSettingsSerializer_v534.INSTANCE, 188)
            .registerPacket(DeathInfoPacket.class, DeathInfoSerializer_v534.INSTANCE, 189)
            .registerPacket(EditorNetworkPacket.class, EditorNetworkSerializer_v534.INSTANCE, 190)
            .build();
}
