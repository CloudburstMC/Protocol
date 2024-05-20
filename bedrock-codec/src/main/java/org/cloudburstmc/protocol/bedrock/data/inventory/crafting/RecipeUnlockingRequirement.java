package org.cloudburstmc.protocol.bedrock.data.inventory.crafting;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Value;
import org.cloudburstmc.protocol.bedrock.data.inventory.descriptor.ItemDescriptorWithCount;

import java.util.List;

@Value
public class RecipeUnlockingRequirement {
    public static final RecipeUnlockingRequirement INVALID = new RecipeUnlockingRequirement(UnlockingContext.NONE);

    UnlockingContext context;
    List<ItemDescriptorWithCount> ingredients = new ObjectArrayList<>();

    public enum UnlockingContext {
        NONE,
        ALWAYS_UNLOCKED,
        PLAYER_IN_WATER,
        PLAYER_HAS_MANY_ITEMS;

        private static final UnlockingContext[] VALUES = values();

        public static UnlockingContext from(int id) {
            return VALUES[id];
        }
    }

    public boolean isInvalid() {
        return this.ingredients.isEmpty() && this.context.equals(UnlockingContext.NONE);
    }
}