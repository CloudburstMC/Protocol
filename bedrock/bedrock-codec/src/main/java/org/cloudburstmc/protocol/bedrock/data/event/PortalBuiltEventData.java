package org.cloudburstmc.protocol.bedrock.data.event;

import lombok.Value;

@Value
public class PortalBuiltEventData implements EventData {
    private int dimensionId;

    @Override
    public EventDataType getType() {
        return EventDataType.PORTAL_BUILT;
    }
}
