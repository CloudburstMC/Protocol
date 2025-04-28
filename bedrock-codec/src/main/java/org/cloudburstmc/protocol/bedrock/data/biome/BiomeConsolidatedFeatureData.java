package org.cloudburstmc.protocol.bedrock.data.biome;

import lombok.Value;

@Value
public class BiomeConsolidatedFeatureData {
    BiomeScatterParamData scatter;
    int feature;
    int identifier;
    int pass;
    boolean internalUse;
}
