package org.cloudburstmc.protocol.java.data.crafting;

import lombok.Value;
import org.cloudburstmc.protocol.java.data.inventory.ItemStack;

@Value
public class RecipeIngredient {
    ItemStack[] choices;
}
