package org.cloudburstmc.protocol.bedrock.codec.v544;

import org.cloudburstmc.protocol.bedrock.codec.BedrockCodec;
import org.cloudburstmc.protocol.bedrock.codec.v503.BedrockCodecHelper_v503;
import org.cloudburstmc.protocol.bedrock.codec.v527.Bedrock_v527;
import org.cloudburstmc.protocol.bedrock.codec.v534.Bedrock_v534;
import org.cloudburstmc.protocol.bedrock.codec.v544.serializer.*;
import org.cloudburstmc.protocol.bedrock.packet.*;

public class Bedrock_v544 extends Bedrock_v534 {

    public static final BedrockCodec CODEC = Bedrock_v527.CODEC.toBuilder()
            .protocolVersion(544)
            .minecraftVersion("1.19.20")
            .helper(() -> new BedrockCodecHelper_v503(ENTITY_DATA, GAME_RULE_TYPES, ITEM_STACK_REQUEST_TYPES))
            .updateSerializer(StartGamePacket.class, new StartGameSerializer_v544())
            .updateSerializer(UpdateAttributesPacket.class, new UpdateAttributesSerializer_v544())
            .updateSerializer(ClientboundMapItemDataPacket.class, new ClientboundMapItemDataSerializer_v544())
            .updateSerializer(MapInfoRequestPacket.class, new MapInfoRequestSerializer_v544())
            .updateSerializer(ModalFormResponsePacket.class, new ModalFormResponseSerializer_v544())
            .updateSerializer(NetworkChunkPublisherUpdatePacket.class, new NetworkChunkPublisherUpdateSerializer_v544())
            .registerPacket(FeatureRegistryPacket.class, new FeatureRegistrySerializer_v544(), 191)
            .build();

}
