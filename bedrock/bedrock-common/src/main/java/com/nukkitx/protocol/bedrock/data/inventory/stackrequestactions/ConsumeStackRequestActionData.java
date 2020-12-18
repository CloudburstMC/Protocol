package com.nukkitx.protocol.bedrock.data.inventory.stackrequestactions;

import com.nukkitx.protocol.bedrock.data.inventory.StackRequestSlotInfoData;
import lombok.Value;

/**
 * ConsumeStackRequestAction is sent by the client when it uses an item to craft another item. The original
 * item is 'consumed'.
 */
@Value
public class ConsumeStackRequestActionData extends StackRequestActionData {
    byte count;
    StackRequestSlotInfoData source;

    @Override
    public StackRequestActionType getType() {
        return StackRequestActionType.CONSUME;
    }
}
