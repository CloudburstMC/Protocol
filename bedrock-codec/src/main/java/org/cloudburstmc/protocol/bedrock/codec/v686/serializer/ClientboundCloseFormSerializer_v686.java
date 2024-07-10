package org.cloudburstmc.protocol.bedrock.codec.v686.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.packet.ClientboundCloseFormPacket;

public class ClientboundCloseFormSerializer_v686 implements BedrockPacketSerializer<ClientboundCloseFormPacket> {
    public static final ClientboundCloseFormSerializer_v686 INSTANCE = new ClientboundCloseFormSerializer_v686();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, ClientboundCloseFormPacket packet) {

    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, ClientboundCloseFormPacket packet) {

    }
}