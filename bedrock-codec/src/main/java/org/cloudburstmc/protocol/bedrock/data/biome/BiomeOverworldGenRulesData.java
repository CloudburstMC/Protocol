package org.cloudburstmc.protocol.bedrock.data.biome;

import lombok.Value;

import java.util.List;

@Value
public class BiomeOverworldGenRulesData {

    List<BiomeWeightedData> hillsTransformations;
    List<BiomeWeightedData> mutateTransformations;
    List<BiomeWeightedData> riverTransformations;
    List<BiomeWeightedData> shoreTransformations;
    List<BiomeConditionalTransformationData> preHillsEdgeTransformations;
    List<BiomeConditionalTransformationData> postShoreTransformations;
    List<BiomeWeightedTemperatureData> climateTransformations;
}
