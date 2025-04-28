package org.cloudburstmc.protocol.bedrock.data.biome;

import lombok.Value;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.List;

@Value
public class BiomeDefinitionChunkGenData {
    @Nullable
    BiomeClimateData climate;
    @Nullable
    List<BiomeConsolidatedFeatureData> consolidatedFeatures;
    @Nullable
    BiomeMountainParamsData mountainParams;
    @Nullable
    BiomeSurfaceMaterialAdjustmentData surfaceMaterialAdjustment;
    @Nullable
    BiomeSurfaceMaterialData surfaceMaterial;
    boolean hasSwampSurface;
    boolean hasFrozenOceanSurface;
    boolean hasTheEndSurface;
    @Nullable
    BiomeMesaSurfaceData mesaSurface;
    @Nullable
    BiomeCappedSurfaceData cappedSurface;
    @Nullable
    BiomeOverworldGenRulesData overworldGenRules;
    @Nullable
    BiomeMultinoiseGenRulesData multinoiseGenRules;
    @Nullable
    BiomeLegacyWorldGenRulesData legacyWorldGenRules;
}
