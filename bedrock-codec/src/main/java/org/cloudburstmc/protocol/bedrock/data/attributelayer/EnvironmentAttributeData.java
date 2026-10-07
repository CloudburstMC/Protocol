package org.cloudburstmc.protocol.bedrock.data.attributelayer;

import lombok.Value;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.cloudburstmc.protocol.bedrock.data.camera.CameraEase;

@Value
public class EnvironmentAttributeData {

    String attributeName;

    @Nullable
    AttributeData from;
    AttributeData attribute;
    @Nullable
    AttributeData to;

    int currentTransitionTicks;
    int totalTransitionTicks;
    CameraEase easing;
    /**
     * @since v2225
     */
    String clockName;
    /**
     * @since v1001
     */
    int localTransitionTicks;
    /**
     * @since v1001
     */
    boolean noiseTransition;
    /**
     * @since v2225
     */
    String noiseName;
    /**
     * @since v2192
     */
    NoiseAlignment noiseAlignment;
}
