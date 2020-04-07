package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.annotation.NoEncryption;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(doNotUseGetters = true, callSuper = false)
@NoEncryption // This is sent in plain text to complete the Diffie Hellman key exchange.
public class ServerToClientHandshakePacket extends BedrockPacket {
    private String jwt;

    @Override
    public final boolean handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.SERVER_TO_CLIENT_HANDSHAKE;
    }
}
