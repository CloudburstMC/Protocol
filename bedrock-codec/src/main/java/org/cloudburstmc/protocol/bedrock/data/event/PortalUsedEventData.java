package org.cloudburstmc.protocol.bedrock.data.event;

import lombok.Value;

@Value
public class PortalUsedEventData implements EventData {
    private final int fromDimensionId;
    private final int toDimensionId;

    @Override
    public EventDataType getType() {
        return EventDataType.PORTAL_USED;
    }
}
