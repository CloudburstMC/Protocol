package org.cloudburstmc.protocol.java.data.crafting;

import lombok.Getter;
import net.kyori.adventure.key.Key;

@Getter
public abstract class Recipe {
    private final RecipeType<?> type;
    private final Key identifier;

    protected Recipe(RecipeType<?> type, Key identifier) {
        this.type = type;
        this.identifier = identifier;
    }
}
