package org.cloudburstmc.protocol.java.data.crafting;

import lombok.Builder;
import net.kyori.adventure.key.Key;

public class SpecialRecipe extends Recipe {

    @Builder
    protected SpecialRecipe(RecipeType<?> type, Key identifier) {
        super(type, identifier);
    }
}
