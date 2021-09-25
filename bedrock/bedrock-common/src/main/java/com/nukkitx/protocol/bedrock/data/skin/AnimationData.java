package com.nukkitx.protocol.bedrock.data.skin;

import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor
public class AnimationData {
    ImageData image;
    AnimatedTextureType textureType;
    float frames;
    AnimationExpressionType expressionType;

    public AnimationData(ImageData image, AnimatedTextureType textureType, float frames) {
        this.image = image;
        this.textureType = textureType;
        this.frames = frames;
        this.expressionType = AnimationExpressionType.LINEAR;
    }
}
