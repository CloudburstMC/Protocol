package org.cloudburstmc.protocol.bedrock.codec.v568;

import org.cloudburstmc.protocol.bedrock.codec.BedrockCodec;
import org.cloudburstmc.protocol.bedrock.codec.v567.Bedrock_v567;

public class Bedrock_v568 extends Bedrock_v567 {

    public static final BedrockCodec CODEC = Bedrock_v567.CODEC.toBuilder()
            .raknetProtocolVersion(11)
            .protocolVersion(568)
            .minecraftVersion("1.19.63")
            .helper(() -> new BedrockCodecHelper_v568(ENTITY_DATA, GAME_RULE_TYPES, ITEM_STACK_REQUEST_TYPES, CONTAINER_SLOT_TYPES, PLAYER_ABILITIES, TEXT_PROCESSING_ORIGINS))
            .build();
}
