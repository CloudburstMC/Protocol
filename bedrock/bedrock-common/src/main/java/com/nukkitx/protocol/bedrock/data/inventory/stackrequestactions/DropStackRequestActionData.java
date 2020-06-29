package com.nukkitx.protocol.bedrock.data.inventory.stackrequestactions;

import com.nukkitx.protocol.bedrock.data.inventory.StackRequestSlotInfoData;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * DropStackRequestActionData is sent by the client when it drops an item out of the inventory when it has its
 * inventory opened. This action is not sent when a player drops an item out of the hotbar using the Q button
 * (or the equivalent on mobile). The InventoryTransaction packet is still used for that action, regardless of
 * whether the item stack network IDs are used or not.
 */
@AllArgsConstructor
@Getter
public class DropStackRequestActionData extends StackRequestActionData {
    // count is the count of items removed from the source slot
    byte count;

    // source is the source slot from which items are dropped to the ground
    StackRequestSlotInfoData source;

    // ?? Perhaps deals with order of items being dropped? Normally false.
    boolean randomly;
}
