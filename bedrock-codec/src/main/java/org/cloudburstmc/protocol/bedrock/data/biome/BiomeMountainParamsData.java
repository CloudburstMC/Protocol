package org.cloudburstmc.protocol.bedrock.data.biome;

import lombok.Value;
import org.cloudburstmc.protocol.bedrock.data.definitions.BlockDefinition;

@Value
public class BiomeMountainParamsData {

    BlockDefinition steepBlock;
    boolean northSlopes;
    boolean southSlopes;
    boolean westSlopes;
    boolean eastSlopes;
    boolean topSlideEnabled;
}
