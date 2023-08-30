package org.cloudburstmc.protocol.bedrock.data.definitions;

import org.cloudburstmc.protocol.common.NamedDefinition;

public interface ItemDefinition extends NamedDefinition {
    ItemDefinition AIR = new SimpleItemDefinition("minecraft:air", 0, false);
    ItemDefinition LEGACY_FIREWORK = new SimpleItemDefinition("minecraft:fireworks_rocket", 401, false);

    boolean isComponentBased();
}
