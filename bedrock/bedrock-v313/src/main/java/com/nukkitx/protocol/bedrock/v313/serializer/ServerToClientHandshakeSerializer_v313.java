package com.nukkitx.protocol.bedrock.v313.serializer;

import com.nukkitx.protocol.bedrock.annotation.NoEncryption;
import com.nukkitx.protocol.bedrock.packet.ServerToClientHandshakePacket;
import com.nukkitx.protocol.bedrock.v313.BedrockUtils;
import com.nukkitx.protocol.serializer.PacketSerializer;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoEncryption // This is sent in plain text to complete the Diffie Hellman key exchange.
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ServerToClientHandshakeSerializer_v313 implements PacketSerializer<ServerToClientHandshakePacket> {
    public static final ServerToClientHandshakeSerializer_v313 INSTANCE = new ServerToClientHandshakeSerializer_v313();


    @Override
    public void serialize(ByteBuf buffer, ServerToClientHandshakePacket packet) {
        BedrockUtils.writeString(buffer, packet.getJwt());
    }

    @Override
    public void deserialize(ByteBuf buffer, ServerToClientHandshakePacket packet) {
        packet.setJwt(BedrockUtils.readString(buffer));
    }
}
