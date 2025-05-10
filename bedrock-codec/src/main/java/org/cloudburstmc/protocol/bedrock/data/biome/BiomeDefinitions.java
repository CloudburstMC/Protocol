package org.cloudburstmc.protocol.bedrock.data.biome;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.cloudburstmc.protocol.common.util.index.Indexable;

import java.util.Map;

@Value
@RequiredArgsConstructor(onConstructor_ = { @Deprecated })
public class BiomeDefinitions {

    @Getter(AccessLevel.NONE)
    transient Indexable<Map<String, BiomeDefinitionData>> definitions;

    @JsonCreator
    public BiomeDefinitions(Map<String, BiomeDefinitionData> definitions) {
        this.definitions = new UnindexedBiomes(definitions);
    }

    public Map<String, BiomeDefinitionData> getDefinitions() {
        return definitions.get();
    }
}
