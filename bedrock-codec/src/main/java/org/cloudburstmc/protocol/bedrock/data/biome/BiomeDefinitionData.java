package org.cloudburstmc.protocol.bedrock.data.biome;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Value;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.cloudburstmc.protocol.common.util.index.Indexable;
import org.cloudburstmc.protocol.common.util.index.Unindexed;

import java.awt.Color;
import java.util.List;

@Value
public class BiomeDefinitionData {

    /**
     * The numeric id of this biome. This field is always {@code null} for vanilla biome, and is only set for custom biome (start at 30000).
     */
    @Nullable
    Integer id;
    float temperature;
    float downfall;
    float redSporeDensity;
    float blueSporeDensity;
    float ashDensity;
    float whiteAshDensity;
    float foliageSnow;
    float depth;
    float scale;
    Color mapWaterColor;
    boolean rain;
    @Nullable
    @Getter(AccessLevel.NONE)
    transient Indexable<List<String>> tags;
    @Nullable
    BiomeDefinitionChunkGenData chunkGenData;

    @JsonCreator
    public BiomeDefinitionData(@Nullable Integer id, float temperature, float downfall, float redSporeDensity, float blueSporeDensity, float ashDensity, float whiteAshDensity, float foliageSnow, float depth, float scale, Color mapWaterColor, boolean rain, @Nullable List<String> tags, @Nullable BiomeDefinitionChunkGenData chunkGenData) {
        this(id, temperature, downfall, redSporeDensity, blueSporeDensity, ashDensity, whiteAshDensity, foliageSnow, depth, scale, mapWaterColor, rain, tags == null ? null : new Unindexed<>(tags), chunkGenData);
    }

    public BiomeDefinitionData(@Nullable Integer id, float temperature, float downfall, float foliageSnow, float depth, float scale, Color mapWaterColor, boolean rain, @Nullable List<String> tags, @Nullable BiomeDefinitionChunkGenData chunkGenData) {
        this(id, temperature, downfall, 0, 0, 0, 0, foliageSnow, depth, scale, mapWaterColor, rain, tags == null ? null : new Unindexed<>(tags), chunkGenData);
    }

    public BiomeDefinitionData(@Nullable Integer id, float temperature, float downfall, float foliageSnow, float depth, float scale, Color mapWaterColor, boolean rain, @Nullable Indexable<List<String>> tags, @Nullable BiomeDefinitionChunkGenData chunkGenData) {
        this(id, temperature, downfall, 0, 0, 0, 0, foliageSnow, depth, scale, mapWaterColor, rain, tags, chunkGenData);
    }

    public BiomeDefinitionData(@Nullable Integer id, float temperature, float downfall, float redSporeDensity, float blueSporeDensity, float ashDensity, float whiteAshDensity, float depth, float scale, Color mapWaterColor, boolean rain, @Nullable Indexable<List<String>> tags, @Nullable BiomeDefinitionChunkGenData chunkGenData) {
        this(id, temperature, downfall, redSporeDensity, blueSporeDensity, ashDensity, whiteAshDensity, 0, depth, scale, mapWaterColor, rain, tags, chunkGenData);
    }

    private BiomeDefinitionData(@Nullable Integer id, float temperature, float downfall, float redSporeDensity, float blueSporeDensity, float ashDensity, float whiteAshDensity, float foliageSnow, float depth, float scale, Color mapWaterColor, boolean rain, @Nullable Indexable<List<String>> tags, @Nullable BiomeDefinitionChunkGenData chunkGenData) {
        this.id = id;
        this.temperature = temperature;
        this.downfall = downfall;
        this.redSporeDensity = redSporeDensity;
        this.blueSporeDensity = blueSporeDensity;
        this.ashDensity = ashDensity;
        this.whiteAshDensity = whiteAshDensity;
        this.foliageSnow = foliageSnow;
        this.depth = depth;
        this.scale = scale;
        this.mapWaterColor = mapWaterColor;
        this.rain = rain;
        this.tags = tags;
        this.chunkGenData = chunkGenData;
    }

    public @Nullable List<String> getTags() {
        if (tags == null) {
            return null;
        }
        return tags.get();
    }
}
