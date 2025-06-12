package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.bedrock.data.auth.AuthPayload;
import org.cloudburstmc.protocol.common.PacketSignal;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(doNotUseGetters = true, callSuper = false)
@ToString(doNotUseGetters = true)
public class LoginPacket implements BedrockPacket {
    private int protocolVersion;
    /**
     * The JWT payload signed by Minecraft's authentication server.
     * Assuming this is a valid signature, it can be trusted to contain the player's identity and other information.
     */
    private AuthPayload authPayload;
    /**
     * The JWT payload signed by the client.
     * The client can modify this, so it should not be trusted.
     */
    private String clientJwt;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public BedrockPacketType getPacketType() {
        return BedrockPacketType.LOGIN;
    }

    @Override
    public LoginPacket clone() {
        try {
            return (LoginPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}

