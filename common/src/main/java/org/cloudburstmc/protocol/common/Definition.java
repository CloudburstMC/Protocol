package org.cloudburstmc.protocol.common;

/**
 * A mapping for a protocol feature that uses runtime IDs to transmit data more efficiently.
 */
public interface Definition {

    /**
     * The runtime ID of this definition to be sent over the network.
     *
     * @return runtime id
     */
    int getRuntimeId();
}
