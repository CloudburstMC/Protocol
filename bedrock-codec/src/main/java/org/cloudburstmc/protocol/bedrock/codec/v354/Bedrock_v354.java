package org.cloudburstmc.protocol.bedrock.codec.v354;

import org.cloudburstmc.protocol.bedrock.codec.BedrockCodec;
import org.cloudburstmc.protocol.bedrock.codec.EntityDataTypeMap;
import org.cloudburstmc.protocol.bedrock.codec.v291.serializer.LevelEventSerializer_v291;
import org.cloudburstmc.protocol.bedrock.codec.v291.serializer.LevelSoundEvent1Serializer_v291;
import org.cloudburstmc.protocol.bedrock.codec.v313.serializer.LevelSoundEvent2Serializer_v313;
import org.cloudburstmc.protocol.bedrock.codec.v332.serializer.LevelSoundEventSerializer_v332;
import org.cloudburstmc.protocol.bedrock.codec.v340.BedrockCodecHelper_v340;
import org.cloudburstmc.protocol.bedrock.codec.v340.Bedrock_v340;
import org.cloudburstmc.protocol.bedrock.codec.v354.serializer.*;
import org.cloudburstmc.protocol.bedrock.data.LevelEvent;
import org.cloudburstmc.protocol.bedrock.data.LevelEventType;
import org.cloudburstmc.protocol.bedrock.data.PacketRecipient;
import org.cloudburstmc.protocol.bedrock.data.SoundEvent;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityDataFormat;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityDataTypes;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityFlag;
import org.cloudburstmc.protocol.bedrock.packet.*;
import org.cloudburstmc.protocol.bedrock.transformer.FlagTransformer;
import org.cloudburstmc.protocol.common.util.TypeMap;

public class Bedrock_v354 extends Bedrock_v340 {

    protected static final TypeMap<EntityFlag> ENTITY_FLAGS = Bedrock_v340.ENTITY_FLAGS.toBuilder()
            .shift(74, 1)
            .insert(74, EntityFlag.BLOCKED_USING_DAMAGED_SHIELD)
            .insert(81, EntityFlag.IS_ILLAGER_CAPTAIN)
            .insert(82, EntityFlag.STUNNED)
            .insert(83, EntityFlag.ROARING)
            .insert(84, EntityFlag.DELAYED_ATTACK)
            .insert(85, EntityFlag.IS_AVOIDING_MOBS)
            .insert(86, EntityFlag.FACING_TARGET_TO_RANGE_ATTACK)
            .build();

    protected static final EntityDataTypeMap ENTITY_DATA = Bedrock_v340.ENTITY_DATA.toBuilder()
            .update(EntityDataTypes.FLAGS, new FlagTransformer(ENTITY_FLAGS, 0))
            .update(EntityDataTypes.FLAGS_2, new FlagTransformer(ENTITY_FLAGS, 1))
            .insert(EntityDataTypes.TRADE_EXPERIENCE, 102, EntityDataFormat.INT)
            .build();

    protected static final TypeMap<SoundEvent> SOUND_EVENTS = Bedrock_v340.SOUND_EVENTS.toBuilder()
            .insert(187, SoundEvent.FLETCHING_TABLE_USE)
            .replace(257, SoundEvent.GRINDSTONE_USE)
            .insert(258, SoundEvent.BELL)
            .insert(259, SoundEvent.CAMPFIRE_CRACKLE)
            .insert(262, SoundEvent.SWEET_BERRY_BUSH_HURT)
            .insert(263, SoundEvent.SWEET_BERRY_BUSH_PICK)
            .insert(260, SoundEvent.ROAR)
            .insert(261, SoundEvent.STUN)
            .insert(264, SoundEvent.CARTOGRAPHY_TABLE_USE)
            .insert(265, SoundEvent.STONECUTTER_USE)
            .insert(266, SoundEvent.COMPOSTER_EMPTY)
            .insert(267, SoundEvent.COMPOSTER_FILL)
            .insert(268, SoundEvent.COMPOSTER_FILL_LAYER)
            .insert(269, SoundEvent.COMPOSTER_READY)
            .insert(270, SoundEvent.BARREL_OPEN)
            .insert(271, SoundEvent.BARREL_CLOSE)
            .insert(272, SoundEvent.RAID_HORN)
            .insert(273, SoundEvent.LOOM_USE)
            .insert(274, SoundEvent.UNDEFINED)
            .build();

    protected static final TypeMap<LevelEventType> LEVEL_EVENTS = Bedrock_v340.LEVEL_EVENTS.toBuilder()
            .insert(LEVEL_EVENT_PARTICLE + 22, LevelEvent.PARTICLE_KNOCKBACK_ROAR)
            .build();


    public static final BedrockCodec CODEC = Bedrock_v340.CODEC.toBuilder()
            .protocolVersion(354)
            .minecraftVersion("1.11.0")
            .helper(() -> new BedrockCodecHelper_v340(ENTITY_DATA, GAME_RULE_TYPES))
            .updateSerializer(CraftingDataPacket.class, CraftingDataSerializer_v354.INSTANCE)
            .updateSerializer(EventPacket.class, EventSerializer_v354.INSTANCE)
            .updateSerializer(ClientboundMapItemDataPacket.class, ClientboundMapItemDataSerializer_v354.INSTANCE)
            .updateSerializer(UpdateTradePacket.class, UpdateTradeSerializer_v354.INSTANCE)
            .updateSerializer(LecternUpdatePacket.class, LecternUpdateSerializer_v354.INSTANCE)
            .updateSerializer(LevelEventPacket.class, new LevelEventSerializer_v291(LEVEL_EVENTS))
            .updateSerializer(LevelSoundEvent1Packet.class, new LevelSoundEvent1Serializer_v291(SOUND_EVENTS))
            .updateSerializer(LevelSoundEvent2Packet.class, new LevelSoundEvent2Serializer_v313(SOUND_EVENTS))
            .updateSerializer(LevelSoundEventPacket.class, new LevelSoundEventSerializer_v332(SOUND_EVENTS))
            .registerPacket(OnScreenTextureAnimationPacket::new, OnScreenTextureAnimationSerializer_v354.INSTANCE, 130, PacketRecipient.CLIENT)
            .registerPacket(MapCreateLockedCopyPacket::new, MapCreateLockedCopySerializer_v354.INSTANCE, 131, PacketRecipient.SERVER)
            .build();
}
