package com.nukkitx.protocol.bedrock.data.event;

import com.nukkitx.protocol.bedrock.data.BlockInteractionType;
import lombok.Value;

@Value
public class CauldronInteractEventData implements EventData {
    private final BlockInteractionType blockInteractionType;
    private final int itemId;

    @Override
    public EventDataType getType() {
        return EventDataType.CAULDRON_INTERACT;
    }
}
