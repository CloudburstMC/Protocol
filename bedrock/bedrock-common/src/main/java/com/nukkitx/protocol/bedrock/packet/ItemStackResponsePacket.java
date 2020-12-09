package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.data.inventory.ContainerSlotType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
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

    public enum ResponseStatus {
        OK,
        ERROR
    }

    /**
     * Response is a response to an individual ItemStackRequest.
     */
    @Value
    public static class Response {
        /**
         * success specifies if the request with the requestId below was successful. If this is the case, the
         * containers below will have information on what slots ended up changing. If not, the container info
         * will be empty.
         * @deprecated as of v419
         */
        @Deprecated
        boolean success;

        /**
         * Replaces the success boolean as of v419
         */
        ResponseStatus result;

        /**
         * requestId is the unique ID of the request that this response is in reaction to. If rejected, the client
         * will undo the actions from the request with this ID.
         */
        int requestId;

        /**
         * containers holds information on the containers that had their contents changed as a result of the
         * request.
         */
        List<ContainerEntry> containers;

        @Deprecated
        public Response(boolean success, int requestId, List<ContainerEntry> containers) {
            this.success = success;
            this.requestId = requestId;
            this.containers = containers;
            this.result = success ? ResponseStatus.OK : ResponseStatus.ERROR;
        }

        public Response(ResponseStatus result, int requestId, List<ContainerEntry> containers) {
            this.result = result;
            this.requestId = requestId;
            this.containers = containers;
            this.success = false;
        }
    }

    /**
     * ContainerEntry holds information on what slots in a container have what item stack in them.
     */
    @Value
    public static class ContainerEntry {
        /**
         * container that the slots that follow are in.
         */
        ContainerSlotType container;

        /**
         * items holds information on what item stack should be present in specific slots in the container.
         */
        List<ItemEntry> items;
    }

    /**
     * ItemEntry holds information on what item stack should be present in a specific slot.
     */
    @Value
    public static class ItemEntry {
        byte slot;
        byte hotbarSlot;
        byte count;

        /**
         * stackNetworkID is the network ID of the new stack at a specific slot.
         */
        int stackNetworkId;

        @NonNull String customName;
    }
}
