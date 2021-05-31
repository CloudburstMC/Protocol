package org.cloudburstmc.protocol.bedrock.codec.v407;

import lombok.experimental.UtilityClass;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodec;
import org.cloudburstmc.protocol.bedrock.codec.v390.Bedrock_v390;
import org.cloudburstmc.protocol.bedrock.codec.v407.serializer.*;
import org.cloudburstmc.protocol.bedrock.packet.*;

@UtilityClass
public class Bedrock_v407 {
    public static BedrockCodec CODEC = Bedrock_v390.CODEC.toBuilder()
            .protocolVersion(407)
            .minecraftVersion("1.16.0")
            .helper(BedrockCodecHelper_v407.INSTANCE)
            .updateSerializer(StartGamePacket.class, StartGameSerializer_v407.INSTANCE)
            .updateSerializer(InventoryTransactionPacket.class, InventoryTransactionSerializer_v407.INSTANCE)
            .updateSerializer(HurtArmorPacket.class, HurtArmorSerializer_v407.INSTANCE)
            .updateSerializer(SetSpawnPositionPacket.class, SetSpawnPositionSerializer_v407.INSTANCE)
            .updateSerializer(InventoryContentPacket.class, InventoryContentSerializer_v407.INSTANCE)
            .updateSerializer(InventorySlotPacket.class, InventorySlotSerializer_v407.INSTANCE)
            .updateSerializer(CraftingDataPacket.class, CraftingDataSerializer_v407.INSTANCE)
            .updateSerializer(LevelSoundEvent2Packet.class, LevelSoundEvent2Serializer_v407.INSTANCE)
            .updateSerializer(LevelSoundEventPacket.class, LevelSoundEventSerializer_v407.INSTANCE)
            .registerPacket(CreativeContentPacket.class, CreativeContentSerializer_v407.INSTANCE, 145)
            .registerPacket(PlayerEnchantOptionsPacket.class, PlayerEnchantOptionsSerializer_v407.INSTANCE, 146)
            .registerPacket(ItemStackRequestPacket.class, ItemStackRequestSerializer_v407.INSTANCE, 147)
            .registerPacket(ItemStackResponsePacket.class, ItemStackResponseSerializer_v407.INSTANCE, 148)
            .registerPacket(PlayerArmorDamagePacket.class, PlayerArmorDamageSerializer_v407.INSTANCE, 149)
            .registerPacket(CodeBuilderPacket.class, CodeBuilderSerializer_v407.INSTANCE, 150)
            .registerPacket(UpdatePlayerGameTypePacket.class, UpdatePlayerGameTypeSerializer_v407.INSTANCE, 151)
            .registerPacket(EmoteListPacket.class, EmoteListSerializer_v407.INSTANCE, 152)
            .registerPacket(PositionTrackingDBServerBroadcastPacket.class, PositionTrackingDBServerBroadcastSerializer_v407.INSTANCE, 153)
            .registerPacket(PositionTrackingDBClientRequestPacket.class, PositionTrackingDBClientRequestSerializer_v407.INSTANCE, 154)
            .registerPacket(DebugInfoPacket.class, DebugInfoSerializer_v407.INSTANCE, 155)
            .registerPacket(PacketViolationWarningPacket.class, PacketViolationWarningSerializer_v407.INSTANCE, 156)
            .build();
}
