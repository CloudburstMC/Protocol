package org.cloudburstmc.protocol.java.data.crafting;

import lombok.Builder;
import lombok.Getter;
import net.kyori.adventure.key.Key;
import org.cloudburstmc.protocol.java.data.inventory.ItemStack;

@Getter
public class SmithingRecipe extends Recipe {
    private final RecipeIngredient base;
    private final RecipeIngredient addition;
    private final ItemStack result;

    @Builder
    protected SmithingRecipe(RecipeType<?> type, Key identifier, RecipeIngredient base,
                          RecipeIngredient addition, ItemStack result) {
        super(type, identifier);

        this.base = base;
        this.addition = addition;
        this.result = result;
    }
}
