package com.nukkitx.protocol.bedrock.packet;


import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.data.inventory.ComponentItemData;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Arrays;
import java.util.List;

/**
 * Definitions for custom component items added to the game
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(doNotUseGetters = true, callSuper = false)
public class ItemComponentPacket extends BedrockPacket {

    private final List<ComponentItemData> items = new ObjectArrayList<>();

    public ItemComponentPacket addItem(ComponentItemData item) {
        this.items.add(item);
        return this;
    }

    public ItemComponentPacket addItems(ComponentItemData... items) {
        this.items.addAll(Arrays.asList(items));
        return this;
    }

    @Override
    public boolean handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public BedrockPacketType getPacketType() {
        return BedrockPacketType.ITEM_COMPONENT;
    }
}
