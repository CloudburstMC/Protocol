package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.math.vector.Vector2f;
import org.cloudburstmc.protocol.common.PacketSignal;

@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class CameraAimAssistPacket implements BedrockPacket {
    private Vector2f viewAngle;
    private float distance;
    private TargetMode targetMode;
    private Action action;

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public BedrockPacketType getPacketType() {
        return BedrockPacketType.CAMERA_AIM_ASSIST;
    }

    @Override
    public CameraAimAssistPacket clone() {
        try {
            return (CameraAimAssistPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }

    public enum TargetMode {
        ANGLE,
        DISTANCE
    }

    public enum Action {
        SET,
        CLEAR
    }
}
