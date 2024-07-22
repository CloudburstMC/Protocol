package org.cloudburstmc.protocol.bedrock.codec.v712;

import org.cloudburstmc.protocol.bedrock.codec.BedrockCodec;
import org.cloudburstmc.protocol.bedrock.codec.v291.serializer.LevelSoundEvent1Serializer_v291;
import org.cloudburstmc.protocol.bedrock.codec.v313.serializer.LevelSoundEvent2Serializer_v313;
import org.cloudburstmc.protocol.bedrock.codec.v332.serializer.LevelSoundEventSerializer_v332;
import org.cloudburstmc.protocol.bedrock.codec.v686.Bedrock_v686;
import org.cloudburstmc.protocol.bedrock.codec.v712.serializer.*;
import org.cloudburstmc.protocol.bedrock.data.PacketRecipient;
import org.cloudburstmc.protocol.bedrock.data.SoundEvent;
import org.cloudburstmc.protocol.bedrock.data.inventory.ContainerSlotType;
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.action.ItemStackRequestActionType;
import org.cloudburstmc.protocol.bedrock.packet.*;
import org.cloudburstmc.protocol.common.util.TypeMap;

public class Bedrock_v712 extends Bedrock_v686 {

    protected static final TypeMap<SoundEvent> SOUND_EVENTS = Bedrock_v686.SOUND_EVENTS
            .toBuilder()
            .insert(510, SoundEvent.IMITATE_BOGGED)
            .replace(530, SoundEvent.VAULT_REJECT_REWARDED_PLAYER)
            .insert(531, SoundEvent.UNDEFINED)
            .build();

    protected static final TypeMap<ItemStackRequestActionType> ITEM_STACK_REQUEST_TYPES = Bedrock_v686.ITEM_STACK_REQUEST_TYPES
            .toBuilder()
            .remove(7)
            .remove(8)
            .build();

    protected static final TypeMap<ContainerSlotType> CONTAINER_SLOT_TYPES = Bedrock_v686.CONTAINER_SLOT_TYPES
            .toBuilder()
            .insert(63, ContainerSlotType.DYNAMIC_CONTAINER)
            .build();

    public static final BedrockCodec CODEC = Bedrock_v686.CODEC.toBuilder()
            .raknetProtocolVersion(11)
            .protocolVersion(712)
            .minecraftVersion("1.21.20")
            .helper(() -> new BedrockCodecHelper_v712(ENTITY_DATA, GAME_RULE_TYPES, ITEM_STACK_REQUEST_TYPES, CONTAINER_SLOT_TYPES, PLAYER_ABILITIES, TEXT_PROCESSING_ORIGINS))
            .updateSerializer(LevelSoundEvent1Packet.class, new LevelSoundEvent1Serializer_v291(SOUND_EVENTS))
            .updateSerializer(LevelSoundEvent2Packet.class, new LevelSoundEvent2Serializer_v313(SOUND_EVENTS))
            .updateSerializer(LevelSoundEventPacket.class, new LevelSoundEventSerializer_v332(SOUND_EVENTS))
            .updateSerializer(CameraInstructionPacket.class, CameraInstructionSerializer_v712.INSTANCE)
            .updateSerializer(CameraPresetsPacket.class, CameraPresetsSerializer_v712.INSTANCE)
            .updateSerializer(ChangeDimensionPacket.class, ChangeDimensionSerializer_v712.INSTANCE)
            .updateSerializer(DisconnectPacket.class, DisconnectSerializer_v712.INSTANCE)
            .updateSerializer(EditorNetworkPacket.class, EditorNetworkSerializer_v712.INSTANCE)
            .updateSerializer(InventoryContentPacket.class, InventoryContentSerializer_v712.INSTANCE)
            .updateSerializer(InventorySlotPacket.class, InventorySlotSerializer_v712.INSTANCE)
            .updateSerializer(MobArmorEquipmentPacket.class, MobArmorEquipmentSerializer_v712.INSTANCE)
            .updateSerializer(PlayerArmorDamagePacket.class, PlayerArmorDamageSerializer_v712.INSTANCE)
            .updateSerializer(PlayerAuthInputPacket.class, PlayerAuthInputSerializer_v712.INSTANCE)
            .updateSerializer(ResourcePacksInfoPacket.class, ResourcePacksInfoSerializer_v712.INSTANCE)
            .updateSerializer(SetTitlePacket.class, SetTitleSerializer_v712.INSTANCE)
            .updateSerializer(StopSoundPacket.class, StopSoundSerializer_v712.INSTANCE)
            .registerPacket(ServerboundLoadingScreenPacket::new, ServerboundLoadingScreenSerializer_v712.INSTANCE, 312, PacketRecipient.SERVER)
            .registerPacket(JigsawStructureDataPacket::new, JigsawStructureDataSerializer_v712.INSTANCE, 313, PacketRecipient.CLIENT)
            .registerPacket(CurrentStructureFeaturePacket::new, CurrentStructureFeatureSerializer_v712.INSTANCE, 314, PacketRecipient.CLIENT)
            .registerPacket(ServerboundDiagnosticsPacket::new, ServerboundDiagnosticsSerializer_v712.INSTANCE, 315, PacketRecipient.SERVER)
            .build();
}