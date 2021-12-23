package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.data.CameraShakeAction;
import com.nukkitx.protocol.bedrock.data.CameraShakeType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Causes the client's camera view to shake with a specified intensity and duration.
 * <p>
 * No known uses yet.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true, callSuper = false)
public class CameraShakePacket extends BedrockPacket {

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
    public boolean handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public BedrockPacketType getPacketType() {
        return BedrockPacketType.CAMERA_SHAKE;
    }
}
