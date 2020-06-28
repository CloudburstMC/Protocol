package com.nukkitx.protocol.bedrock.data.inventory.stackrequestactions;

import com.nukkitx.protocol.bedrock.data.inventory.StackRequestSlotInfoData;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Value;

/**
 * TransferStackRequestActionData is the structure shared by StackRequestActions that transfer items from one
 * slot into another
 */
@Getter
@AllArgsConstructor
public class TransferStackRequestActionData extends StackRequestActionData {
    // Count of the item in the source slot that was taken towards the destination slot.
    byte count;

    // Source from which count of the itemstack is taken
    StackRequestSlotInfoData source;

    // Destination to which count of the itemstack is put
    StackRequestSlotInfoData destination;
}
