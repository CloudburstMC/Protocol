package com.nukkitx.protocol.bedrock.data.inventory.stackrequestactions;

import lombok.Value;

/**
 * Called when renaming an item in an anvil or cartography table. Uses the filter strings present in the request.
 */
@Value
public class CraftRecipeOptionalStackRequestActionData implements StackRequestActionData {
    /**
     * For the cartography table, if a certain MULTI recipe is being called, this points to the network ID that was assigned.
     */
    int recipeNetworkId;
    /**
     * Most likely the index in the request's filter strings that this action is using
     */
    int filteredStringIndex;

    @Override
    public StackRequestActionType getType() {
        return StackRequestActionType.CRAFT_RECIPE_OPTIONAL;
    }
}
