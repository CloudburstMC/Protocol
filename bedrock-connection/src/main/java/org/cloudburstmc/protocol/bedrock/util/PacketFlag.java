package org.cloudburstmc.protocol.bedrock.util;

public interface PacketFlag {

    /**
     * Whether this flag can be passed from packet wrapper to batch wrapper.
     * @return true if the flag can be passed
     */
    default boolean canInherit() {
        return true;
    }
}
