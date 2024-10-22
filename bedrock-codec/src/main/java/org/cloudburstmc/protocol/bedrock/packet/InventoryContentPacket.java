package org.cloudburstmc.protocol.bedrock.packet;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.protocol.bedrock.data.inventory.ContainerSlotType;
import org.cloudburstmc.protocol.bedrock.data.inventory.FullContainerName;
import org.cloudburstmc.protocol.bedrock.data.inventory.ItemData;
import org.cloudburstmc.protocol.common.PacketSignal;

import java.util.List;

@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class InventoryContentPacket implements BedrockPacket {
    private List<ItemData> contents = new ObjectArrayList<>();
    private int containerId;
    /**
     * @since v712
     */
    private FullContainerName containerNameData = new FullContainerName(ContainerSlotType.ANVIL_INPUT, null);
    /**
     * @since v729
     * @deprecated since v748. Use storageItem ItemData size instead.
     */
    private int dynamicContainerSize;
    /**
     * @since v748
     */
    private ItemData storageItem = ItemData.AIR;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.INVENTORY_CONTENT;
    }

    @Override
    public InventoryContentPacket clone() {
        try {
            return (InventoryContentPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}

