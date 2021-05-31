package org.cloudburstmc.protocol.bedrock.codec.v422;

import lombok.experimental.UtilityClass;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodec;
import org.cloudburstmc.protocol.bedrock.codec.v419.Bedrock_v419;
import org.cloudburstmc.protocol.bedrock.codec.v422.serializer.FilterTextSerializer_v422;
import org.cloudburstmc.protocol.bedrock.codec.v422.serializer.ItemStackResponseSerializer_v422;
import org.cloudburstmc.protocol.bedrock.codec.v422.serializer.ResourcePacksInfoSerializer_v422;
import org.cloudburstmc.protocol.bedrock.packet.FilterTextPacket;
import org.cloudburstmc.protocol.bedrock.packet.ItemStackResponsePacket;
import org.cloudburstmc.protocol.bedrock.packet.ResourcePacksInfoPacket;

@UtilityClass
public class Bedrock_v422 {
    public static BedrockCodec CODEC = Bedrock_v419.CODEC.toBuilder()
            .protocolVersion(422)
            .minecraftVersion("1.16.200")
            .helper(BedrockCodecHelper_v422.INSTANCE)
            .updateSerializer(ResourcePacksInfoPacket.class, ResourcePacksInfoSerializer_v422.INSTANCE)
            .updateSerializer(ItemStackResponsePacket.class, ItemStackResponseSerializer_v422.INSTANCE)
            .registerPacket(FilterTextPacket.class, FilterTextSerializer_v422.INSTANCE, 163)
            .build();

}
