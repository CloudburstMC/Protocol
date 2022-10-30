package org.cloudburstmc.protocol.bedrock.data.event;

import lombok.Value;

@Value
public class MovementCorrectedEventData implements EventData {
    private final float positionDelta;
    private final float cheatingScore;
    private final float scoreThreshold;
    private final float distanceThreshold;
    private final int durationThreshold;

    @Override
    public EventDataType getType() {
        return EventDataType.MOVEMENT_CORRECTED;
    }
}
