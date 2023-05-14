package org.cloudburstmc.protocol.bedrock.data.definitions;

import org.cloudburstmc.protocol.common.Definition;

public interface ItemDefinition extends Definition {
    ItemDefinition AIR = new SimpleItemDefinition("minecraft:air", 0, false);
    ItemDefinition LEGACY_FIREWORK = new SimpleItemDefinition("minecraft:fireworks_rocket", 401, false);

    String getIdentifier();

    boolean isComponentBased();
}
