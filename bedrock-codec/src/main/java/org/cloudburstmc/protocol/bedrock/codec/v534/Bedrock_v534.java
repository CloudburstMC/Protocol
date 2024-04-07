package org.cloudburstmc.protocol.bedrock.codec.v534;

import org.cloudburstmc.protocol.bedrock.codec.BedrockCodec;
import org.cloudburstmc.protocol.bedrock.codec.v291.serializer.EntityEventSerializer_v291;
import org.cloudburstmc.protocol.bedrock.codec.v291.serializer.LevelSoundEvent1Serializer_v291;
import org.cloudburstmc.protocol.bedrock.codec.v313.serializer.LevelSoundEvent2Serializer_v313;
import org.cloudburstmc.protocol.bedrock.codec.v332.serializer.LevelSoundEventSerializer_v332;
import org.cloudburstmc.protocol.bedrock.codec.v503.BedrockCodecHelper_v503;
import org.cloudburstmc.protocol.bedrock.codec.v527.Bedrock_v527;
import org.cloudburstmc.protocol.bedrock.codec.v534.serializer.*;
import org.cloudburstmc.protocol.bedrock.data.Ability;
import org.cloudburstmc.protocol.bedrock.data.PacketRecipient;
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

    protected static final TypeMap<Ability> PLAYER_ABILITIES = TypeMap.builder(Ability.class)
            .insert(0, Ability.BUILD)
            .insert(1, Ability.MINE)
            .insert(2, Ability.DOORS_AND_SWITCHES)
            .insert(3, Ability.OPEN_CONTAINERS)
            .insert(4, Ability.ATTACK_PLAYERS)
            .insert(5, Ability.ATTACK_MOBS)
            .insert(6, Ability.OPERATOR_COMMANDS)
            .insert(7, Ability.TELEPORT)
            .insert(8, Ability.INVULNERABLE)
            .insert(9, Ability.FLYING)
            .insert(10, Ability.MAY_FLY)
            .insert(11, Ability.INSTABUILD)
            .insert(12, Ability.LIGHTNING)
            .insert(13, Ability.FLY_SPEED)
            .insert(14, Ability.WALK_SPEED)
            .insert(15, Ability.MUTED)
            .insert(16, Ability.WORLD_BUILDER)
            .insert(17, Ability.NO_CLIP)
            .build();

    public static final BedrockCodec CODEC = Bedrock_v527.CODEC.toBuilder()
            .protocolVersion(534)
            .minecraftVersion("1.19.10")
            .helper(() -> new BedrockCodecHelper_v534(ENTITY_DATA, GAME_RULE_TYPES, ITEM_STACK_REQUEST_TYPES, CONTAINER_SLOT_TYPES, PLAYER_ABILITIES))
            .updateSerializer(StartGamePacket.class, StartGameSerializer_v534.INSTANCE)
            .updateSerializer(AddEntityPacket.class, AddEntitySerializer_v534.INSTANCE)
            .updateSerializer(AddPlayerPacket.class, AddPlayerSerializer_v534.INSTANCE)
            .updateSerializer(EntityEventPacket.class, new EntityEventSerializer_v291(ENTITY_EVENTS))
            .updateSerializer(LevelSoundEvent1Packet.class, new LevelSoundEvent1Serializer_v291(SOUND_EVENTS))
            .updateSerializer(LevelSoundEvent2Packet.class, new LevelSoundEvent2Serializer_v313(SOUND_EVENTS))
            .updateSerializer(LevelSoundEventPacket.class, new LevelSoundEventSerializer_v332(SOUND_EVENTS))
            .registerPacket(UpdateAbilitiesPacket::new, UpdateAbilitiesSerializer_v534.INSTANCE, 187, PacketRecipient.CLIENT)
            .registerPacket(UpdateAdventureSettingsPacket::new, UpdateAdventureSettingsSerializer_v534.INSTANCE, 188, PacketRecipient.CLIENT)
            .registerPacket(DeathInfoPacket::new, DeathInfoSerializer_v534.INSTANCE, 189, PacketRecipient.CLIENT)
            .registerPacket(EditorNetworkPacket::new, EditorNetworkSerializer_v534.INSTANCE, 190, PacketRecipient.BOTH)
            .build();
}
