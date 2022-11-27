package org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.action.ItemStackRequestAction;

/**
 * Request represents a single request present in an {@link org.cloudburstmc.protocol.bedrock.packet.ItemStackRequestPacket} or
 * {@link org.cloudburstmc.protocol.bedrock.packet.PlayerAuthInputPacket} sent by the client to
 * change an item in an inventory.
 * Item stack requests are either approved or rejected by the server using the ItemStackResponse packet.
 */
@Value
@RequiredArgsConstructor
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
    ItemStackRequestAction[] actions;

    /**
     * Used for the server to determine which strings should be filtered. Used in anvils to verify a renamed item.
     *
     * @since v422
     */
    String[] filterStrings;

    /**
     * @since v552
     */
    TextProcessingEventOrigin textProcessingEventOrigin;

    public ItemStackRequest(int requestId, ItemStackRequestAction[] actions, String[] filterStrings) {
        this(requestId, actions, filterStrings, TextProcessingEventOrigin.BLOCK_ENTITY_DATA_TEXT);
    }
}
