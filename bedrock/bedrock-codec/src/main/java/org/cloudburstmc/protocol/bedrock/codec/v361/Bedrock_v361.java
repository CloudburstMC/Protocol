package org.cloudburstmc.protocol.bedrock.codec.v361;

import lombok.experimental.UtilityClass;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodec;
import org.cloudburstmc.protocol.bedrock.codec.v354.Bedrock_v354;
import org.cloudburstmc.protocol.bedrock.codec.v361.serializer.*;
import org.cloudburstmc.protocol.bedrock.packet.*;

@UtilityClass
public class Bedrock_v361 {
    public static BedrockCodec CODEC = Bedrock_v354.CODEC.toBuilder()
            .protocolVersion(361)
            .minecraftVersion("1.12.0")
            .helper(BedrockCodecHelper_v361.INSTANCE)
            .deregisterPacket(AddHangingEntityPacket.class)
            .updateSerializer(StartGamePacket.class, StartGameSerializer_v361.INSTANCE)
            .updateSerializer(AddPaintingPacket.class, AddPaintingSerializer_v361.INSTANCE)
            .updateSerializer(CraftingDataPacket.class, CraftingDataSerializer_v361.INSTANCE)
            .updateSerializer(LevelChunkPacket.class, LevelChunkSerializer_v361.INSTANCE)
            .updateSerializer(CommandBlockUpdatePacket.class, CommandBlockUpdateSerializer_v361.INSTANCE)
            .updateSerializer(ResourcePackDataInfoPacket.class, ResourcePackDataInfoSerializer_v361.INSTANCE)
            .updateSerializer(StructureBlockUpdatePacket.class, StructureBlockUpdateSerializer_v361.INSTANCE)
            .updateSerializer(LevelEventGenericPacket.class, LevelEventGenericSerializer_v361.INSTANCE)
            .updateSerializer(VideoStreamConnectPacket.class, VideoStreamConnectSerializer_v361.INSTANCE)
            // AddEntityPacket 127
            // RemoveEntityPacket 128
            .registerPacket(ClientCacheStatusPacket.class, ClientCacheStatusSerializer_v361.INSTANCE, 129)
            .registerPacket(StructureTemplateDataRequestPacket.class, StructureTemplateDataRequestSerializer_v361.INSTANCE, 132)
            .registerPacket(StructureTemplateDataResponsePacket.class, StructureTemplateDataResponseSerializer_v361.INSTANCE, 133)
            .registerPacket(UpdateBlockPropertiesPacket.class, UpdateBlockPropertiesSerializer_v361.INSTANCE, 134)
            .registerPacket(ClientCacheBlobStatusPacket.class, ClientCacheBlobStatusSerializer_v361.INSTANCE, 135)
            .registerPacket(ClientCacheMissResponsePacket.class, ClientCacheMissResponseSerializer_v361.INSTANCE, 136)
            .build();
}
