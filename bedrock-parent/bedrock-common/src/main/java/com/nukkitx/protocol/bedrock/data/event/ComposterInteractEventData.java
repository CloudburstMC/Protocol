package com.nukkitx.protocol.bedrock.data.event;

import com.nukkitx.protocol.bedrock.data.BlockInteractionType;
import lombok.Value;

@Value
public class ComposterInteractEventData implements EventData {
    private final BlockInteractionType blockInteractionType;
    private final int itemId;

    @Override
    public EventDataType getType() {
        return EventDataType.COMPOSTER_INTERACT;
    }
}
