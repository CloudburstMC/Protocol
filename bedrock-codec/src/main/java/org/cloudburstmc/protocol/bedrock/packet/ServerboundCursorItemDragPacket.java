package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.common.PacketSignal;

/**
 * Sent from the client when a cursor item split drag starts or stops.
 * @since v2225
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class ServerboundCursorItemDragPacket implements BedrockPacket {

    private State state;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.SERVERBOUND_CURSOR_ITEM_DRAG;
    }

    @Override
    public ServerboundCursorItemDragPacket clone() {
        try {
            return (ServerboundCursorItemDragPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }

    public enum State {
        START,
        STOP
    }
}
