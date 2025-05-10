package org.cloudburstmc.protocol.bedrock.data.biome;

import lombok.Value;
import org.cloudburstmc.protocol.bedrock.data.definitions.BlockDefinition;

@Value
public class BiomeSurfaceMaterialData {

    BlockDefinition topBlock;
    BlockDefinition midBlock;
    BlockDefinition seaFloorBlock;
    BlockDefinition foundationBlock;
    BlockDefinition seaBlock;
    int seaFloorDepth;
}
