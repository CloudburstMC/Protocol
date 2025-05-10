package org.cloudburstmc.protocol.bedrock.data.biome;

import lombok.Value;
import org.cloudburstmc.protocol.bedrock.data.definitions.BlockDefinition;

@Value
public class BiomeMesaSurfaceData {

    BlockDefinition clayMaterial;
    BlockDefinition hardClayMaterial;
    boolean brycePillars;
    boolean hasForest;
}
