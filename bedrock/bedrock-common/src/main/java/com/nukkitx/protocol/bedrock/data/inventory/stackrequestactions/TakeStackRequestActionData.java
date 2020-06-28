package com.nukkitx.protocol.bedrock.data.inventory.stackrequestactions;


import com.nukkitx.protocol.bedrock.data.inventory.StackRequestSlotInfoData;

/**
 * TakeStackRequestActionData is sent by the client to the server to take x amount of items from one slot in a
 * container to the cursor.
 */

public class TakeStackRequestActionData extends TransferStackRequestActionData {
    public TakeStackRequestActionData(byte count, StackRequestSlotInfoData source, StackRequestSlotInfoData destination) {
        super(count, source, destination);
    }
}
