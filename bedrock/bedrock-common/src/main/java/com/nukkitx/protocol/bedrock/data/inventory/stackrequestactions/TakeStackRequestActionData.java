package com.nukkitx.protocol.bedrock.data.inventory.stackrequestactions;

import com.nukkitx.protocol.bedrock.data.inventory.StackRequestSlotInfoData;
import lombok.Value;

/**
 * TakeStackRequestActionData is sent by the client to the server to take x amount of items from one slot in a
 * container to the cursor.
 */
@Value
public class TakeStackRequestActionData implements TransferStackRequestActionData {
    byte count;
    StackRequestSlotInfoData source;
    StackRequestSlotInfoData destination;

    @Override
    public StackRequestActionType getType() {
        return StackRequestActionType.TAKE;
    }
}
