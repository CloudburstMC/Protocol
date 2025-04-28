package org.cloudburstmc.protocol.bedrock.data.biome;

import lombok.Value;

import java.util.List;

@Value
public class BiomeSurfaceMaterialAdjustmentData {
    List<BiomeElementData> biomeElements;
}
