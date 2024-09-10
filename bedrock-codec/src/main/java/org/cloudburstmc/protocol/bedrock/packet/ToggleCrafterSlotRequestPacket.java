package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.math.vector.Vector3i;
import org.cloudburstmc.protocol.common.PacketSignal;

@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class ToggleCrafterSlotRequestPacket implements BedrockPacket {
    private Vector3i blockPosition;
    private byte slot;
    private boolean disabled;

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public BedrockPacketType getPacketType() {
        return BedrockPacketType.TOGGLE_CRAFTER_SLOT_REQUEST;
    }

    @Override
    public ToggleCrafterSlotRequestPacket clone() {
        try {
            return (ToggleCrafterSlotRequestPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}

