package org.cloudburstmc.protocol.bedrock.data.inventory.transaction;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Data;
import org.cloudburstmc.math.vector.Vector3f;
import org.cloudburstmc.math.vector.Vector3i;
import org.cloudburstmc.protocol.bedrock.data.defintions.BlockDefinition;
import org.cloudburstmc.protocol.bedrock.data.inventory.ItemData;

import java.util.List;

@Data
public class ItemUseTransaction {
    private int legacyRequestId;
    private final List<LegacySetItemSlotData> legacySlots = new ObjectArrayList<>();
    private boolean usingNetIds;
    private final List<InventoryActionData> actions = new ObjectArrayList<>();
    private int actionType;
    private Vector3i blockPosition;
    private int blockFace;
    private int hotbarSlot;
    private ItemData itemInHand;
    private Vector3f playerPosition;
    private Vector3f clickPosition;
    private BlockDefinition blockDefinition;
}
