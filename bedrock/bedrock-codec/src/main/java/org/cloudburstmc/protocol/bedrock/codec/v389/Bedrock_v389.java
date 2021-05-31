package org.cloudburstmc.protocol.bedrock.codec.v389;

import lombok.experimental.UtilityClass;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodec;
import org.cloudburstmc.protocol.bedrock.codec.v388.Bedrock_v388;
import org.cloudburstmc.protocol.bedrock.codec.v389.serializer.EventSerializer_v389;
import org.cloudburstmc.protocol.bedrock.packet.EventPacket;

@UtilityClass
public class Bedrock_v389 {
    public static BedrockCodec CODEC = Bedrock_v388.CODEC.toBuilder()
            .protocolVersion(389)
            .minecraftVersion("1.14.0")
            .helper(BedrockCodecHelper_v389.INSTANCE)
            .updateSerializer(EventPacket.class, EventSerializer_v389.INSTANCE)
            .build();
}
