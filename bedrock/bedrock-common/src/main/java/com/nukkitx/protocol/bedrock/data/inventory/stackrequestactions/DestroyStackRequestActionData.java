package com.nukkitx.protocol.bedrock.data.inventory.stackrequestactions;

import com.nukkitx.protocol.bedrock.data.inventory.StackRequestSlotInfoData;

/**
 * DestroyStackRequestActionData is sent by the client when it destroys an item in creative mode by moving it
 * back into the creative inventory.
 */
public class DestroyStackRequestActionData extends StackRequestActionData {
    // count is the count of items removed from the source slot
    byte count;

    // source is the source slot from which items are destroyed
    StackRequestSlotInfoData source;
}
