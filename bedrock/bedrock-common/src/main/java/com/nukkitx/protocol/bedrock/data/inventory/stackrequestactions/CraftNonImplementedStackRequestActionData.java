package com.nukkitx.protocol.bedrock.data.inventory.stackrequestactions;

import lombok.Value;

/**
 * CraftNonImplementedStackRequestActionData is an action sent for inventory actions that aren't yet implemented
 * in the new system. These include, for example, anvils
 */
@Value
public class CraftNonImplementedStackRequestActionData extends StackRequestActionData {

    @Override
    public StackRequestActionType getType() {
        return StackRequestActionType.CRAFT_NON_IMPLEMENTED_DEPRECATED;
    }
}
