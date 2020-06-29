package com.nukkitx.protocol.bedrock.data.inventory.stackrequestactions;

/**
 * AutoCraftRecipeStackRequestActionData is sent by the client similarly to the CraftRecipeStackRequestActionData. The
 * only difference is that the recipe is automatically created and crafted by shift clicking the recipe book.
 */
public class AutoCraftRecipeStackRequestActionData extends CraftRecipeStackRequestActionData {

    public AutoCraftRecipeStackRequestActionData(int recipeNetworkId) {
        super(recipeNetworkId);
    }
}
