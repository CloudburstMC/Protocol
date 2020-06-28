package com.nukkitx.protocol.bedrock.data.inventory.stackrequestactions;

import com.nukkitx.protocol.bedrock.data.inventory.StackRequestSlotInfoData;

/**
 * ConsumeStackRequestAction is sent by the client when it uses an item to craft another item. The original
 * item is 'consumed'.
 */
public class ConsumeStackRequestActionData extends DestroyStackRequestActionData {
    public ConsumeStackRequestActionData(byte count, StackRequestSlotInfoData source) {
        super(count, source);
    }
}
