package org.cloudburstmc.protocol.bedrock.codec.v431;

import lombok.experimental.UtilityClass;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodec;
import org.cloudburstmc.protocol.bedrock.codec.v428.Bedrock_v428;
import org.cloudburstmc.protocol.bedrock.codec.v431.serializer.PlayerAuthInputSerializer_v431;
import org.cloudburstmc.protocol.bedrock.packet.PlayerAuthInputPacket;

@UtilityClass
public class Bedrock_v431 {
    public static BedrockCodec CODEC = Bedrock_v428.CODEC.toBuilder()
            .protocolVersion(431)
            .minecraftVersion("1.16.220")
            .helper(BedrockCodecHelper_v431.INSTANCE)
            .registerPacket(PlayerAuthInputPacket.class, PlayerAuthInputSerializer_v431.INSTANCE, 144)
            .build();
}
