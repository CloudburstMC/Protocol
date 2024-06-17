package org.cloudburstmc.protocol.bedrock.codec.v705;

import org.cloudburstmc.protocol.bedrock.codec.BedrockCodec;
import org.cloudburstmc.protocol.bedrock.codec.v291.serializer.LevelSoundEvent1Serializer_v291;
import org.cloudburstmc.protocol.bedrock.codec.v313.serializer.LevelSoundEvent2Serializer_v313;
import org.cloudburstmc.protocol.bedrock.codec.v332.serializer.LevelSoundEventSerializer_v332;
import org.cloudburstmc.protocol.bedrock.codec.v685.Bedrock_v685;
import org.cloudburstmc.protocol.bedrock.codec.v705.serializer.*;
import org.cloudburstmc.protocol.bedrock.data.PacketRecipient;
import org.cloudburstmc.protocol.bedrock.data.SoundEvent;
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.action.ItemStackRequestActionType;
import org.cloudburstmc.protocol.bedrock.packet.*;
import org.cloudburstmc.protocol.common.util.TypeMap;

public class Bedrock_v705 extends Bedrock_v685 {

    protected static final TypeMap<SoundEvent> SOUND_EVENTS = Bedrock_v685.SOUND_EVENTS
            .toBuilder()
            .insert(510, SoundEvent.IMITATE_BOGGED)
            .replace(530, SoundEvent.VAULT_REJECT_REWARDED_PLAYER)
            .insert(531, SoundEvent.UNDEFINED)
            .build();

    protected static final TypeMap<ItemStackRequestActionType> ITEM_STACK_REQUEST_TYPES = Bedrock_v685.ITEM_STACK_REQUEST_TYPES
            .toBuilder()
            .remove(7)
            .remove(8)
            .build();

    public static final BedrockCodec CODEC = Bedrock_v685.CODEC.toBuilder()
            .raknetProtocolVersion(11)
            .protocolVersion(705)
            .minecraftVersion("1.21.10")
            .helper(() -> new BedrockCodecHelper_v705(ENTITY_DATA, GAME_RULE_TYPES, ITEM_STACK_REQUEST_TYPES, CONTAINER_SLOT_TYPES, PLAYER_ABILITIES, TEXT_PROCESSING_ORIGINS))
            .updateSerializer(LevelSoundEvent1Packet.class, new LevelSoundEvent1Serializer_v291(SOUND_EVENTS))
            .updateSerializer(LevelSoundEvent2Packet.class, new LevelSoundEvent2Serializer_v313(SOUND_EVENTS))
            .updateSerializer(LevelSoundEventPacket.class, new LevelSoundEventSerializer_v332(SOUND_EVENTS))
            .updateSerializer(CameraPresetsPacket.class, CameraPresetsSerializer_v705.INSTANCE)
            .updateSerializer(ChangeDimensionPacket.class, ChangeDimensionSerializer_v705.INSTANCE)
            .updateSerializer(DisconnectPacket.class, DisconnectSerializer_v705.INSTANCE)
            .updateSerializer(EditorNetworkPacket.class, EditorNetworkSerializer_v705.INSTANCE)
            .updateSerializer(MobArmorEquipmentPacket.class, MobArmorEquipmentSerializer_v705.INSTANCE)
            .updateSerializer(PlayerArmorDamagePacket.class, PlayerArmorDamageSerializer_v705.INSTANCE)
            .updateSerializer(PlayerAuthInputPacket.class, PlayerAuthInputSerializer_v705.INSTANCE)
            .updateSerializer(SetTitlePacket.class, SetTitleSerializer_v705.INSTANCE)
            .updateSerializer(StopSoundPacket.class, StopSoundSerializer_v705.INSTANCE)
            .registerPacket(ClientboundCloseFormPacket::new, ClientboundCloseFormSerializer_v705.INSTANCE, 310, PacketRecipient.CLIENT)
            .registerPacket(ServerboundLoadingScreenPacket::new, ServerboundLoadingScreenSerializer_v705.INSTANCE, 312, PacketRecipient.SERVER)
            .registerPacket(JigsawStructureDataPacket::new, JigsawStructureDataSerializer_v705.INSTANCE, 313, PacketRecipient.CLIENT)
            .build();
}