package org.cloudburstmc.protocol.bedrock.codec.v390;

import org.cloudburstmc.protocol.bedrock.codec.BedrockCodec;
import org.cloudburstmc.protocol.bedrock.codec.v389.Bedrock_v389;
import org.cloudburstmc.protocol.bedrock.codec.v390.serializer.PlayerListSerializer_v390;
import org.cloudburstmc.protocol.bedrock.codec.v390.serializer.PlayerSkinSerializer_v390;
import org.cloudburstmc.protocol.bedrock.packet.PlayerListPacket;
import org.cloudburstmc.protocol.bedrock.packet.PlayerSkinPacket;

public class Bedrock_v390 extends Bedrock_v389 {

    public static BedrockCodec CODEC = Bedrock_v389.CODEC.toBuilder()
            .protocolVersion(390)
            .minecraftVersion("1.14.60")
            .helper(() -> new BedrockCodecHelper_v390(ENTITY_DATA, GAME_RULE_TYPES))
            .updateSerializer(PlayerListPacket.class, PlayerListSerializer_v390.INSTANCE)
            .updateSerializer(PlayerSkinPacket.class, PlayerSkinSerializer_v390.INSTANCE)
            .build();
}
