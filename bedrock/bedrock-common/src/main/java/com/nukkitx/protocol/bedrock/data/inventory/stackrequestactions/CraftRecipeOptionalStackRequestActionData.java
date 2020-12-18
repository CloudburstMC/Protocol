package com.nukkitx.protocol.bedrock.data.inventory.stackrequestactions;

import lombok.Value;

@Value
public class CraftRecipeOptionalStackRequestActionData extends StackRequestActionData {
    int recipeId;

    @Override
    public StackRequestActionType getType() {
        return StackRequestActionType.CRAFT_RECIPE_OPTIONAL;
    }
}
