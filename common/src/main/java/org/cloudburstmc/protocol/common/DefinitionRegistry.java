package org.cloudburstmc.protocol.common;

/**
 * A basic registry for protocol definitions that can be expanded upon.
 *
 * @param <D>
 */
public interface DefinitionRegistry<D extends Definition> {

    D getDefinition(int runtimeId);

    boolean isRegistered(D definition);
}
