package org.cloudburstmc.protocol.bedrock.data.biome;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.cloudburstmc.protocol.bedrock.data.ExpressionOp;
import org.cloudburstmc.protocol.common.util.index.Indexable;
import org.cloudburstmc.protocol.common.util.index.Unindexed;

@Value
@RequiredArgsConstructor(onConstructor_ = { @Deprecated })
public class BiomeElementData {
    float noiseFrequencyScale;
    float noiseLowerBound;
    float noiseUpperBound;
    ExpressionOp heightMinType;
    @Getter(AccessLevel.NONE)
    transient Indexable<String> heightMin;
    ExpressionOp heightMaxType;
    @Getter(AccessLevel.NONE)
    transient Indexable<String> heightMax;
    BiomeSurfaceMaterialData adjustedMaterials;

    @JsonCreator
    public BiomeElementData(float noiseFrequencyScale, float noiseLowerBound, float noiseUpperBound,
                            ExpressionOp heightMinType, String heightMin, ExpressionOp heightMaxType,
                            String heightMax, BiomeSurfaceMaterialData adjustedMaterials) {
        this.noiseFrequencyScale = noiseFrequencyScale;
        this.noiseLowerBound = noiseLowerBound;
        this.noiseUpperBound = noiseUpperBound;
        this.heightMinType = heightMinType;
        this.heightMin = new Unindexed<>(heightMin);
        this.heightMaxType = heightMaxType;
        this.heightMax = new Unindexed<>(heightMax);
        this.adjustedMaterials = adjustedMaterials;
    }

    public String getHeightMin() {
        return heightMin.get();
    }

    public String getHeightMax() {
        return heightMax.get();
    }
}
