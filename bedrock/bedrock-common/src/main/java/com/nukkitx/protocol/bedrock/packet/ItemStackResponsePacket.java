package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.util.ArrayList;
import java.util.List;

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

    @Value
    public static class Response {
        private final boolean hasContainers;
        private final int requestId;
        private final List<ContainerEntry> containers;
    }

    @Value
    public static class ContainerEntry {
        private final byte windowsId;
        private final List<ItemEntry> items;
    }

    @Value
    public static class ItemEntry {
        private final byte slot;
        private final byte hotbarSlot;
        private final byte count;
        private final int itemStackId;
    }
}
