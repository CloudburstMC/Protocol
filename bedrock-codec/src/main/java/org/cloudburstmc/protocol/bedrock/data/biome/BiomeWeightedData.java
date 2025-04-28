package org.cloudburstmc.protocol.bedrock.data.biome;

import lombok.Value;

@Value
public class BiomeWeightedData {

    int biome;
    long weight;
}
