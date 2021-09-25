package com.nukkitx.protocol.bedrock.data.event;

import lombok.Value;

@Value
public class MovementCorrectedEventData implements EventData {
    float positionDelta;
    float cheatingScore;
    float scoreThreshold;
    float distanceThreshold;
    int durationThreshold;

    @Override
    public EventDataType getType() {
        return EventDataType.MOVEMENT_CORRECTED;
    }
}
