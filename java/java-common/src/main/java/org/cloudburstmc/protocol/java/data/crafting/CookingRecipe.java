package org.cloudburstmc.protocol.java.data.crafting;

import lombok.Builder;
import lombok.Getter;
import net.kyori.adventure.key.Key;
import org.cloudburstmc.protocol.java.data.inventory.ItemStack;

@Getter
public class CookingRecipe extends Recipe {
    private final String group;
    private final RecipeIngredient ingredient;
    private final ItemStack result;
    private final float experience;
    private final int cookingTime;

    @Builder
    protected CookingRecipe(RecipeType<?> type, Key identifier, String group,
                         RecipeIngredient ingredient, ItemStack result, float experience,
                         int cookingTime) {
        super(type, identifier);

        this.group = group;
        this.ingredient = ingredient;
        this.result = result;
        this.experience = experience;
        this.cookingTime = cookingTime;
    }
}
