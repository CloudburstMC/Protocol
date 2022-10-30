package org.cloudburstmc.protocol.bedrock.data.inventory.stackrequestactions;

import lombok.Value;
import org.cloudburstmc.protocol.bedrock.data.inventory.StackRequestSlotInfoData;

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
