package org.cloudburstmc.protocol.bedrock.data.biome;

import lombok.Value;

@Value
public class BiomeMountainParamsData {

    int steepBlock;
    boolean northSlopes;
    boolean southSlopes;
    boolean westSlopes;
    boolean eastSlopes;
    boolean topSlideEnabled;
}
