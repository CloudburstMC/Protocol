package com.nukkitx.protocol.bedrock.data.event;

import lombok.Value;

@Value
public class MobBornEventData implements EventData {
    int entityType;
    int variant;
    int color;

    @Override
    public EventDataType getType() {
        return EventDataType.MOB_BORN;
    }
}
