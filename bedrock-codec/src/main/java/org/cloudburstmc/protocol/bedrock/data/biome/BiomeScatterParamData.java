package org.cloudburstmc.protocol.bedrock.data.biome;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.cloudburstmc.protocol.bedrock.data.CoordinateEvaluationOrder;
import org.cloudburstmc.protocol.bedrock.data.ExpressionOp;
import org.cloudburstmc.protocol.common.util.index.Indexable;
import org.cloudburstmc.protocol.common.util.index.Unindexed;

import java.util.List;

@Value
@RequiredArgsConstructor(onConstructor_ = { @Deprecated })
public class BiomeScatterParamData {

    List<BiomeCoordinateData> coordinates;
    CoordinateEvaluationOrder evalOrder;
    ExpressionOp chancePercentType;
    @Getter(AccessLevel.NONE)
    transient Indexable<String> chancePercent;
    int chanceNumerator;
    int changeDenominator;
    ExpressionOp iterationsType;
    @Getter(AccessLevel.NONE)
    transient Indexable<String> iterations;

    @JsonCreator
    public BiomeScatterParamData(List<BiomeCoordinateData> coordinates, CoordinateEvaluationOrder evalOrder,
                                 ExpressionOp chancePercentType, String chancePercent, int chanceNumerator,
                                 int changeDenominator, ExpressionOp iterationsType, String iterations) {
        this.coordinates = coordinates;
        this.evalOrder = evalOrder;
        this.chancePercentType = chancePercentType;
        this.chancePercent = new Unindexed<>(chancePercent);
        this.chanceNumerator = chanceNumerator;
        this.changeDenominator = changeDenominator;
        this.iterationsType = iterationsType;
        this.iterations = new Unindexed<>(iterations);
    }

    public String getChancePercent() {
        return chancePercent.get();
    }

    public String getIterations() {
        return iterations.get();
    }
}
