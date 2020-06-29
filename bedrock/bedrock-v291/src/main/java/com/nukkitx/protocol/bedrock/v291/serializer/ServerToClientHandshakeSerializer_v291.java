package com.nukkitx.protocol.bedrock.v291.serializer;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.annotation.NoEncryption;
import com.nukkitx.protocol.bedrock.packet.ServerToClientHandshakePacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoEncryption // This is sent in plain text to complete the Diffie Hellman key exchange.
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ServerToClientHandshakeSerializer_v291 implements BedrockPacketSerializer<ServerToClientHandshakePacket> {
    public static final ServerToClientHandshakeSerializer_v291 INSTANCE = new ServerToClientHandshakeSerializer_v291();


    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, ServerToClientHandshakePacket packet) {
        helper.writeString(buffer, packet.getJwt());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, ServerToClientHandshakePacket packet) {
        packet.setJwt(helper.readString(buffer));
    }
}
