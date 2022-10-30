package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.bedrock.data.inventory.ItemStackRequest;
import org.cloudburstmc.protocol.common.PacketSignal;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@ToString(doNotUseGetters = true)
public class ItemStackRequestPacket implements BedrockPacket {
    private final List<ItemStackRequest> requests = new ArrayList<>();

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public BedrockPacketType getPacketType() {
        return BedrockPacketType.ITEM_STACK_REQUEST;
    }

}
