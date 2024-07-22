package org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.action;

import lombok.Value;

@Value
public class CraftGrindstoneAction implements ItemStackRequestAction {
    int recipeNetworkId;
    /**
     * @since v712
     */
    int numberOfRequestedCrafts;
    int repairCost;

    @Override
    public ItemStackRequestActionType getType() {
        return ItemStackRequestActionType.CRAFT_REPAIR_AND_DISENCHANT;
    }
}