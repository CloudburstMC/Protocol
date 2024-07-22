package org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.action;

import lombok.Value;

/**
 * CraftRecipeStackRequestActionData is sent by the client the moment it begins crafting an item. This is the
 * first action sent, before the Consume and Create item stack request actions.
 * This action is also sent when an item is enchanted. Enchanting should be treated mostly the same way as
 * crafting, where the old item is consumed.
 */
@Value
public class CraftRecipeAction implements RecipeItemStackRequestAction {
    int recipeNetworkId;
    /**
     * @since v712
     */
    int numberOfRequestedCrafts;

    @Override
    public ItemStackRequestActionType getType() {
        return ItemStackRequestActionType.CRAFT_RECIPE;
    }
}
