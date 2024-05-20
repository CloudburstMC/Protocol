package org.cloudburstmc.protocol.bedrock.data.inventory.crafting.recipe;

import org.cloudburstmc.protocol.bedrock.data.inventory.ItemData;
import org.cloudburstmc.protocol.bedrock.data.inventory.crafting.RecipeUnlockingRequirement;
import org.cloudburstmc.protocol.bedrock.data.inventory.descriptor.ItemDescriptorWithCount;

import java.util.List;

/**
 * A recipe that can be used on a grid-like table.
 */
public interface CraftingRecipeData extends TaggedCraftingData, UniqueCraftingData, IdentifiableRecipeData {

    List<ItemDescriptorWithCount> getIngredients();

    List<ItemData> getResults();

    int getPriority();

    RecipeUnlockingRequirement getRequirement();
}
