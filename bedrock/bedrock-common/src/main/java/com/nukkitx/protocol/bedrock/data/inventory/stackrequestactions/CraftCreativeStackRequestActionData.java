package com.nukkitx.protocol.bedrock.data.inventory.stackrequestactions;

import lombok.Value;

/**
 * CraftCreativeStackRequestActionData is sent by the client when it takes an item out of the creative inventory.
 * The item is thus not really crafted, but instantly created.
 */
@Value
public class CraftCreativeStackRequestActionData implements StackRequestActionData {
    /**
     * creativeItemNetworkId is the network ID of the creative item that is being created. This is one of the
     * creative item network IDs sent in the CreativeContent packet.
     */
    int creativeItemNetworkId;

    @Override
    public StackRequestActionType getType() {
        return StackRequestActionType.CRAFT_CREATIVE;
    }
}
