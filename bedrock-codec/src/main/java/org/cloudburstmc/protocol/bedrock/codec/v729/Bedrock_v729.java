package org.cloudburstmc.protocol.bedrock.codec.v729;

import org.cloudburstmc.protocol.bedrock.codec.BedrockCodec;
import org.cloudburstmc.protocol.bedrock.codec.v291.serializer.LevelSoundEvent1Serializer_v291;
import org.cloudburstmc.protocol.bedrock.codec.v313.serializer.LevelSoundEvent2Serializer_v313;
import org.cloudburstmc.protocol.bedrock.codec.v332.serializer.LevelSoundEventSerializer_v332;
import org.cloudburstmc.protocol.bedrock.codec.v712.Bedrock_v712;
import org.cloudburstmc.protocol.bedrock.codec.v729.serializer.*;
import org.cloudburstmc.protocol.bedrock.data.PacketRecipient;
import org.cloudburstmc.protocol.bedrock.data.SoundEvent;
import org.cloudburstmc.protocol.bedrock.packet.*;
import org.cloudburstmc.protocol.common.util.TypeMap;

public class Bedrock_v729 extends Bedrock_v712 {

    protected static final TypeMap<SoundEvent> SOUND_EVENTS = Bedrock_v712.SOUND_EVENTS
            .toBuilder()
            .replace(531, SoundEvent.IMITATE_DROWNED)
            // skip 532
            .insert(533, SoundEvent.BUNDLE_INSERT_FAILED)
            .insert(534, SoundEvent.UNDEFINED)
            .build();

    public static final BedrockCodec CODEC = Bedrock_v712.CODEC.toBuilder()
            .raknetProtocolVersion(11)
            .protocolVersion(729)
            .minecraftVersion("1.21.30")
            .helper(() -> new BedrockCodecHelper_v729(ENTITY_DATA, GAME_RULE_TYPES, ITEM_STACK_REQUEST_TYPES, CONTAINER_SLOT_TYPES, PLAYER_ABILITIES, TEXT_PROCESSING_ORIGINS))
            .updateSerializer(LevelSoundEvent1Packet.class, new LevelSoundEvent1Serializer_v291(SOUND_EVENTS))
            .updateSerializer(LevelSoundEvent2Packet.class, new LevelSoundEvent2Serializer_v313(SOUND_EVENTS))
            .updateSerializer(LevelSoundEventPacket.class, new LevelSoundEventSerializer_v332(SOUND_EVENTS))
            .updateSerializer(EmotePacket.class, EmoteSerializer_v729.INSTANCE)
            .updateSerializer(InventoryContentPacket.class, InventoryContentSerializer_v729.INSTANCE)
            .updateSerializer(InventorySlotPacket.class, InventorySlotSerializer_v729.INSTANCE)
            .updateSerializer(ResourcePacksInfoPacket.class, ResourcePacksInfoSerializer_v729.INSTANCE)
            .updateSerializer(TransferPacket.class, TransferSerializer_v729.INSTANCE)
            .updateSerializer(UpdateAttributesPacket.class, UpdateAttributesSerializer_v729.INSTANCE)
            .updateSerializer(CameraPresetsPacket.class, CameraPresetsSerializer_v729.INSTANCE)
            .registerPacket(CameraAimAssistPacket::new, CameraAimAssistSerializer_v729.INSTANCE, 316, PacketRecipient.CLIENT)
            .registerPacket(ContainerRegistryCleanupPacket::new, ContainerRegistryCleanupSerializer_v729.INSTANCE, 317, PacketRecipient.CLIENT)
            .build();
}