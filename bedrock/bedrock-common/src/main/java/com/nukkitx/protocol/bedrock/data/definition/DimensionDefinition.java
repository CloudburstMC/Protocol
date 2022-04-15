package com.nukkitx.protocol.bedrock.data.definition;

import lombok.Value;

@Value
public class DimensionDefinition {
    String id;
    int maximumHeight;
    int minimumHeight;
    int generatorType;
}