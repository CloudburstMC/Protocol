package org.cloudburstmc.protocol.java.data.crafting;

import lombok.Builder;
import lombok.Getter;
import net.kyori.adventure.key.Key;
import org.cloudburstmc.protocol.java.data.inventory.ItemStack;

@Getter
public class StonecuttingRecipe extends Recipe {
    private final String group;
    private final RecipeIngredient ingredient;
    private final ItemStack result;

    @Builder
    protected StonecuttingRecipe(RecipeType<?> type, Key identifier, String group,
                              RecipeIngredient ingredient, ItemStack result) {
        super(type, identifier);

        this.group = group;
        this.ingredient = ingredient;
        this.result = result;
    }
}
