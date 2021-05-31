package org.cloudburstmc.protocol.bedrock.data.event;

import lombok.Value;

@Value
public class BellUsedEventData implements EventData {
    private final int itemId;

    @Override
    public EventDataType getType() {
        return EventDataType.BELL_USED;
    }
}
