package com.nukkitx.protocol.bedrock.data.event;

import lombok.Value;

@Value
public class BellUsedEventData implements EventData {
    int itemId;

    @Override
    public EventDataType getType() {
        return EventDataType.BELL_USED;
    }
}
