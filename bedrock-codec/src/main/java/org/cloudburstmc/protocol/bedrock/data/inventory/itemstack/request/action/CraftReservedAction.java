package org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.action;

import lombok.Value;

@Value
public class CraftReservedAction implements ItemStackRequestAction {

    String reservedId;
    int numCrafts;

    @Override
    public ItemStackRequestActionType getType() {
        return ItemStackRequestActionType.RESERVED;
    }
}
