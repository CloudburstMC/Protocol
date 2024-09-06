package org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.action;

import lombok.Value;

@Value
public class CraftLoomAction implements ItemStackRequestAction {
    String patternId;
    /**
     * @since v712
     */
    int timesCrafted;

    @Override
    public ItemStackRequestActionType getType() {
        return ItemStackRequestActionType.CRAFT_LOOM;
    }
}