package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.data.inventory.stackrequestactions.StackRequestActionData;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import io.netty.util.AsciiString;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class ItemStackRequestPacket extends BedrockPacket {
    private final List<Request> requests = new ArrayList<>();

    @Override
    public boolean handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public BedrockPacketType getPacketType() {
        return BedrockPacketType.ITEM_STACK_REQUEST;
    }

    /**
     * Request represents a single request present in an ItemStackRequest packet sent by the client to
     * change an item in an inventory.
     * Item stack requests are either approved or rejected by the server using the ItemStackResponse packet.
     */
    @Value
    public static class Request {
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

}
