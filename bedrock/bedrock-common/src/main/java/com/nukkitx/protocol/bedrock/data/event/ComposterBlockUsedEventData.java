package com.nukkitx.protocol.bedrock.data.event;

import lombok.Value;

@Value
public class ComposterBlockUsedEventData implements EventData {
    private final int blockInteractionType;
    private final int unknown0;

    @Override
    public EventDataType getType() {
        return EventDataType.COMPOSTER_BLOCK_USED;
    }
}
