package org.cloudburstmc.protocol.bedrock.data.biome;

import it.unimi.dsi.fastutil.ints.IntList;
import lombok.Value;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.awt.*;
import java.util.Optional;
import java.util.OptionalInt;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
@Value
public class BiomeDefinitionData {
    OptionalInt id;
    float temperature;
    float downfall;
    float redSporeDensity;
    float blueSporeDensity;
    float ashDensity;
    float whiteAshDensity;
    float depth;
    float scale;
    Color mapWaterColor;
    boolean rain;
    int @Nullable [] tags;
    @Nullable
    BiomeDefinitionChunkGenData chunkGenData;
}
