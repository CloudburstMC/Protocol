package org.cloudburstmc.protocol.bedrock.data;

import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode
public class MapDecoration {
    private final int image;
    private final int rotation;
    private final int xOffset;
    private final int yOffset;
    private final String label;
    private final int color;
}
