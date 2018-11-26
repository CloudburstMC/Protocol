package com.nukkitx.protocol.bedrock.v291.packet;

import com.nukkitx.protocol.bedrock.annotation.NoEncryption;
import com.nukkitx.protocol.bedrock.packet.ServerToClientHandshakePacket;
import com.nukkitx.protocol.bedrock.v291.BedrockUtils;
import io.netty.buffer.ByteBuf;

@NoEncryption // This is sent in plain text to complete the Diffie Hellman key exchange.
public class ServerToClientHandshakePacket_v291 extends ServerToClientHandshakePacket {

    @Override
    public void encode(ByteBuf buffer) {
        BedrockUtils.writeString(buffer, jwt);
    }

    @Override
    public void decode(ByteBuf buffer) {
        jwt = BedrockUtils.readString(buffer);
    }
}
