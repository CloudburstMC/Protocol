package org.cloudburstmc.protocol.bedrock.packet;


import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.bedrock.data.inventory.ComponentItemData;
import org.cloudburstmc.protocol.common.PacketSignal;

import java.util.List;

/**
 * Definitions for custom component items added to the game
 */
@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class ItemComponentPacket implements BedrockPacket {

    private final List<ComponentItemData> items = new ObjectArrayList<>();

    @Override
    public PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    @Override
    public BedrockPacketType getPacketType() {
        return BedrockPacketType.ITEM_COMPONENT;
    }

    @Override
    public ItemComponentPacket clone() {
        try {
            return (ItemComponentPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}

