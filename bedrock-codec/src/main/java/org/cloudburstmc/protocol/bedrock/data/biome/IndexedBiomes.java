package org.cloudburstmc.protocol.bedrock.data.biome;

import it.unimi.dsi.fastutil.ints.IntObjectPair;
import org.cloudburstmc.protocol.common.util.index.Indexable;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

@SuppressWarnings("rawtypes")
public final class IndexedBiomes implements Indexable<Map<String, BiomeDefinitionData>> {

    private final List<IntObjectPair<BiomeDefinitionData>> biomes;
    private final List<String> values;
    private Map<String, BiomeDefinitionData> cached;

    public IndexedBiomes(List<IntObjectPair<BiomeDefinitionData>> biomes, List<String> values) {
        this.biomes = biomes;
        this.values = values;
    }

    @Override
    public Map<String, BiomeDefinitionData> get() {
        if (cached != null) {
            return cached;
        }
        Map<String, BiomeDefinitionData> map = new LinkedHashMap<>();
        for (IntObjectPair<BiomeDefinitionData> pair : biomes) {
            map.put(values.get(pair.firstInt()), pair.second());
        }
        return cached = map;
    }
}
