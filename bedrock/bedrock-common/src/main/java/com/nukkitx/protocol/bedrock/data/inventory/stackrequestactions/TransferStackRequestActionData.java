package com.nukkitx.protocol.bedrock.data.inventory.stackrequestactions;

import com.nukkitx.protocol.bedrock.data.inventory.StackRequestSlotInfoData;

/**
 * TransferStackRequestActionData is the structure shared by StackRequestActions that transfer items from one
 * slot into another
 */
public abstract class TransferStackRequestActionData extends StackRequestActionData {

    public abstract byte getCount();

    public abstract StackRequestSlotInfoData getSource();

    public abstract StackRequestSlotInfoData getDestination();
}
