package com.nukkitx.protocol.bedrock.data.inventory.stackrequestactions;

import com.nukkitx.protocol.bedrock.data.inventory.StackRequestSlotInfoData;

/**
 * TransferStackRequestActionData is the structure shared by StackRequestActions that transfer items from one
 * slot into another
 */
public interface TransferStackRequestActionData extends StackRequestActionData {

    byte getCount();

   StackRequestSlotInfoData getSource();

    StackRequestSlotInfoData getDestination();
}
