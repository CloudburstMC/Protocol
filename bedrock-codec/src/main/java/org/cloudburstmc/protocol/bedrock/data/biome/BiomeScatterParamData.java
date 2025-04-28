package org.cloudburstmc.protocol.bedrock.data.biome;

import lombok.Value;

import java.util.List;

@Value
public class BiomeScatterParamData {

    List<BiomeCoordinateData> coordinates;
    int evalOrder;
    int chancePercentType;
    int chancePercent;
    int chanceNumerator;
    int changeDenominator;
    int iterationsType;
    int iterations;
}
