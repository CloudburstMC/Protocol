package org.cloudburstmc.protocol.bedrock.data.biome;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.cloudburstmc.protocol.bedrock.data.ExpressionOp;
import org.cloudburstmc.protocol.bedrock.data.RandomDistributionType;
import org.cloudburstmc.protocol.common.util.index.Indexable;
import org.cloudburstmc.protocol.common.util.index.Unindexed;

@Value
@RequiredArgsConstructor(onConstructor_ = { @Deprecated })
public class BiomeCoordinateData {
    ExpressionOp minValueType;
    @Getter(AccessLevel.NONE)
    transient Indexable<String> minValue;
    ExpressionOp maxValueType;
    @Getter(AccessLevel.NONE)
    transient Indexable<String> maxValue;
    long gridOffset;
    long gridStepSize;
    RandomDistributionType distribution;

    @JsonCreator
    public BiomeCoordinateData(ExpressionOp minValueType, String minValue, ExpressionOp maxValueType, String maxValue,
                               long gridOffset, long gridStepSize, RandomDistributionType distribution) {
        this.minValueType = minValueType;
        this.minValue = new Unindexed<>(minValue);
        this.maxValueType = maxValueType;
        this.maxValue = new Unindexed<>(maxValue);
        this.gridOffset = gridOffset;
        this.gridStepSize = gridStepSize;
        this.distribution = distribution;
    }

    public String getMinValue() {
        return minValue.get();
    }

    public String getMaxValue() {
        return maxValue.get();
    }
}
