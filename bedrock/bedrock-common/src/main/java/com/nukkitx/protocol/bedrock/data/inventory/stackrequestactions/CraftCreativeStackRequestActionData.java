package com.nukkitx.protocol.bedrock.data.inventory.stackrequestactions;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * CraftCreativeStackRequestActionData is sent by the client when it takes an item out of the creative inventory.
 * The item is thus not really crafted, but instantly created.
 */
@Getter
@AllArgsConstructor
public class CraftCreativeStackRequestActionData extends StackRequestActionData {
    // creativeItemNetworkId is the network ID of the creative item that is being created. This is one of the
    // creative item network IDs sent in the CreativeContent packet.
    int creativeItemNetworkId;
}
