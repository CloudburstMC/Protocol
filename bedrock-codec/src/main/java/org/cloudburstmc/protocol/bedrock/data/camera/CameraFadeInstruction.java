package org.cloudburstmc.protocol.bedrock.data.camera;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CameraFadeInstruction {
    private TimeData timeData;
    private Color color;

    @Data
    public static class TimeData {
        private final float fadeInTime;
        private final float waitTime;
        private final float fadeOutTime;
    }
}
