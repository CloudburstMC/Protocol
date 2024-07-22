package org.cloudburstmc.protocol.bedrock.packet;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.cloudburstmc.math.vector.Vector3f;
import org.cloudburstmc.math.vector.Vector3i;
import org.cloudburstmc.protocol.bedrock.data.definitions.BlockDefinition;
import org.cloudburstmc.protocol.bedrock.data.inventory.ItemData;
import org.cloudburstmc.protocol.bedrock.data.inventory.transaction.InventoryActionData;
import org.cloudburstmc.protocol.bedrock.data.inventory.transaction.InventoryTransactionType;
import org.cloudburstmc.protocol.bedrock.data.inventory.transaction.ItemUseTransaction;
import org.cloudburstmc.protocol.bedrock.data.inventory.transaction.LegacySetItemSlotData;
import org.cloudburstmc.protocol.common.PacketSignal;

import java.util.List;

@Data
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class InventoryTransactionPacket implements BedrockPacket {
    private int legacyRequestId;
    private final List<LegacySetItemSlotData> legacySlots = new ObjectArrayList<>();
    private final List<InventoryActionData> actions = new ObjectArrayList<>();
    private InventoryTransactionType transactionType;
    private int actionType;
    private long runtimeEntityId;
    private Vector3i blockPosition;
    private int blockFace;
    private int hotbarSlot;
    private ItemData itemInHand;
    private Vector3f playerPosition;
    private Vector3f clickPosition;
    private Vector3f headPosition;
    /**
     * @since v407
     * @deprecated v431
     */
    @Deprecated
    private boolean usingNetIds;
    /**
     * Block definition of block being picked.
     * ItemUseInventoryTransaction only
     *
     * @param blockDefinition block definition of block
     * @return block definition of block
     */
    private BlockDefinition blockDefinition;
    /**
     * @since v712
     */
    private ItemUseTransaction.TriggerType triggerType;

    @Override
    public final PacketSignal handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.INVENTORY_TRANSACTION;
    }
}
