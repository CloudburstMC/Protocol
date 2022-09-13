package org.cloudburstmc.protocol.bedrock.data.defintions;

import lombok.Value;
import org.cloudburstmc.protocol.common.Definition;

@Value
public class ItemDefinition implements Definition {
    public static ItemDefinition EMPTY = new ItemDefinition("minecraft:empty", 0, false);
    public static ItemDefinition LEGACY_FIREWORK = new ItemDefinition("minecraft:fireworks_rocket", 401, false);

    String identifier;
    int runtimeId;
    boolean componentBased;
}
