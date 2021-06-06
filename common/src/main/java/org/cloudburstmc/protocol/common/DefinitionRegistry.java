package org.cloudburstmc.protocol.common;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static org.cloudburstmc.protocol.common.util.Preconditions.checkArgument;
import static org.cloudburstmc.protocol.common.util.Preconditions.checkNotNull;

public final class DefinitionRegistry<D extends Definition> {
    private final Int2ObjectMap<D> runtimeMap;
    private final Map<String, D> identifierMap;

    private DefinitionRegistry(Int2ObjectMap<D> runtimeMap, Map<String, D> identifierMap) {
        this.runtimeMap = runtimeMap;
        this.identifierMap = identifierMap;
    }

    public D getDefinition(String identifier) {
        return this.identifierMap.get(identifier);
    }

    public D getDefinition(int runtimeId) {
        return this.runtimeMap.get(runtimeId);
    }

    public static <D extends Definition> Builder<D> builder() {
        return new Builder<>();
    }

    public static class Builder<D extends Definition> {
        private final Int2ObjectMap<D> runtimeMap = new Int2ObjectOpenHashMap<>();
        private final Map<String, D> identifierMap = new HashMap<>();

        public Builder<D> addAll(Collection<D> definitions) {
            for (D definition : definitions) {
                this.add(definition);
            }
            return this;
        }

        public Builder<D> add(D definition) {
            checkNotNull(definition, "definition");
            checkArgument(!this.identifierMap.containsKey(definition.getIdentifier()),
                    "Identifier is already registered");
            checkArgument(!this.runtimeMap.containsKey(definition.getRuntimeId()),
                    "Runtime ID is already registered");
            this.runtimeMap.put(definition.getRuntimeId(), definition);
            this.identifierMap.put(definition.getIdentifier(), definition);

            return this;
        }
    }
}
