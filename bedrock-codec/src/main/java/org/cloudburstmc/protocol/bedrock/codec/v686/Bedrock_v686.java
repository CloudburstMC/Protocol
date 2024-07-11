package org.cloudburstmc.protocol.bedrock.codec.v686;

import org.cloudburstmc.protocol.bedrock.codec.BedrockCodec;
import org.cloudburstmc.protocol.bedrock.codec.v685.Bedrock_v685;
import org.cloudburstmc.protocol.bedrock.codec.v686.serializer.ClientboundCloseFormSerializer_v686;
import org.cloudburstmc.protocol.bedrock.data.PacketRecipient;
import org.cloudburstmc.protocol.bedrock.packet.ClientboundCloseFormPacket;

public class Bedrock_v686 extends Bedrock_v685 {

    public static final BedrockCodec CODEC = Bedrock_v685.CODEC.toBuilder()
            .protocolVersion(686)
            .minecraftVersion("1.21.2")
            .registerPacket(ClientboundCloseFormPacket::new, ClientboundCloseFormSerializer_v686.INSTANCE, 310, PacketRecipient.CLIENT)
            .build();
}