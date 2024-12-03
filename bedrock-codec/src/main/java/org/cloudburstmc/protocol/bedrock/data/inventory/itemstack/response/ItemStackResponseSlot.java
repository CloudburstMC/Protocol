package org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.response;

import lombok.*;

/**
 * ItemEntry holds information on what item stack should be present in a specific slot.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemStackResponseSlot {
    private int slot;
    private int hotbarSlot;
    private int count;

    /**
     * stackNetworkID is the network ID of the new stack at a specific slot.
     */
    private int stackNetworkId;

    /**
     * Holds the final custom name of a renamed item, if relevant.
     *
     * @since v422
     */
    private @NonNull String customName;

    /**
     * @since v428
     */
    private int durabilityCorrection;
    /**
     * @since v766
     */
    private String filteredCustomName = "";
}