package org.cloudburstmc.protocol.bedrock.data.biome;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.cloudburstmc.protocol.common.util.index.Indexable;
import org.cloudburstmc.protocol.common.util.index.Unindexed;

@Value
@RequiredArgsConstructor(onConstructor_ = { @Deprecated })
public class BiomeConsolidatedFeatureData {
    BiomeScatterParamData scatter;
    @Getter(AccessLevel.NONE)
    transient Indexable<String> feature;
    @Getter(AccessLevel.NONE)
    transient Indexable<String> identifier;
    @Getter(AccessLevel.NONE)
    transient Indexable<String> pass;
    boolean internalUse;

    @JsonCreator
    public BiomeConsolidatedFeatureData(BiomeScatterParamData scatter, String feature, String identifier, String pass,
            boolean internalUse) {
        this.scatter = scatter;
        this.feature = new Unindexed<>(feature);
        this.identifier = new Unindexed<>(identifier);
        this.pass = new Unindexed<>(pass);
        this.internalUse = internalUse;
    }

    public String getFeature() {
        return feature.get();
    }

    public String getIdentifier() {
        return identifier.get();
    }

    public String getPass() {
        return pass.get();
    }
}
