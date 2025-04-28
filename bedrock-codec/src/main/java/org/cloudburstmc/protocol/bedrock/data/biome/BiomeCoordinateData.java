package org.cloudburstmc.protocol.bedrock.data.biome;

import lombok.Value;

@Value
public class BiomeCoordinateData {
    int minValueType;
    int minValue;
    int maxValueType;
    int maxValue;
    long gridOffset;
    long gridStepSize;
    int distribution;
}
