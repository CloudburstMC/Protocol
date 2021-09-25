package com.nukkitx.protocol.bedrock.data.event;

import lombok.Value;

@Value
public class CauldronUsedEventData implements EventData {
    int potionId;
    int color;
    int fillLevel;

    @Override
    public EventDataType getType() {
        return EventDataType.CAULDRON_USED;
    }
}
