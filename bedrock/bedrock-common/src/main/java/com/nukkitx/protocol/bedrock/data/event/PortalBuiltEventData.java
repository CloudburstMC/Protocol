package com.nukkitx.protocol.bedrock.data.event;

import lombok.Value;

@Value
public class PortalBuiltEventData implements EventData {
    int dimensionId;

    @Override
    public EventDataType getType() {
        return EventDataType.PORTAL_BUILT;
    }
}
