package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.bedrock.annotation.NoEncryption;
import org.cloudburstmc.protocol.common.PacketSignal;


@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
@NoEncryption // This is sent in plain text to complete the Diffie Hellman key exchange.
public class ServerToClientHandshakePacket implements BedrockPacket {
    private String jwt;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.SERVER_TO_CLIENT_HANDSHAKE;
    }

    @Override
    public ServerToClientHandshakePacket clone() {
        try {
            return (ServerToClientHandshakePacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}

