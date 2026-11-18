package org.cloudburstmc.protocol.bedrock.codec.v2168;

import org.cloudburstmc.protocol.bedrock.codec.BedrockCodec;
import org.cloudburstmc.protocol.bedrock.codec.v2168.serializer.*;
import org.cloudburstmc.protocol.bedrock.packet.*;

public class Bedrock_v2168_hotfix4 extends Bedrock_v2168 {

    public static final BedrockCodec CODEC = Bedrock_v2168.CODEC.toBuilder()
            .protocolVersion(2168)
            .minecraftVersion("1.26.44")
            .helper(() -> new BedrockCodecHelper_v2168(ENTITY_DATA, GAME_RULE_TYPES, ITEM_STACK_REQUEST_TYPES, CONTAINER_SLOT_TYPES, PLAYER_ABILITIES, TEXT_PROCESSING_ORIGINS, ITEM_STACK_REQUEST_ACTION_ENUM_VALUES))
            .updateSerializer(SetScorePacket.class, SetScoreSerializer_v2168_hotfix4.INSTANCE)
            .build();
}
