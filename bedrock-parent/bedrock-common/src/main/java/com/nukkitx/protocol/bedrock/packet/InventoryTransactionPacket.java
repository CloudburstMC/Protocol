package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.math.vector.Vector3f;
import com.nukkitx.math.vector.Vector3i;
import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.data.inventory.InventoryActionData;
import com.nukkitx.protocol.bedrock.data.inventory.ItemData;
import com.nukkitx.protocol.bedrock.data.inventory.LegacySetItemSlotData;
import com.nukkitx.protocol.bedrock.data.inventory.TransactionType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(doNotUseGetters = true, callSuper = false)
public class InventoryTransactionPacket extends BedrockPacket {
    private int legacyRequestId;
    private final List<LegacySetItemSlotData> legacySlots = new ObjectArrayList<>();
    private final List<InventoryActionData> actions = new ObjectArrayList<>();
    private TransactionType transactionType;
    private int actionType;
    private long runtimeEntityId;
    private Vector3i blockPosition;
    private int blockFace;
    private int hotbarSlot;
    private ItemData itemInHand;
    private Vector3f playerPosition;
    private Vector3f clickPosition;
    private Vector3f headPosition;
    private boolean hasNetworkIds;
    /**
     * Runtime ID of block being picked.
     * ItemUseInventoryTransaction only
     *
     * @param blockRuntimeId runtime ID of block
     * @return runtime ID of block
     */
    private int blockRuntimeId;

    @Override
    public final boolean handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.INVENTORY_TRANSACTION;
    }
}
