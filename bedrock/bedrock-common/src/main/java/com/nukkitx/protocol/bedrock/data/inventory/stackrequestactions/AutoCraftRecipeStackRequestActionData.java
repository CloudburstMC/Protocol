package com.nukkitx.protocol.bedrock.data.inventory.stackrequestactions;

import com.nukkitx.protocol.bedrock.data.inventory.descriptor.ItemDescriptorWithCount;
import lombok.Value;

import java.util.List;

/**
 * AutoCraftRecipeStackRequestActionData is sent by the client similarly to the CraftRecipeStackRequestActionData. The
 * only difference is that the recipe is automatically created and crafted by shift clicking the recipe book.
 */
@Value
public class AutoCraftRecipeStackRequestActionData implements RecipeStackRequestActionData {
    int recipeNetworkId;
    /**
     * @since v448
     */
    int timesCrafted;
    List<ItemDescriptorWithCount> ingredients;

    @Override
    public StackRequestActionType getType() {
        return StackRequestActionType.CRAFT_RECIPE_AUTO;
    }
}
