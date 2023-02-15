package org.cloudburstmc.protocol.bedrock.codec.v568;

import org.cloudburstmc.protocol.bedrock.codec.BedrockCodec;
import org.cloudburstmc.protocol.bedrock.codec.v560.Bedrock_v560;

public class Bedrock_v568 extends Bedrock_v560 {

    public static final BedrockCodec CODEC = Bedrock_v560.CODEC.toBuilder()
            .raknetProtocolVersion(11)
            .protocolVersion(568)
            .minecraftVersion("1.19.62")
            .helper(() -> new BedrockCodecHelper_v568(ENTITY_DATA, GAME_RULE_TYPES, ITEM_STACK_REQUEST_TYPES, CONTAINER_SLOT_TYPES))
            .build();
}
