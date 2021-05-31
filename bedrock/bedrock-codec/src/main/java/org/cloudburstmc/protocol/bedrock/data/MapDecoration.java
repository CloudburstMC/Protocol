package org.cloudburstmc.protocol.bedrock.data;

import lombok.Value;

@Value
public class MapDecoration {
    private final int image;
    private final int rotation;
    private final int xOffset;
    private final int yOffset;
    private final String label;
    private final int color;
}
