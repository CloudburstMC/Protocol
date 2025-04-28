package org.cloudburstmc.protocol.bedrock.data.biome;

import lombok.Value;

import java.util.List;

@Value
public class BiomeConditionalTransformationData {
    List<BiomeWeightedData> weightedBiomes;
    int conditionJson;
    long minPassingNeighbors;
}
