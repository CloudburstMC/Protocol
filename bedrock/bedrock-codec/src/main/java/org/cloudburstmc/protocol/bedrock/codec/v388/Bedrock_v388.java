package org.cloudburstmc.protocol.bedrock.codec.v388;

import lombok.experimental.UtilityClass;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodec;
import org.cloudburstmc.protocol.bedrock.codec.v361.Bedrock_v361;
import org.cloudburstmc.protocol.bedrock.codec.v388.serializer.*;
import org.cloudburstmc.protocol.bedrock.packet.*;

@UtilityClass
public class Bedrock_v388 {
    public static final BedrockCodec CODEC = Bedrock_v361.CODEC.toBuilder()
            .protocolVersion(388)
            .minecraftVersion("1.13.0")
            .helper(BedrockCodecHelper_v388.INSTANCE)
            .deregisterPacket(ExplodePacket.class)
            .updateSerializer(ResourcePackStackPacket.class, ResourcePackStackSerializer_v388.INSTANCE)
            .updateSerializer(StartGamePacket.class, StartGameSerializer_v388.INSTANCE)
            .updateSerializer(AddPlayerPacket.class, AddPlayerSerializer_v388.INSTANCE)
            .updateSerializer(InteractPacket.class, InteractSerializer_v388.INSTANCE)
            .updateSerializer(RespawnPacket.class, RespawnSerializer_v388.INSTANCE)
            .updateSerializer(CraftingDataPacket.class, CraftingDataSerializer_v388.INSTANCE)
            .updateSerializer(PlayerListPacket.class, PlayerListSerializer_v388.INSTANCE)
            .updateSerializer(EventPacket.class, EventSerializer_v388.INSTANCE)
            .updateSerializer(AvailableCommandsPacket.class, AvailableCommandsSerializer_v388.INSTANCE)
            .updateSerializer(ResourcePackChunkDataPacket.class, ResourcePackChunkDataSerializer_v388.INSTANCE)
            .updateSerializer(StructureBlockUpdatePacket.class, StructureBlockUpdateSerializer_v388.INSTANCE)
            .updateSerializer(PlayerSkinPacket.class, PlayerSkinSerializer_v388.INSTANCE)
            .updateSerializer(MoveEntityDeltaPacket.class, MoveEntityDeltaSerializer_v388.INSTANCE)
            .updateSerializer(StructureTemplateDataResponsePacket.class, StructureTemplateDataResponseSerializer_v388.INSTANCE)
            .registerPacket(TickSyncPacket.class, TickSyncSerializer_v388.INSTANCE, 23)
            .registerPacket(EducationSettingsPacket.class, EducationSettingsSerializer_v388.INSTANCE, 137)
            .registerPacket(EmotePacket.class, EmoteSerializer_v388.INSTANCE, 138)
            .registerPacket(MultiplayerSettingsPacket.class, MultiplayerSettingsSerializer_v388.INSTANCE, 139)
            .registerPacket(SettingsCommandPacket.class, SettingsCommandSerializer_v388.INSTANCE, 140)
            .registerPacket(AnvilDamagePacket.class, AnvilDamageSerializer_v388.INSTANCE, 141)
            .registerPacket(CompletedUsingItemPacket.class, CompletedUsingItemSerializer_v388.INSTANCE, 142)
            .registerPacket(NetworkSettingsPacket.class, NetworkSettingsSerializer_v388.INSTANCE, 143)
            .registerPacket(PlayerAuthInputPacket.class, PlayerAuthInputSerializer_v388.INSTANCE, 144)
            .build();
}
