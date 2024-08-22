package org.cloudburstmc.protocol.bedrock.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.bedrock.data.inventory.FullContainerName;
import org.cloudburstmc.protocol.bedrock.data.inventory.ItemData;
import org.cloudburstmc.protocol.common.PacketSignal;

@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class InventorySlotPacket implements BedrockPacket {
    private int containerId;
    private int slot;
    private ItemData item;
    /**
     * @since v712
     * @deprecated since v729
     */
    private int dynamicContainerId;
    /**
     * @since v729
     */
    private FullContainerName fullContainerName;
    /**
     * @since v729
     */
    private int dynamicContainerSize;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.INVENTORY_SLOT;
    }
}
