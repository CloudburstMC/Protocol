package org.cloudburstmc.protocol.bedrock.data.biome;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.cloudburstmc.protocol.common.util.index.Indexable;
import org.cloudburstmc.protocol.common.util.index.Unindexed;

import java.util.List;

@Value
@RequiredArgsConstructor(onConstructor_ = { @Deprecated })
public class BiomeConditionalTransformationData {
    List<BiomeWeightedData> weightedBiomes;
    @Getter(AccessLevel.NONE)
    transient Indexable<String> conditionJson;
    long minPassingNeighbors;

    @JsonCreator
    public BiomeConditionalTransformationData(List<BiomeWeightedData> weightedBiomes, String conditionJson,
                                              long minPassingNeighbors) {
        this.weightedBiomes = weightedBiomes;
        this.conditionJson = new Unindexed<>(conditionJson);
        this.minPassingNeighbors = minPassingNeighbors;
    }

    public String getConditionJson() {
        return conditionJson.get();
    }
}
