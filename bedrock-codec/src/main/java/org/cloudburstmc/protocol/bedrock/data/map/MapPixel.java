package org.cloudburstmc.protocol.bedrock.data.map;

import lombok.Value;

@Value
public class MapPixel {
    /**
     * Colour value of pixel
     */
    int pixel;
    /**
     * Pixel index in map.
     */
    int index;
}
