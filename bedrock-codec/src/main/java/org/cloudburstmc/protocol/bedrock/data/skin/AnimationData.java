package org.cloudburstmc.protocol.bedrock.data.skin;

import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor
public class AnimationData {
    private final ImageData image;
    private final AnimatedTextureType textureType;
    private final float frames;
    private final AnimationExpressionType expressionType;

    public AnimationData(ImageData image, AnimatedTextureType textureType, float frames) {
        this.image = image;
        this.textureType = textureType;
        this.frames = frames;
        this.expressionType = AnimationExpressionType.LINEAR;
    }
}
