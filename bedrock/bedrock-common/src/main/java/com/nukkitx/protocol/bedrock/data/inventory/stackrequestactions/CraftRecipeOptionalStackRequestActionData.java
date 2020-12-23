package com.nukkitx.protocol.bedrock.data.inventory.stackrequestactions;

import lombok.Value;

/**
 * Called when renaming an item on an anvil.
 */
@Value
public class CraftRecipeOptionalStackRequestActionData implements StackRequestActionData {

    @Override
    public StackRequestActionType getType() {
        return StackRequestActionType.CRAFT_RECIPE_OPTIONAL;
    }
}
