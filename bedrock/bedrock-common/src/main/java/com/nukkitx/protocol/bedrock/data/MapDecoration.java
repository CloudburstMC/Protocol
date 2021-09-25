package com.nukkitx.protocol.bedrock.data;

import lombok.Value;

@Value
public class MapDecoration {
    int image;
    int rotation;
    int xOffset;
    int yOffset;
    String label;
    int color;
}
