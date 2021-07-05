package org.cloudburstmc.protocol.java.data.crafting;

import lombok.Builder;
import lombok.Getter;
import net.kyori.adventure.key.Key;

@Getter
public class Recipe {
    private final RecipeType<Recipe> type;
    private final Key identifier;

    @Builder
    protected Recipe(RecipeType<Recipe> type, Key identifier) {
        this.type = type;
        this.identifier = identifier;
    }
}
