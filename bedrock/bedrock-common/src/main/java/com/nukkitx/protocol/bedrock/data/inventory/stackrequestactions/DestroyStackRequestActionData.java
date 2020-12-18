package com.nukkitx.protocol.bedrock.data.inventory.stackrequestactions;

import com.nukkitx.protocol.bedrock.data.inventory.StackRequestSlotInfoData;
import lombok.Value;

/**
 * DestroyStackRequestActionData is sent by the client when it destroys an item in creative mode by moving it
 * back into the creative inventory.
 */
@Value
public class DestroyStackRequestActionData extends StackRequestActionData {
    byte count;
    StackRequestSlotInfoData source;

    @Override
    public StackRequestActionType getType() {
        return StackRequestActionType.DESTROY;
    }
}
