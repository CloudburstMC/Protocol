package com.nukkitx.protocol.bedrock.v545;

import com.nukkitx.protocol.bedrock.BedrockPacketCodec;
import com.nukkitx.protocol.bedrock.v544.Bedrock_v544;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Bedrock_v545 {
    public static final BedrockPacketCodec V545_CODEC = Bedrock_v544.V544_CODEC.toBuilder()
            .protocolVersion(545)
            .minecraftVersion("1.19.21")
            .build();
}