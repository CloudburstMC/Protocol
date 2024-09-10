package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.common.PacketSignal;

@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class MobEffectPacket implements BedrockPacket {
    private long runtimeEntityId;
    private Event event;
    private int effectId;
    private int amplifier;
    private boolean particles;
    private int duration;
    /**
     * @since v662
     */
    private long tick;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.MOB_EFFECT;
    }

    public enum Event {
        NONE,
        ADD,
        MODIFY,
        REMOVE,
    }

    @Override
    public MobEffectPacket clone() {
        try {
            return (MobEffectPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}

