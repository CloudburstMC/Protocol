package org.cloudburstmc.protocol.common;

public interface Definition {

    /**
     * Provides a runtime id (or entry id) of this definition instance.
     * It is used to map against persistent identifier.
     * @return runtime id
     */
    int getRuntimeId();

    /**
     * Provides a definition identifier. Some implementations can have common identifier,
     * but should always have different persistent identifier.
     * @return identifier
     */
    String getIdentifier();

    /**
     * Provides unique identifier per definition permutation.
     * If two objects have same identifier, they should be considered as equal.
     * @return persistent identifier
     */
    default String getPersistentIdentifier() {
        return this.getIdentifier();
    }

    default boolean equalsLazy(Definition definition) {
        return this.getPersistentIdentifier().equals(definition.getPersistentIdentifier());
    }
}
