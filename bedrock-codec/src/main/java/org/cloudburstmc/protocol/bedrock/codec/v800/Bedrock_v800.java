package org.cloudburstmc.protocol.bedrock.codec.v800;

import org.cloudburstmc.protocol.bedrock.codec.BedrockCodec;
import org.cloudburstmc.protocol.bedrock.codec.EntityDataTypeMap;
import org.cloudburstmc.protocol.bedrock.codec.v776.BedrockCodecHelper_v776;
import org.cloudburstmc.protocol.bedrock.codec.v786.Bedrock_v786;
import org.cloudburstmc.protocol.bedrock.codec.v786.serializer.LevelSoundEventSerializer_v786;
import org.cloudburstmc.protocol.bedrock.codec.v800.serializer.*;
import org.cloudburstmc.protocol.bedrock.data.PacketRecipient;
import org.cloudburstmc.protocol.bedrock.data.SoundEvent;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityDataFormat;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityDataTypes;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityFlag;
import org.cloudburstmc.protocol.bedrock.packet.*;
import org.cloudburstmc.protocol.bedrock.transformer.FlagTransformer;
import org.cloudburstmc.protocol.common.util.TypeMap;

public class Bedrock_v800 extends Bedrock_v786 {

    protected static final TypeMap<EntityFlag> ENTITY_FLAGS = Bedrock_v786.ENTITY_FLAGS
            .toBuilder()
            .insert(123, EntityFlag.DOES_SERVER_AUTH_ONLY_DISMOUNT)
            .build();

    protected static final EntityDataTypeMap ENTITY_DATA = Bedrock_v786.ENTITY_DATA
            .toBuilder()
            .update(EntityDataTypes.FLAGS, new FlagTransformer(ENTITY_FLAGS, 0))
            .update(EntityDataTypes.FLAGS_2, new FlagTransformer(ENTITY_FLAGS, 1))
            .insert(EntityDataTypes.SEAT_THIRD_PERSON_CAMERA_RADIUS, 134, EntityDataFormat.FLOAT)
            .insert(EntityDataTypes.SEAT_CAMERA_RELAX_DISTANCE_SMOOTHING, 135, EntityDataFormat.FLOAT)
            .build();

    protected static final TypeMap<SoundEvent> SOUND_EVENTS = Bedrock_v786.SOUND_EVENTS
            .toBuilder()
            .replace(546, SoundEvent.IMITATE_PHANTOM)
            .insert(547, SoundEvent.IMITATE_ZOGLIN)
            .insert(548, SoundEvent.IMITATE_GUARDIAN)
            .insert(549, SoundEvent.IMITATE_RAVAGER)
            .insert(550, SoundEvent.IMITATE_PILLAGER)
            .insert(551, SoundEvent.PLACE_IN_WATER)
            .insert(552, SoundEvent.STATE_CHANGE)
            .insert(553, SoundEvent.IMITATE_HAPPY_GHAST)
            .insert(554, SoundEvent.UNEQUIP_GENERIC)
            .insert(555, SoundEvent.UNDEFINED)
            .build();

    @SuppressWarnings("deprecation")
    public static final BedrockCodec CODEC = Bedrock_v786.CODEC.toBuilder()
            .raknetProtocolVersion(11)
            .protocolVersion(800)
            .minecraftVersion("1.21.80")
            .helper(() -> new BedrockCodecHelper_v776(ENTITY_DATA, GAME_RULE_TYPES, ITEM_STACK_REQUEST_TYPES, CONTAINER_SLOT_TYPES, PLAYER_ABILITIES, TEXT_PROCESSING_ORIGINS))
            .updateSerializer(LevelSoundEventPacket.class, new LevelSoundEventSerializer_v786(SOUND_EVENTS))
            .updateSerializer(BiomeDefinitionListPacket.class, BiomeDefinitionListSerializer_v800.INSTANCE)
            .updateSerializer(CameraPresetsPacket.class, CameraPresetsSerializer_v800.INSTANCE)
            .updateSerializer(PlayerListPacket.class, PlayerListSerializer_v800.INSTANCE)
            .updateSerializer(CameraAimAssistPresetsPacket.class, CameraAimAssistPresetsSerializer_v800.INSTANCE)
            .registerPacket(PlayerLocationPacket::new, PlayerLocationSerializer_v800.INSTANCE, 326, PacketRecipient.BOTH)
            .registerPacket(ClientboundControlSchemeSetPacket::new, ClientboundControlSchemeSetSerializer_v800.INSTANCE, 327, PacketRecipient.CLIENT)
            .deregisterPacket(CompressedBiomeDefinitionListPacket.class)
            .deregisterPacket(PlayerInputPacket.class)
            .deregisterPacket(RiderJumpPacket.class)
            .build();
}
