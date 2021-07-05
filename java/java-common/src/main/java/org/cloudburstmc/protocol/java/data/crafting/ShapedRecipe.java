package org.cloudburstmc.protocol.java.data.crafting;

import lombok.Builder;
import lombok.Getter;
import net.kyori.adventure.key.Key;
import org.cloudburstmc.protocol.java.data.inventory.ItemStack;

@Getter
public class ShapedRecipe extends Recipe {
    private final int width;
    private final int height;
    private final String group;
    private final RecipeIngredient[] ingredients;
    private final ItemStack result;

    @Builder
    protected ShapedRecipe(RecipeType<?> type, Key identifier, String group,
                        int width, int height, RecipeIngredient[] ingredients,
                        ItemStack result) {
        super(type, identifier);

        this.width = width;
        this.height = height;
        this.group = group;
        this.ingredients = ingredients;
        this.result = result;
    }
}
