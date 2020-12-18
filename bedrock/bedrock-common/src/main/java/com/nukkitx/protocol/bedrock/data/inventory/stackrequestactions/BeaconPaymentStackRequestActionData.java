package com.nukkitx.protocol.bedrock.data.inventory.stackrequestactions;

import lombok.Value;

/**
 * BeaconPaymentStackRequestActionData is sent by the client when it submits an item to enable effects from a
 * beacon. These items will have been moved into the beacon item slot in advance.
 */
@Value
public class BeaconPaymentStackRequestActionData extends StackRequestActionData {
    int primaryEffect;
    int secondaryEffect;

    @Override
    public StackRequestActionType getType() {
        return StackRequestActionType.BEACON_PAYMENT;
    }
}
