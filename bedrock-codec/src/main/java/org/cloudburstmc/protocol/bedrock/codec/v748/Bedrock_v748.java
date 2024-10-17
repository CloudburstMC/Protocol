package org.cloudburstmc.protocol.bedrock.codec.v748;

import org.cloudburstmc.protocol.bedrock.codec.BedrockCodec;
import org.cloudburstmc.protocol.bedrock.codec.v729.BedrockCodecHelper_v729;
import org.cloudburstmc.protocol.bedrock.codec.v729.Bedrock_v729;
import org.cloudburstmc.protocol.bedrock.codec.v748.serializer.*;
import org.cloudburstmc.protocol.bedrock.data.PacketRecipient;
import org.cloudburstmc.protocol.bedrock.packet.*;

public class Bedrock_v748 extends Bedrock_v729 {

    public static final BedrockCodec CODEC = Bedrock_v729.CODEC.toBuilder()
            .raknetProtocolVersion(11)
            .protocolVersion(748)
            .minecraftVersion("1.21.40")
            .helper(() -> new BedrockCodecHelper_v729(ENTITY_DATA, GAME_RULE_TYPES, ITEM_STACK_REQUEST_TYPES, CONTAINER_SLOT_TYPES, PLAYER_ABILITIES, TEXT_PROCESSING_ORIGINS))
            .updateSerializer(CameraInstructionPacket.class, CameraInstructionSerializer_v748.INSTANCE)
            .updateSerializer(CameraPresetsPacket.class, CameraPresetsSerializer_v748.INSTANCE)
            .updateSerializer(CraftingDataPacket.class, CraftingDataSerializer_v748.INSTANCE)
            .updateSerializer(InventoryContentPacket.class, InventoryContentSerializer_v748.INSTANCE)
            .updateSerializer(InventorySlotPacket.class, InventorySlotSerializer_v748.INSTANCE)
            .updateSerializer(MobEffectPacket.class, MobEffectSerializer_v748.INSTANCE)
            .updateSerializer(PlayerAuthInputPacket.class, PlayerAuthInputSerializer_v748.INSTANCE)
            .updateSerializer(ResourcePacksInfoPacket.class, ResourcePacksInfoSerializer_v748.INSTANCE)
            .registerPacket(MovementEffectPacket::new, MovementEffectSerializer_v748.INSTANCE, 318, PacketRecipient.CLIENT)
            .registerPacket(SetMovementAuthorityPacket::new, SetMovementAuthoritySerializer_v748.INSTANCE, 319, PacketRecipient.CLIENT)
            .build();
}