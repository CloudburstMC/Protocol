package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.cloudburstmc.protocol.common.PacketSignal;

import java.util.EnumSet;
import java.util.Set;

@Data
@EqualsAndHashCode(doNotUseGetters = true)
public class MoveEntityDeltaPacket implements BedrockPacket {
    private long runtimeEntityId;

    private final Set<Flag> flags = EnumSet.noneOf(Flag.class);

    private int deltaX;
    private int deltaY;
    private int deltaZ;

    private float x;
    private float y;
    private float z;

    private float pitch;
    private float yaw;
    private float headYaw;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.MOVE_ENTITY_DELTA;
    }

    public String toString() {
        return "MoveEntityDeltaPacket(runtimeEntityId=" + runtimeEntityId +
                ", flags=" + flags + ", delta=(" + deltaX + ", " + deltaY + ", " + deltaZ +
                "), position=(" + x + ", " + y + ", " + z +
                "), rotation=(" + pitch + ", " + yaw + ", " + headYaw + "))";
    }

    public enum Flag {
        HAS_X,
        HAS_Y,
        HAS_Z,
        HAS_PITCH,
        HAS_YAW,
        HAS_HEAD_YAW,
        ON_GROUND,
        TELEPORTING,
        FORCE_MOVE_LOCAL_ENTITY
    }

    @Override
    public MoveEntityDeltaPacket clone() {
        try {
            return (MoveEntityDeltaPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}

