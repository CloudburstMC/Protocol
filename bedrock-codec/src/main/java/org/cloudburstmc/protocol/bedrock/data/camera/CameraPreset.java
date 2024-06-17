package org.cloudburstmc.protocol.bedrock.data.camera;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.cloudburstmc.math.vector.Vector2f;
import org.cloudburstmc.math.vector.Vector3f;
import org.cloudburstmc.protocol.common.util.OptionalBoolean;

import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CameraPreset {
    private String identifier;
    private String parentPreset = "";
    // All the values below are optional, and will not be encoded if null is used
    private Vector3f pos;
    private Float yaw;
    private Float pitch;
    /**
     * @since v705
     */
    private Vector2f viewOffset;
    /**
     * @since v705
     */
    private Optional<Float> radius;
    private CameraAudioListener listener;
    private OptionalBoolean playEffect;
}
