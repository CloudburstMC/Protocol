package org.cloudburstmc.protocol.bedrock.data.defintions;

import lombok.Value;
import lombok.experimental.NonFinal;
import org.cloudburstmc.protocol.common.Definition;

@Value
@NonFinal
public class ItemDefinition implements Definition {
    public static ItemDefinition AIR = new ItemDefinition("minecraft:air", 0, false);
    public static ItemDefinition LEGACY_FIREWORK = new ItemDefinition("minecraft:fireworks_rocket", 401, false);

    String identifier;
    int runtimeId;
    boolean componentBased;
}
