package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.common.PacketSignal;

@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class AnimatePacket implements BedrockPacket {
    private float rowingTime;
    private Action action;
    private long runtimeEntityId;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.ANIMATE;
    }

    public enum Action {
        NO_ACTION,
        SWING_ARM,
        WAKE_UP,
        CRITICAL_HIT,
        MAGIC_CRITICAL_HIT,
        ROW_RIGHT,
        ROW_LEFT,
    }

    @Override
    public AnimatePacket clone() {
        try {
            return (AnimatePacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}

