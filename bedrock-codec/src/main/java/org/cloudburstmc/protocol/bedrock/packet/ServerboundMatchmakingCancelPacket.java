package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.common.PacketSignal;

/**
 * Requests cancellation of the sending player's matchmaking ticket.
 * @since v2225
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class ServerboundMatchmakingCancelPacket implements BedrockPacket {

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.SERVERBOUND_MATCHMAKING_CANCEL;
    }

    @Override
    public ServerboundMatchmakingCancelPacket clone() {
        try {
            return (ServerboundMatchmakingCancelPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
