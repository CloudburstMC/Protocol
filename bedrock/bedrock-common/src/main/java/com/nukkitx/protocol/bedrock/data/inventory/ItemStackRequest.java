package com.nukkitx.protocol.bedrock.data.inventory;

import com.nukkitx.protocol.bedrock.data.inventory.stackrequestactions.StackRequestActionData;
import lombok.Value;

/**
 * Request represents a single request present in an {@link com.nukkitx.protocol.bedrock.packet.ItemStackRequestPacket} or
 * {@link com.nukkitx.protocol.bedrock.packet.PlayerAuthInputPacket} sent by the client to
 * change an item in an inventory.
 * Item stack requests are either approved or rejected by the server using the ItemStackResponse packet.
 */
@Value
public class ItemStackRequest {
    /**
     * requestId is a unique ID for the request. This ID is used by the server to send a response for this
     * specific request in the ItemStackResponse packet.
     */
    int requestId;

    /**
     * actions is a list of actions performed by the client. The actual type of the actions depends on which
     * ID was present
     */
    StackRequestActionData[] actions;

    /**
     * Used for the server to determine which strings should be filtered. Used in anvils to verify a renamed item.
     * @since v422
     */
    String[] filterStrings;
}
