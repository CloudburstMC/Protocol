package org.cloudburstmc.protocol.bedrock.data.inventory.stackrequestactions;

import lombok.Value;
import org.cloudburstmc.protocol.bedrock.data.inventory.StackRequestSlotInfoData;

/**
 * PlaceStackRequestAction is sent by the client to the server to place x amount of items from one slot into
 * another slot, such as when shift clicking an item in the inventory to move it around or when moving an item
 * in the cursor into a slot.
 */
@Value
public class PlaceStackRequestActionData implements TransferStackRequestActionData {
    byte count;
    StackRequestSlotInfoData source;
    StackRequestSlotInfoData destination;

    @Override
    public StackRequestActionType getType() {
        return StackRequestActionType.PLACE;
    }
}
