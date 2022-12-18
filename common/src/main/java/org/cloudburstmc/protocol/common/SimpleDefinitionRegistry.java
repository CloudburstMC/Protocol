package org.cloudburstmc.protocol.common;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static org.cloudburstmc.protocol.common.util.Preconditions.checkArgument;
import static org.cloudburstmc.protocol.common.util.Preconditions.checkNotNull;

public class SimpleDefinitionRegistry<D extends Definition> implements DefinitionRegistry<D> {

    private final Int2ObjectMap<D> runtimeMap;

    private SimpleDefinitionRegistry(Int2ObjectMap<D> runtimeMap, Map<String, D> identifierMap) {
        this.runtimeMap = runtimeMap;
    }

    public static <D extends Definition> Builder<D> builder() {
        return new Builder<>();
    }

    @Override
    public D getDefinition(int runtimeId) {
        return this.runtimeMap.get(runtimeId);
    }

    @Override
    public boolean isRegistered(D definition) {
        // A containsValue() check here would cause a linear search through the entire map, so we do this instead.
        // Also, might as well do a reference check since there should only be one instance of each definition.
        return runtimeMap.get(definition.getRuntimeId()) == definition;
    }

    public Builder<D> toBuilder() {
        return new Builder<D>()
                .addAll(this.runtimeMap.values());
    }

    public static class Builder<D extends Definition> {
        private final Int2ObjectMap<D> runtimeMap = new Int2ObjectOpenHashMap<>();
        private final Map<String, D> identifierMap = new HashMap<>();

        public Builder<D> addAll(Collection<? extends D> definitions) {
            for (D definition : definitions) {
                this.add(definition);
            }
            return this;
        }

        public Builder<D> add(D definition) {
            checkNotNull(definition, "definition");
            checkArgument(!this.runtimeMap.containsKey(definition.getRuntimeId()),
                    "Runtime ID is already registered: " + definition.getRuntimeId());
            this.runtimeMap.put(definition.getRuntimeId(), definition);

            return this;
        }

        public Builder<D> remove(D definition) {
            checkNotNull(definition, "definition");
            checkArgument(this.runtimeMap.containsKey(definition.getRuntimeId()),
                    "Runtime ID is not registered: " + definition.getRuntimeId());
            this.runtimeMap.remove(definition.getRuntimeId());

            return this;
        }

        public SimpleDefinitionRegistry<D> build() {
            return new SimpleDefinitionRegistry<>(this.runtimeMap, this.identifierMap);
        }
    }
}
