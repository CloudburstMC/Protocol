package org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.action;

import lombok.Value;
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.ItemStackRequestSlotData;

/**
 * DestroyStackRequestActionData is sent by the client when it destroys an item in creative mode by moving it
 * back into the creative inventory.
 */
@Value
public class DestroyAction implements ItemStackRequestAction {
    int count;
    ItemStackRequestSlotData source;

    @Override
    public ItemStackRequestActionType getType() {
        return ItemStackRequestActionType.DESTROY;
    }
}
