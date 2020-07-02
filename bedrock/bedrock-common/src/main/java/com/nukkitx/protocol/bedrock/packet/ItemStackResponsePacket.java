package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.data.inventory.Container;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.util.ArrayList;
import java.util.List;

/**
 * ItemStackResponse is sent by the server in response to an ItemStackRequest packet from the client. This
 * packet is used to either approve or reject ItemStackRequests from the client. If a request is approved, the
 * client will simply continue as normal. If rejected, the client will undo the actions so that the inventory
 * should be in sync with the server again.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ItemStackResponsePacket extends BedrockPacket {
    private final List<Response> entries = new ArrayList<>();

    @Override
    public boolean handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public BedrockPacketType getPacketType() {
        return BedrockPacketType.ITEM_STACK_RESPONSE;
    }

    /**
     * Response is a response to an individual ItemStackRequest.
     */
    @Value
    public static class Response {
        // success specifies if the request with the requestId below was successful. If this is the case, the
        // containers below will have information on what slots ended up changing. If not, the container info
        // will be empty.
        private final boolean success;

        // requestId is the unique ID of the request that this response is in reaction to. If rejected, the client
        // will undo the actions from the request with this ID.
        private final int requestId;

        // containers holds information on the containers that had their contents changed as a result of the
        // request.
        private final List<ContainerEntry> containers;
    }

    /**
     * ContainerEntry holds information on what slots in a container have what item stack in them.
     */
    @Value
    public static class ContainerEntry {
        // container that the slots that follow are in.
        private final Container container;

        // items holds information on what item stack should be present in specific slots in the container.
        private final List<ItemEntry> items;
    }

    /**
     * ItemEntry holds information on what item stack should be present in a specific slot.
     */
    @Value
    public static class ItemEntry {
        private final byte slot;
        private final byte hotbarSlot;
        private final byte count;

        // stackNetworkID is the network ID of the new stack at a specific slot.
        private final int stackNetworkId;
    }
}
