package org.cloudburstmc.protocol.common;

public interface NamedDefinition extends Definition {
    /**
     * The identifier of this definition.
     *
     * @return identifier
     */
    String getIdentifier();
}
