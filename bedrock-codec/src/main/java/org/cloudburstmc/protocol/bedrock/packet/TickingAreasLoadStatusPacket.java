package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.common.PacketSignal;

/**
 * Client bound packet to indicate whether the server has preloaded the ticking areas.
 *
 * @since v503
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class TickingAreasLoadStatusPacket implements BedrockPacket {
    boolean waitingForPreload;

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public BedrockPacketType getPacketType() {
        return BedrockPacketType.TICKING_AREAS_LOAD_STATUS;
    }

    @Override
    public TickingAreasLoadStatusPacket clone() {
        try {
            return (TickingAreasLoadStatusPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}

