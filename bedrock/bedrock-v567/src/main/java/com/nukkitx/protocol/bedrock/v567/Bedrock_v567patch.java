package com.nukkitx.protocol.bedrock.v567;

import com.nukkitx.protocol.bedrock.BedrockPacketCodec;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Bedrock_v567patch {
    public static final BedrockPacketCodec BEDROCK_V567PATCH = Bedrock_v567.V567_CODEC.toBuilder()
            .helper(BedrockPacketHelper_v567patch.INSTANCE)
            .build();
}
