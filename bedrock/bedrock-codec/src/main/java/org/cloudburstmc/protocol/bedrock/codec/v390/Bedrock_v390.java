package org.cloudburstmc.protocol.bedrock.codec.v390;

import lombok.experimental.UtilityClass;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodec;
import org.cloudburstmc.protocol.bedrock.codec.v389.Bedrock_v389;
import org.cloudburstmc.protocol.bedrock.codec.v390.serializer.PlayerListSerializer_v390;
import org.cloudburstmc.protocol.bedrock.codec.v390.serializer.PlayerSkinSerializer_v390;
import org.cloudburstmc.protocol.bedrock.packet.PlayerListPacket;
import org.cloudburstmc.protocol.bedrock.packet.PlayerSkinPacket;

@UtilityClass
public class Bedrock_v390 {
    public static BedrockCodec CODEC = Bedrock_v389.CODEC.toBuilder()
            .protocolVersion(390)
            .minecraftVersion("1.14.60")
            .helper(BedrockCodecHelper_v390.INSTANCE)
            .updateSerializer(PlayerListPacket.class, PlayerListSerializer_v390.INSTANCE)
            .updateSerializer(PlayerSkinPacket.class, PlayerSkinSerializer_v390.INSTANCE)
            .build();
}
