package org.cloudburstmc.protocol.bedrock.codec.v408;

import org.cloudburstmc.protocol.bedrock.codec.BedrockCodec;
import org.cloudburstmc.protocol.bedrock.codec.v407.Bedrock_v407;

public class Bedrock_v408 extends Bedrock_v407 {
    public static BedrockCodec CODEC = Bedrock_v407.CODEC.toBuilder()
            .protocolVersion(408)
            .minecraftVersion("1.16.20")
            .build();
}
