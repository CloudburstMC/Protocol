package org.cloudburstmc.protocol.bedrock.data.attributelayer;

import lombok.Value;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.List;

@Value
public class AttributeLayerData {

    String layerName;
    /**
     * @since v1001
     * @deprecated since v2225
     */
    @Nullable
    String noiseName;
    int dimension;
    AttributeLayerSettings settings;
    List<EnvironmentAttributeData> attributes;
}
