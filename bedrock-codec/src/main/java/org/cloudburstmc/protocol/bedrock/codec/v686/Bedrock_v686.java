package org.cloudburstmc.protocol.bedrock.codec.v686;

import org.cloudburstmc.protocol.bedrock.codec.BedrockCodec;
import org.cloudburstmc.protocol.bedrock.codec.v575.BedrockCodecHelper_v575;
import org.cloudburstmc.protocol.bedrock.codec.v685.Bedrock_v685;
import org.cloudburstmc.protocol.bedrock.codec.v686.serializer.ClientboundCloseFormSerializer_v686;
import org.cloudburstmc.protocol.bedrock.data.PacketRecipient;
import org.cloudburstmc.protocol.bedrock.packet.ClientboundCloseFormPacket;

public class Bedrock_v686 extends Bedrock_v685 {

    public static final BedrockCodec CODEC = Bedrock_v685.CODEC.toBuilder()
            .raknetProtocolVersion(11)
            .protocolVersion(686)
            .minecraftVersion("1.21.2")
            .helper(() -> new BedrockCodecHelper_v575(ENTITY_DATA, GAME_RULE_TYPES, ITEM_STACK_REQUEST_TYPES, CONTAINER_SLOT_TYPES, PLAYER_ABILITIES, TEXT_PROCESSING_ORIGINS))
            .registerPacket(ClientboundCloseFormPacket::new, ClientboundCloseFormSerializer_v686.INSTANCE, 310, PacketRecipient.CLIENT)
            .build();
}