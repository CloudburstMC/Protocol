package org.cloudburstmc.protocol.bedrock.codec.v408;

import lombok.experimental.UtilityClass;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodec;
import org.cloudburstmc.protocol.bedrock.codec.v407.Bedrock_v407;

@UtilityClass
public class Bedrock_v408 {
    public static BedrockCodec CODEC = Bedrock_v407.CODEC.toBuilder()
            .protocolVersion(408)
            .minecraftVersion("1.16.20")
            .build();
}
