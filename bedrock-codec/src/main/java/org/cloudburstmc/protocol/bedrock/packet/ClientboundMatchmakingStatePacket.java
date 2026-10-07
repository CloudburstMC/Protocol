package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.cloudburstmc.protocol.common.PacketSignal;

/**
 * Informs the client when its matchmaking state changes.
 * @since v2225
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class ClientboundMatchmakingStatePacket implements BedrockPacket {

    private MatchmakingState state;
    private String destinationName;
    @Nullable
    private MatchmakingStateOptions options;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.CLIENTBOUND_MATCHMAKING_STATE;
    }

    @Override
    public ClientboundMatchmakingStatePacket clone() {
        try {
            return (ClientboundMatchmakingStatePacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }

    public enum MatchmakingState {
        IDLE,
        MATCHMAKING,
        MATCH_FOUND,
        CANCELED,
        PLAYER_LEFT_PARTY,
        PLAYER_LEFT_SERVER,
        SERVER_SHUTDOWN,
        TIMED_OUT,
        REQUEUE_AS_PARTY,
    }

    @Value
    public static class MatchmakingStateOptions {
        String triggeringPlayerName;
        boolean triggeredByLocalPlayer;
    }
}
