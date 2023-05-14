package org.cloudburstmc.protocol.bedrock.data.definitions;

import lombok.Value;

@Value
public class DimensionDefinition {
    String id;
    int maximumHeight;
    int minimumHeight;
    int generatorType;
}
