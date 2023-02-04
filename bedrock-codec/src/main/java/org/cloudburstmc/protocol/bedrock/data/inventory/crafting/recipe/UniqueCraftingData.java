package org.cloudburstmc.protocol.bedrock.data.inventory.crafting.recipe;

import java.util.UUID;

public interface UniqueCraftingData extends NetworkRecipeData {

    UUID getUuid();
}
