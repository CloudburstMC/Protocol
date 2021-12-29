package com.nukkitx.protocol.bedrock.data.inventory.stackrequestactions;

import lombok.Value;

/**
 * MineBlockStackRequestActionData is sent by the client when it breaks a block.
 */
@Value
public class MineBlockStackRequestActionData implements StackRequestActionData {
    int hotbarSlot;
    int predictedDurability;
    int stackNetworkId;

    @Override
    public StackRequestActionType getType() {
        return StackRequestActionType.MINE_BLOCK;
    }

}
