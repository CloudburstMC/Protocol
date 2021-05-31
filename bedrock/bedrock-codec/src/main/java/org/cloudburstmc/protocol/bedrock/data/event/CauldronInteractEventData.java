package org.cloudburstmc.protocol.bedrock.data.event;

import lombok.Value;
import org.cloudburstmc.protocol.bedrock.data.BlockInteractionType;

@Value
public class CauldronInteractEventData implements EventData {
    private final BlockInteractionType blockInteractionType;
    private final int itemId;

    @Override
    public EventDataType getType() {
        return EventDataType.CAULDRON_INTERACT;
    }
}
