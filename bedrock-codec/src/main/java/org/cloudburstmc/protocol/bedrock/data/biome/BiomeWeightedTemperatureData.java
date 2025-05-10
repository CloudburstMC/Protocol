package org.cloudburstmc.protocol.bedrock.data.biome;

import lombok.Value;

@Value
public class BiomeWeightedTemperatureData {

    BiomeTemperatureCategory temperature;
    long weight;
}
