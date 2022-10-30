package org.cloudburstmc.protocol.bedrock.data.inventory.stackrequestactions;

import lombok.Value;
import org.cloudburstmc.protocol.bedrock.data.inventory.StackRequestSlotInfoData;

/**
 * DestroyStackRequestActionData is sent by the client when it destroys an item in creative mode by moving it
 * back into the creative inventory.
 */
@Value
public class DestroyStackRequestActionData implements StackRequestActionData {
    byte count;
    StackRequestSlotInfoData source;

    @Override
    public StackRequestActionType getType() {
        return StackRequestActionType.DESTROY;
    }
}
