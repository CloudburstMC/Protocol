package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.data.inventory.ItemData;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import it.unimi.dsi.fastutil.ints.Int2ObjectLinkedOpenHashMap;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Map;


/**
 * CreativeContent is a packet sent by the server to set the creative inventory's content for a player.
 * Introduced in 1.16, this packet replaces the previous method - sending an InventoryContent packet with
 * creative inventory window ID.
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true, callSuper = false)
public class CreativeContentPacket extends BedrockPacket {
    /**
     * Item entries for the creative menu. Each item must have a unique ID for the net ID manager
     */
    private final Map<Integer, ItemData> entries = new Int2ObjectLinkedOpenHashMap<>();

    @Override
    public boolean handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public BedrockPacketType getPacketType() {
        return BedrockPacketType.CREATIVE_CONTENT;
    }
}
