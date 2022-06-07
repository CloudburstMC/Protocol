package org.cloudburstmc.protocol.bedrock.data.defintions;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import org.cloudburstmc.protocol.common.Definition;
import org.cloudburstmc.protocol.common.DefinitionRegistry;

import java.util.HashMap;
import java.util.Map;

import static org.cloudburstmc.protocol.common.util.Preconditions.checkArgument;
import static org.cloudburstmc.protocol.common.util.Preconditions.checkNotNull;

public class SimpleDefinitionRegistry<D extends Definition> implements DefinitionRegistry<D> {

    private final Int2ObjectMap<D> runtimeMap;
    private final Map<String, D> identifierMap;

    private SimpleDefinitionRegistry(Int2ObjectMap<D> runtimeMap, Map<String, D> identifierMap) {
        this.runtimeMap = runtimeMap;
        this.identifierMap = identifierMap;
    }

    public static <D extends Definition> Builder<D> builder() {
        return new Builder<>();
    }

    @Override
    public D getDefinition(String identifier) {
        return this.identifierMap.get(identifier);
    }

    @Override
    public D getDefinition(int runtimeId) {
        return this.runtimeMap.get(runtimeId);
    }

    @Override
    public Builder<D> toBuilder() {
        Builder<D> builder = new Builder<>();
        builder.addAll(this.runtimeMap.values());
        return builder;
    }

    public static class Builder<D extends Definition> implements DefinitionRegistry.Builder<D> {
        private final Int2ObjectMap<D> runtimeMap = new Int2ObjectOpenHashMap<>();
        private final Map<String, D> identifierMap = new HashMap<>();

        @Override
        public DefinitionRegistry.Builder<D> add(D definition) {
            checkNotNull(definition, "definition");
            checkArgument(!this.identifierMap.containsKey(definition.getPersistentIdentifier()),
                    "Identifier is already registered: " + definition.getPersistentIdentifier());
            checkArgument(!this.runtimeMap.containsKey(definition.getRuntimeId()),
                    "Runtime ID is already registered: " + definition.getRuntimeId() );
            this.runtimeMap.put(definition.getRuntimeId(), definition);
            this.identifierMap.put(definition.getPersistentIdentifier(), definition);

            return this;
        }

        @Override
        public DefinitionRegistry.Builder<D> remove(D definition) {
            checkNotNull(definition, "definition");
            checkArgument(this.identifierMap.containsKey(definition.getPersistentIdentifier()),
                    "Identifier is mot registered: " + definition.getPersistentIdentifier());
            checkArgument(this.runtimeMap.containsKey(definition.getRuntimeId()),
                    "Runtime ID is not registered: " + definition.getRuntimeId());
            this.runtimeMap.remove(definition.getRuntimeId());
            this.identifierMap.remove(definition.getPersistentIdentifier());

            return this;
        }

        @Override
        public SimpleDefinitionRegistry<D> build() {
            return new SimpleDefinitionRegistry<>(this.runtimeMap, this.identifierMap);
        }
    }
}
