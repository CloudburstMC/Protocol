package org.cloudburstmc.protocol.common.util;

import org.cloudburstmc.protocol.common.Definition;
import org.cloudburstmc.protocol.common.DefinitionRegistry;

public class DefinitionUtils {

    /**
     * Precondition to ensure a definition being sent is known to the registry.
     *
     * @param registry   registry to check
     * @param definition definition to check
     * @param <D>        definition type
     * @return definition
     * @throws IllegalArgumentException if the definition is not registered
     */
    public static <D extends Definition> D checkDefinition(DefinitionRegistry<D> registry, D definition) {
        if (!registry.isRegistered(definition)) {
            throw new IllegalArgumentException("Definition is not registered: " + definition);
        }
        return definition;
    }
}
