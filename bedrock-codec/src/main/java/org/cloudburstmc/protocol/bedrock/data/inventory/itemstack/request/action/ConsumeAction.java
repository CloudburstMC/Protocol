package org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.action;

import lombok.Value;
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.ItemStackRequestSlotData;

/**
 * ConsumeStackRequestAction is sent by the client when it uses an item to craft another item. The original
 * item is 'consumed'.
 */
@Value
public class ConsumeAction implements ItemStackRequestAction {
    int count;
    ItemStackRequestSlotData source;

    @Override
    public ItemStackRequestActionType getType() {
        return ItemStackRequestActionType.CONSUME;
    }
}
