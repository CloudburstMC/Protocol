package com.nukkitx.protocol.bedrock.data.event;

import lombok.Value;

@Value
public class BellBlockUsedEventData implements EventData {
    private final int unknown0;

    @Override
    public EventDataType getType() {
        return EventDataType.BELL_BLOCK_USED;
    }
}
