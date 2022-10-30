package org.cloudburstmc.protocol.bedrock.data.event;

import lombok.Value;

@Value
public class CauldronUsedEventData implements EventData {
    private final int potionId;
    private final int color;
    private final int fillLevel;

    @Override
    public EventDataType getType() {
        return EventDataType.CAULDRON_USED;
    }
}
