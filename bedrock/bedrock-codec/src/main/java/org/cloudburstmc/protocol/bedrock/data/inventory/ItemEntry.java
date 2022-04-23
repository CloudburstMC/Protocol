package org.cloudburstmc.protocol.bedrock.data.inventory;

import lombok.NonNull;
import lombok.Value;

/**
 * ItemEntry holds information on what item stack should be present in a specific slot.
 */
@Value
public class ItemEntry {
    byte slot;
    byte hotbarSlot;
    byte count;

    /**
     * stackNetworkID is the network ID of the new stack at a specific slot.
     */
    int stackNetworkId;

    /**
     * Holds the final custom name of a renamed item, if relevant.
     * @since v422
     */
    @NonNull String customName;

    /**
     * @since v428
     */
    int durabilityCorrection;
}