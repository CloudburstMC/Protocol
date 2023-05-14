package org.cloudburstmc.protocol.bedrock.data.definitions;

import lombok.Value;
import lombok.experimental.NonFinal;

@Value
@NonFinal
public class SimpleItemDefinition implements ItemDefinition {
    String identifier;
    int runtimeId;
    boolean componentBased;
}
