package com.nukkitx.protocol.bedrock.data.inventory;

import com.nukkitx.protocol.bedrock.data.inventory.stackrequestactions.StackRequestActionData;

/**
 * ItemStackRequestData represents a single request present in an ItemStackRequest packet sent by the client to
 * change an item in an inventory.
 * Item stack requests are either approved or rejected by the server using the ItemStackResponse packet.
 */
public class ItemStackRequestData {
    // requestId is a unique ID for the request. This ID is used by the server to send a response for this
    // specific request in the ItemStackResponse packet.
    int requestId;

    // actions is a list of actions performed by the client. The actual type of the actions depends on which
    // ID was present
    StackRequestActionData[] actions;
}
