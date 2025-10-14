package org.cloudburstmc.protocol.bedrock.data.camera;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.cloudburstmc.math.vector.Vector2f;
import org.cloudburstmc.math.vector.Vector3f;

import java.util.List;

@Data
@AllArgsConstructor
public class CameraSplineInstruction {

    private float totalTime;
    private CameraSplineType type;
    private List<Vector3f> curve;
    private List<Vector2f> progressKeyFrames;
    private List<SplineRotationOption> rotationOption;

    @Data
    @AllArgsConstructor
    public static class SplineRotationOption {

        private Vector3f keyFrameValues;
        private float keyFrameTimes;
    }
}
