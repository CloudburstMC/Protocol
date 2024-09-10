package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.bedrock.data.CameraShakeAction;
import org.cloudburstmc.protocol.bedrock.data.CameraShakeType;
import org.cloudburstmc.protocol.common.PacketSignal;

/**
 * Causes the client's camera view to shake with a specified intensity and duration.
 * <p>
 * No known uses yet.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class CameraShakePacket implements BedrockPacket {

    /**
     * Intensity to shake the player's camera view.
     *
     * @param intensity shake intensity
     * @return shake intensity
     */
    private float intensity;

    /**
     * Amount of time to shake the player's camera.
     *
     * @param duration seconds to shake
     * @return seconds to shake
     */
    private float duration;

    private CameraShakeType shakeType;

    /**
     * @since v428
     */
    private CameraShakeAction shakeAction;

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public BedrockPacketType getPacketType() {
        return BedrockPacketType.CAMERA_SHAKE;
    }

    @Override
    public CameraShakePacket clone() {
        try {
            return (CameraShakePacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}

