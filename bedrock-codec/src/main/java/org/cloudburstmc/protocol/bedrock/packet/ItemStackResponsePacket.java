package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.response.ItemStackResponse;
import org.cloudburstmc.protocol.common.PacketSignal;

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
@ToString(doNotUseGetters = true)
public class ItemStackResponsePacket implements BedrockPacket {
    private final List<ItemStackResponse> entries = new ArrayList<>();

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public BedrockPacketType getPacketType() {
        return BedrockPacketType.ITEM_STACK_RESPONSE;
    }

    @Override
    public ItemStackResponsePacket clone() {
        try {
            return (ItemStackResponsePacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}

