package com.nukkitx.protocol.bedrock.data.inventory;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import javax.annotation.concurrent.Immutable;

/**
 * Represents a unique instance of an item stack. These instances carry a specific network ID that is persistent for the stack.
 */

@Value
@Immutable
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ItemDataInstance {
    // networkId is the network ID of the item stack. If the stack is empty, 0 is always written for this
    // field. If not, the field should be set to 1 if the server authoritative inventories are disabled in the
    // StartGame packet, or to a unique stack ID if it is enabled.
    private final int networkId;

    // item is the actual itemStack
    private final ItemData item;

    public static ItemDataInstance of(int networkId, ItemData itemData) {
        return new ItemDataInstance(networkId, itemData);
    }
}
