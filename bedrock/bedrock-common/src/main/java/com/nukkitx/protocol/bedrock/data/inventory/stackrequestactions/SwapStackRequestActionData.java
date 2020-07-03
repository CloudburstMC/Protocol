package com.nukkitx.protocol.bedrock.data.inventory.stackrequestactions;

import com.nukkitx.protocol.bedrock.data.inventory.StackRequestSlotInfoData;
import lombok.Value;

/**
 * SwapStackRequestActionData is sent by the client to swap the item in its cursor with an item present in another
 * container. The two item stacks swap places.
 */
@Value
public class SwapStackRequestActionData implements StackRequestActionData {
    StackRequestSlotInfoData source;
    StackRequestSlotInfoData destination;

    @Override
    public StackRequestActionType getType() {
        return StackRequestActionType.SWAP;
    }
}
