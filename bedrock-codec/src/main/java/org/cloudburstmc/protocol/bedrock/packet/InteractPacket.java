package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.math.vector.Vector3f;
import org.cloudburstmc.protocol.common.PacketSignal;

@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class InteractPacket implements BedrockPacket {
    private Action action;
    private long runtimeEntityId;
    private Vector3f mousePosition;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.INTERACT;
    }

    public enum Action {
        NONE,
        INTERACT,
        DAMAGE,
        LEAVE_VEHICLE,
        MOUSEOVER,
        NPC_OPEN,
        OPEN_INVENTORY
    }

    @Override
    public InteractPacket clone() {
        try {
            return (InteractPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}

