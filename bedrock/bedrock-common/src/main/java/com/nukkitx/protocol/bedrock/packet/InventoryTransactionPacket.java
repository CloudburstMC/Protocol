package com.nukkitx.protocol.bedrock.packet;

import com.flowpowered.math.vector.Vector3f;
import com.flowpowered.math.vector.Vector3i;
import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.data.InventoryAction;
import com.nukkitx.protocol.bedrock.data.Item;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class InventoryTransactionPacket extends BedrockPacket {
    protected final List<InventoryAction> actions = new ArrayList<>();
    protected Type transactionType;
    protected int actionType;
    protected long runtimeEntityId;
    protected Vector3i blockPosition;
    protected int face;
    protected int hotbarSlot;
    protected Item itemInHand;
    protected Vector3f playerPosition;
    protected Vector3f clickPosition;
    protected Vector3f headPosition;

    @Override
    public final boolean handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public enum Type {
        NORMAL,
        INVENTORY_MISMATCH,
        ITEM_USE,
        ITEM_USE_ON_ENTITY,
        ITEM_RELEASE
    }
}
