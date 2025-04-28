package org.cloudburstmc.protocol.bedrock.data.biome;

import lombok.Value;

@Value
public class BiomeMesaSurfaceData {

    long clayMaterial;
    long hardClayMaterial;
    boolean brycePillars;
    boolean hasForest;
}
