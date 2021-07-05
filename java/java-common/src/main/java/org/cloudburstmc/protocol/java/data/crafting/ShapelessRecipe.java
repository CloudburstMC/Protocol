package org.cloudburstmc.protocol.java.data.crafting;

import lombok.Builder;
import lombok.Getter;
import net.kyori.adventure.key.Key;
import org.cloudburstmc.protocol.java.data.inventory.ItemStack;

@Getter
public class ShapelessRecipe extends Recipe {
    private final String group;
    private final ItemStack result;
    private final RecipeIngredient[] ingredients;

    @Builder
    protected ShapelessRecipe(RecipeType<?> type, Key identifier, String group,
                           ItemStack result, RecipeIngredient[] ingredients) {
        super(type, identifier);

        this.group = group;
        this.result = result;
        this.ingredients = ingredients;
    }
}
