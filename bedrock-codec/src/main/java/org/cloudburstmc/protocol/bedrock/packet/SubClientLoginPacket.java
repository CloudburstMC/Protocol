package org.cloudburstmc.protocol.bedrock.packet;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.bedrock.data.auth.AuthType;
import org.cloudburstmc.protocol.common.PacketSignal;

import java.util.List;

@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class SubClientLoginPacket implements BedrockPacket {
    /**
     * @since v818
     */
    private AuthType authType;
    /**
     * The client's certificate chain signed by Minecraft's authentication server or the client itself.
     */
    private final List<String> certificateChain = new ObjectArrayList<>();
    /**
     * The JWT payload signed by Minecraft's authentication server.
     * Assuming this has a valid signature, it can be trusted to contain the player's identity and other information.
     *
     * @since v818
     */
    private String token;
    /**
     * The JWT payload signed by the client.
     * The client can modify this, so it should not be trusted.
     */
    private String clientJwt;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.SUB_CLIENT_LOGIN;
    }

    @Override
    public SubClientLoginPacket clone() {
        try {
            return (SubClientLoginPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}

