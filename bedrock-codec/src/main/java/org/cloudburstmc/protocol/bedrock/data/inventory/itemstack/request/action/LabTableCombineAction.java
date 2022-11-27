package org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.action;

import lombok.Value;

/**
 * LabTableCombineStackRequestActionData is sent by the client when it uses a lab table to combine item stacks.
 */
@Value
public class LabTableCombineAction implements ItemStackRequestAction {

    @Override
    public ItemStackRequestActionType getType() {
        return ItemStackRequestActionType.LAB_TABLE_COMBINE;
    }
}
