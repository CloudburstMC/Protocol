package com.nukkitx.protocol.bedrock.data.inventory.stackrequestactions;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * CraftRecipeStackRequestActionData is sent by the client the moment it begins crafting an item. This is the
 * first action sent, before the Consume and Create item stack request actions.
 * This action is also sent when an item is enchanted. Enchanting should be treated mostly the same way as
 * crafting, where the old item is consumed.
 */
@Getter
@AllArgsConstructor
public class CraftRecipeStackRequestActionData extends StackRequestActionData {
    // recipeNetworkId is the network ID of the recipe that is about to be crafted. This network ID matches
    // one of the recipes sent in the CraftingData packet, where each of the recipes have a RecipeNetworkID as
    // of 1.16.
    int recipeNetworkId;
}
