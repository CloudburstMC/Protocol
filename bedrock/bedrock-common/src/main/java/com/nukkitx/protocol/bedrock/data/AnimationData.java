package com.nukkitx.protocol.bedrock.data;

import lombok.Value;

@Value
public class AnimationData {
    private final ImageData image;
    private final int type;
    private final float frames;
}
