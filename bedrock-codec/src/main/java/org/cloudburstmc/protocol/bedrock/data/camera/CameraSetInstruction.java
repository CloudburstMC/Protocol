package org.cloudburstmc.protocol.bedrock.data.camera;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.cloudburstmc.math.vector.Vector2f;
import org.cloudburstmc.math.vector.Vector3f;
import org.cloudburstmc.protocol.common.NamedDefinition;
import org.cloudburstmc.protocol.common.util.OptionalBoolean;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CameraSetInstruction {
    private NamedDefinition preset;
    private EaseData ease;
    private Vector3f pos;
    private Vector2f rot;
    private Vector3f facing;
    /**
     * @since v712
     */
    private Vector2f viewOffset;
    private OptionalBoolean defaultPreset = OptionalBoolean.empty();

    @Data
    public static class EaseData {
        private final CameraEase easeType;
        private final float time;
    }
}
