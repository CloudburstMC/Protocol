package org.cloudburstmc.protocol.bedrock.data.inventory;

/**
 * Represents the status of a {@link ItemStackResponse}.
 */
public enum ResponseStatus {
    /**
     * The transaction request was valid.
     */
    OK,

    /**
     * The transaction request was invalid.
     */
    ERROR
}
