package org.cloudburstmc.protocol.common;

import java.util.Collection;

public interface DefinitionRegistry<D extends Definition> {

    D getDefinition(String identifier);
    D getDefinition(int runtimeId);

    default D checkMappedDefinition(D definition) {
        D mappedDefinition = this.getDefinition(definition.getPersistentIdentifier());
        return mappedDefinition == null ? definition : mappedDefinition;
    }

    Builder<D> toBuilder();

    interface Builder<D extends Definition> {
        default Builder<D> addAll(Collection<D> definitions) {
            for (D definition : definitions) {
                this.add(definition);
            }
            return this;
        }

        Builder<D> add(D definition);
        Builder<D> remove(D definition);
        DefinitionRegistry<D> build();
    }
}
