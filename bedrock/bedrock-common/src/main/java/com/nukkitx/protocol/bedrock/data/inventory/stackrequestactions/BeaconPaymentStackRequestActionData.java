package com.nukkitx.protocol.bedrock.data.inventory.stackrequestactions;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * BeaconPaymentStackRequestActionData is sent by the client when it submits an item to enable effects from a
 * beacon. These items will have been moved into the beacon item slot in advance.
 */
@Getter
@AllArgsConstructor
public class BeaconPaymentStackRequestActionData extends StackRequestActionData {
    // The primary effect selected in the beacon
    int primaryEffect;

    // The secondary effect selected in the beacon
    int secondaryEffect;
}
