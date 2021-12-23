package com.nukkitx.protocol.bedrock.data.inventory.stackrequestactions;

import lombok.Value;

@Value
public class CraftLoomStackRequestActionData implements StackRequestActionData {
    String patternId;

    @Override
    public StackRequestActionType getType() {
        return StackRequestActionType.CRAFT_LOOM;
    }
}
