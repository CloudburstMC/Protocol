package org.cloudburstmc.protocol.bedrock.data.biome;

import lombok.Value;

@Value
public class BiomeElementData {
    float noiseFrequencyScale;
    float noiseLowerBound;
    float noiseUpperBound;
    int heightMinType;
    int heightMin;
    int heightMaxType;
    int heightMax;
    BiomeSurfaceMaterialData adjustedMaterials;
}
