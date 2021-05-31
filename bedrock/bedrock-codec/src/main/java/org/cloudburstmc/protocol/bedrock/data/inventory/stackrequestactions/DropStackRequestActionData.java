package org.cloudburstmc.protocol.bedrock.data.inventory.stackrequestactions;

import lombok.Value;
import org.cloudburstmc.protocol.bedrock.data.inventory.StackRequestSlotInfoData;

/**
 * DropStackRequestActionData is sent by the client when it drops an item out of the inventory when it has its
 * inventory opened. This action is not sent when a player drops an item out of the hotbar using the Q button
 * (or the equivalent on mobile). The InventoryTransaction packet is still used for that action, regardless of
 * whether the item stack network IDs are used or not.
 */
@Value
public class DropStackRequestActionData implements StackRequestActionData {
    byte count;
    StackRequestSlotInfoData source;
    boolean randomly; // ?? Perhaps deals with order of items being dropped? Normally false.

    @Override
    public StackRequestActionType getType() {
        return StackRequestActionType.DROP;
    }
}
