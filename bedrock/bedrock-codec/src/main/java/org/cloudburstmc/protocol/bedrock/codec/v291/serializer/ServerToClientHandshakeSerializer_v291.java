package org.cloudburstmc.protocol.bedrock.codec.v291.serializer;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.annotation.NoEncryption;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.packet.ServerToClientHandshakePacket;

@NoEncryption // This is sent in plain text to complete the Diffie Hellman key exchange.
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ServerToClientHandshakeSerializer_v291 implements BedrockPacketSerializer<ServerToClientHandshakePacket> {
    public static final ServerToClientHandshakeSerializer_v291 INSTANCE = new ServerToClientHandshakeSerializer_v291();


    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, ServerToClientHandshakePacket packet) {
        helper.writeString(buffer, packet.getJwt());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, ServerToClientHandshakePacket packet) {
        packet.setJwt(helper.readString(buffer));
    }
}
