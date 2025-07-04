package org.cloudburstmc.protocol.bedrock.codec.v819;

import org.cloudburstmc.protocol.bedrock.codec.BedrockCodec;
import org.cloudburstmc.protocol.bedrock.codec.v818.Bedrock_v818;

/**
 * @author daoge_cmd
 */
public class Bedrock_v819 extends Bedrock_v818 {

    public static final BedrockCodec CODEC = Bedrock_v818.CODEC.toBuilder()
            .minecraftVersion("1.21.93")
            .protocolVersion(819)
            .build();

}
