package com.nukkitx.protocol.bedrock.data.event;

import lombok.Value;

@Value
public class MovementAnomalyEventData implements EventData {
    int eventType;
    float cheatingScore;
    float averagePositionDelta;
    float totalPositionDelta;
    float minPositionDelta;
    float maxPositionDelta;

    @Override
    public EventDataType getType() {
        return EventDataType.MOVEMENT_ANOMALY;
    }
}
