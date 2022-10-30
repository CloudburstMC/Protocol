package org.cloudburstmc.protocol.bedrock.data.inventory.stackrequestactions;

import lombok.Value;
import org.cloudburstmc.protocol.bedrock.data.inventory.StackRequestSlotInfoData;

/**
 * ConsumeStackRequestAction is sent by the client when it uses an item to craft another item. The original
 * item is 'consumed'.
 */
@Value
public class ConsumeStackRequestActionData implements StackRequestActionData {
    byte count;
    StackRequestSlotInfoData source;

    @Override
    public StackRequestActionType getType() {
        return StackRequestActionType.CONSUME;
    }
}
