package org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request;

import lombok.Value;
import org.cloudburstmc.protocol.bedrock.data.inventory.ContainerSlotType;
import org.cloudburstmc.protocol.bedrock.data.inventory.FullContainerName;

/**
 * Holds information on a specific slot client-side.
 */
@Value
public class ItemStackRequestSlotData {
    /**
     * container the slot was in
     *
     * @deprecated since v712
     */
    ContainerSlotType container;

    /**
     * slot is the index of the slot within the container
     */
    int slot;

    /**
     * stackNetworkId is the unique stack ID that the client assumes to be present in this slot. The server
     * must check if these IDs match. If they do not match, servers should reject the stack request that the
     * action holding this info was in.
     */
    int stackNetworkId;

    /**
     * @since v712
     */
    FullContainerName containerName;
}
