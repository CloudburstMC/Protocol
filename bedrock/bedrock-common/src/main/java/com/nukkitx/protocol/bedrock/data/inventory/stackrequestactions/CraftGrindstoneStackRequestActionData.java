package com.nukkitx.protocol.bedrock.data.inventory.stackrequestactions;

import lombok.Value;

@Value
public class CraftGrindstoneStackRequestActionData implements StackRequestActionData {
    int recipeNetworkId;
    int repairCost;

    @Override
    public StackRequestActionType getType() {
        return StackRequestActionType.CRAFT_REPAIR_AND_DISENCHANT;
    }
}
