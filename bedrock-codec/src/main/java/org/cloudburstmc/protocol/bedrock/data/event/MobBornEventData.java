package org.cloudburstmc.protocol.bedrock.data.event;

import lombok.Value;

@Value
public class MobBornEventData implements EventData {
    private final int entityType;
    private final int variant;
    private final int color;

    @Override
    public EventDataType getType() {
        return EventDataType.MOB_BORN;
    }
}
