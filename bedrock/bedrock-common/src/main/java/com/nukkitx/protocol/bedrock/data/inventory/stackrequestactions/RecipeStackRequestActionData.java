package com.nukkitx.protocol.bedrock.data.inventory.stackrequestactions;

/**
 * RecipeStackRequestActionData is the structure shared by StackRequestActions that contain the
 * network id of the recipe the client is about to craft
 */
public interface RecipeStackRequestActionData extends StackRequestActionData {

    int getRecipeNetworkId();
}
